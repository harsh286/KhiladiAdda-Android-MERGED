package com.khiladiadda.gameleague;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.gameleague.adapter.GameDroidoHistoryAdapter;
import com.khiladiadda.gameleague.interfaces.IDridoHistoryView;
import com.khiladiadda.gameleague.interfaces.IOnGamesClickListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.droid_doresponse.DridoHistoryPresenter;
import com.khiladiadda.network.model.response.droid_doresponse.DroidoHistoryGameList;
import com.khiladiadda.network.model.response.droid_doresponse.GameHistoryDroido;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GameDroidoHistoryActivity extends BaseActivity implements IDridoHistoryView, IOnGamesClickListener {
    @BindView(R.id.tv_error_show)
    TextView tvErrorHistory;
    @BindView(R.id.iv_title_name)
    TextView tvTitleToolbar;
    @BindView(R.id.iv_cross_arrow)
    MaterialCardView imgClose;
    @BindView(R.id.rv_history)
    RecyclerView rvDroidoHistory;
    private DridoHistoryPresenter mPresenter;
    private List<GameHistoryDroido> mGameHistoryDroidoList;
    private GameDroidoHistoryAdapter mGameDroidoHistoryAdapter;
    private boolean isLoading, isLastPage;
    private int mCurrentPage = 0;
    private LinearLayoutManager mLayoutManger;

    @Override
    protected int getContentView() {
        return R.layout.activity_game_history_actvivity;
    }

    @Override
    protected void initViews() {
        imgClose.setOnClickListener(this);
        tvTitleToolbar.setText(getString(R.string.history));
    }

    @Override
    protected void initVariables() {
        mPresenter = new DridoHistoryPresenter(this);
        mGameHistoryDroidoList = new ArrayList<>();
        mGameDroidoHistoryAdapter = new GameDroidoHistoryAdapter(mGameHistoryDroidoList, this, this);
        mLayoutManger = new LinearLayoutManager(this);
        rvDroidoHistory.setLayoutManager(mLayoutManger);
        rvDroidoHistory.setAdapter(mGameDroidoHistoryAdapter);
        // rvDroidoHistory.addOnScrollListener(mRecyclerViewOnScrollListener);
        getDroidoHistoryGameList();
    }

    private void getDroidoHistoryGameList() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getDroidoHistoryData();
        } else {
            Snackbar.make(rvDroidoHistory, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_cross_arrow) {
            finish();
        }
    }

    @Override
    public void onDroidoHistoryListFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onDroidoHistoryListSuccess(DroidoHistoryGameList response) {
        hideProgress();
//        if (response.getGameHistoryDroidoList().size() > 0) {
//            hideProgress();
//            if (response.getGameHistoryDroidoList().size() > 0) {
//                if (mCurrentPage == 0) {
//                    mGameHistoryDroidoList.clear();
//                }
//                if (mCurrentPage == 0 && response.getGameHistoryDroidoList().size() <= 0) {
//                    tvErrorHistory.setVisibility(View.VISIBLE);
//                } else if (response.getGameHistoryDroidoList().size() > 0) {
//                    mGameHistoryDroidoList.addAll(response.getGameHistoryDroidoList());
//                    mGameDroidoHistoryAdapter.notifyDataSetChanged();
//                }
//                isLoading = false;
//                mCurrentPage++;
//                if (response.getGameHistoryDroidoList().size() < PAGE_SIZE) {
//                    isLastPage = true;
//                }
//            }
//        } else {
//            tvErrorHistory.setVisibility(View.VISIBLE);
//        }
        mGameHistoryDroidoList.clear();
        mGameHistoryDroidoList.addAll(response.getGameHistoryDroidoList());
        mGameDroidoHistoryAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(int position) {

    }

//    private void loadMoreItems() {
//        //show bottom progress bar
//        isLoading = true;
//        getDroidoHistoryGameList();
//    }
//    private final RecyclerView.OnScrollListener mRecyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
//        @Override
//        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//            super.onScrollStateChanged(recyclerView, newState);
//        }
//
//        @Override
//        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//            super.onScrolled(recyclerView, dx, dy);
//            int visibleItemCount = mLayoutManger.getChildCount();
//            int totalItemCount = mLayoutManger.getItemCount();
//            int firstVisibleItemPosition = mLayoutManger.findFirstVisibleItemPosition();
//
//            if (!isLoading && !isLastPage) {
//                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= AppConstant.PAGE_SIZE) {
//                    loadMoreItems();
//                }
//            }
//        }
//    };
}
