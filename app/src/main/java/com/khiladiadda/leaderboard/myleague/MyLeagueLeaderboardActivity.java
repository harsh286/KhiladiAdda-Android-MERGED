package com.khiladiadda.leaderboard.myleague;

import static com.khiladiadda.utility.AppConstant.PAGE_SIZE;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.leaderboard.LeaderboardPresenter;
import com.khiladiadda.leaderboard.adapter.AllLeaderBoardRVAdapter;
import com.khiladiadda.leaderboard.interfaces.ILeaderboardPresenter;
import com.khiladiadda.leaderboard.interfaces.ILeaderboardView;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.AllLeaderBoardResponse;
import com.khiladiadda.network.model.response.AllLederBoardDetails;
import com.khiladiadda.network.model.response.LeaderboardMainResponse;
import com.khiladiadda.network.model.response.LudoAddaMainResponse;
import com.khiladiadda.network.model.response.LudoLeaderboardResponse;
import com.khiladiadda.network.model.response.OverallLeadBoardResponse;
import com.khiladiadda.network.model.response.hth.LeaderBoardHthResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyLeagueLeaderboardActivity extends BaseActivity implements ILeaderboardView {

    @BindView(R.id.iv_back) ImageView mBackIV;
    @BindView(R.id.tv_activity_name) TextView mActivityNameTV;
    @BindView(R.id.iv_notification) ImageView mNotificationIV;
    @BindView(R.id.rv_leaderboard) RecyclerView mLeaderBoardRV;
    @BindView(R.id.tv_no_data) TextView mNoDataTV;

    private LinearLayoutManager mLayoutManager;
    private ILeaderboardPresenter mPresenter;
    private AllLeaderBoardRVAdapter mAdapter;
    private List<AllLederBoardDetails> mList = null;
    private String mGameId;
    private boolean isLoading, isLastPage;
    private int mCurrentPage = 0, mItemCount;

    @Override protected int getContentView() {
        return R.layout.activity_myleague_leaderboard;
    }

    @Override protected void initViews() {
        mActivityNameTV.setText(R.string.text_leaderboard);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
    }

    @Override protected void initVariables() {
        mPresenter = new LeaderboardPresenter(this);
        mGameId = getIntent().getStringExtra(AppConstant.ID);
        mList = new ArrayList<>();
        mAdapter = new AllLeaderBoardRVAdapter(mList, 2);
        mLayoutManager = new LinearLayoutManager(this);
        mLeaderBoardRV.setLayoutManager(mLayoutManager);
        mLeaderBoardRV.setAdapter(mAdapter);
        mLeaderBoardRV.addOnScrollListener(recyclerViewOnScrollListener);
        getData();
    }

    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getGameById(mGameId, mCurrentPage, PAGE_SIZE);
        } else {
            Snackbar.make(mBackIV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override public void onClick(View v) {
        if(v.getId() == R.id.iv_back){
            finish();
        }
    }

    @Override public void onLeaderBoardComplete(AllLeaderBoardResponse responseModel) {
        if (responseModel.isStatus()) {
            List<AllLederBoardDetails> leaderboardList = responseModel.getResponse();
            mItemCount = responseModel.getResponse().size();
            if (mCurrentPage == 0 && mItemCount <= 0) {
                mList.clear();
                mNoDataTV.setVisibility(View.VISIBLE);
            } else if (mCurrentPage == 0) {
                mList.clear();
            }
            if (mItemCount > 0) {
                mList.addAll(leaderboardList);
            }
            isLoading = false;
            mCurrentPage++;
            if (mItemCount < PAGE_SIZE) {
                isLastPage = true;
            }
        } else {
            mNoDataTV.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
        hideProgress();
    }

    @Override public void onLeaderBoardFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onLudoLeaderBoardComplete(LudoLeaderboardResponse responseModel) {

    }

    @Override
    public void onLudoLeaderBoardFailure(ApiError error) {

    }

    @Override
    public void onLeaderFanBattleComplete(OverallLeadBoardResponse responseModel) {

    }

    @Override
    public void onLeaderFanBattleFailure(ApiError error) {

    }

    @Override
    public void onLeaderHTHBattleComplete(LeaderBoardHthResponse responseModel) {

    }

    @Override
    public void onLeaderHTHBattleFailure(ApiError error) {

    }

    @Override
    public void onLeaderLudoAddaComplete(LudoAddaMainResponse responseModel) {

    }

    @Override
    public void onLeaderLudoAdda(ApiError error) {

    }

    @Override
    public void onLeaderWSComplete(AllLeaderBoardResponse responseModel) {

    }

    @Override
    public void onLeaderWS(ApiError error) {

    }

    @Override
    public void onLeaderDroidComplete(AllLeaderBoardResponse responseModel) {

    }

    @Override
    public void onLeaderDroidError(ApiError error) {

    }

    @Override
    public void onAllLeaderBoardComplete(LeaderboardMainResponse responseModel) {

    }

    @Override
    public void onAllLeaderBoardError(ApiError error) {

    }


    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

            if (!isLoading && !isLastPage) {
                //if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE)
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && mItemCount >= PAGE_SIZE) {
                    loadMoreItems();
                }
            }
        }
    };

    private void loadMoreItems() {
        //show bottom progress bar
        isLoading = true;
        getData();
    }

}