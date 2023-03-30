package com.khiladiadda.wordsearch.activity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.WordSearchLeaderBoardMainResponse;
import com.khiladiadda.network.model.response.WordSearchLiveLeaderBoardMainResponse;
import com.khiladiadda.network.model.response.WordSearchLiveLeaderBoardlbResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.wordsearch.adapter.LeaderBoardAdapter;
import com.khiladiadda.wordsearch.ip.WordSearchLiveLeaderboardPresenter;
import com.khiladiadda.wordsearch.ip.WordSearchQuizParticipants;
import com.khiladiadda.wordsearch.listener.IOnClickListener;
import com.khiladiadda.wordsearch.listener.IWordSearchLiveLeaderboardPresenter;
import com.khiladiadda.wordsearch.listener.IWordSearchLiveLeaderboardView;
import com.khiladiadda.wordsearch.listener.IWordSearchParticipantsPresenter;
import com.khiladiadda.wordsearch.listener.IWordSearchParticipantsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WordSearchLeaderBoardActivity extends BaseActivity implements IOnClickListener, IWordSearchParticipantsView, IWordSearchLiveLeaderboardView {

    @BindView(R.id.rv_leaderboard)
    RecyclerView mLeaderBoardRv;
    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.tv_no_data)
    TextView mNoData;
    @BindView(R.id.tv_name)
    TextView mNameTv;
    @BindView(R.id.tv_rules)
    TextView mRulesTv;
    private String mQuizId;
    private IWordSearchParticipantsPresenter mParticipantsPresenter;
    private IWordSearchLiveLeaderboardPresenter mWordSearchLiveLeaderboardPresenter;
    private LeaderBoardAdapter adapter;
    private Boolean mFrom;
    private List<WordSearchLiveLeaderBoardlbResponse> leaderBoardList = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_leader_board;
    }

    @Override
    protected void initViews() {
        mQuizId = getIntent().getStringExtra(AppConstant.WORD_SEARCH_QUIZ_ID);
        mFrom = getIntent().getBooleanExtra(AppConstant.FROM, false);
        if (!mFrom) {
            mNameTv.setText("Participants");
            mRulesTv.setVisibility(View.GONE);
        } else {
            mNameTv.setText("Live Leaderboard");
            mRulesTv.setVisibility(View.VISIBLE);
        }
        mRulesTv.setText("1. This is not a final leaderboard.\n" +
                "2. You can see the final leaderboard once the tournament over.\n" +
                "3. Once the tournament finishes, you can see leaderboard with the amount you have won.");
        mParticipantsPresenter = new WordSearchQuizParticipants(this, mQuizId);
        mWordSearchLiveLeaderboardPresenter = new WordSearchLiveLeaderboardPresenter(this, mQuizId);
        setUpRecyclerView();
    }

    @Override
    protected void initVariables() {
        mBackIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back) {
            finish();
        }
    }

    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress("");
            if (!mFrom) {
                mParticipantsPresenter.getQuizParticipants();
            } else
                mWordSearchLiveLeaderboardPresenter.getLiveLeaderboard();
        } else {
            Toast.makeText(this, "" + R.string.error_internet, Toast.LENGTH_LONG).show();
        }
    }

    private void setUpRecyclerView() {
        mLeaderBoardRv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(int pos) {
        Log.e("TAG", "onItemClick: popop");
    }

    @Override
    public void onWordSearchParticipantsComplete(WordSearchLeaderBoardMainResponse responseModel) {
        hideProgress();
        if (responseModel.getResponse().isEmpty()) {
            mNoData.setText("There is no participants in this tournament yet. Be the one to particiapte in this tournament");
            mNoData.setVisibility(View.VISIBLE);
        } else mNoData.setVisibility(View.GONE);
        adapter = new LeaderBoardAdapter(responseModel.getResponse(), this, null, mFrom);
        mLeaderBoardRv.setAdapter(adapter);
    }

    @Override
    public void onWordSearchParticipantsFailure(ApiError error) {
        hideProgress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void onWordSearchLiveLeaderboardComplete(WordSearchLiveLeaderBoardMainResponse responseModel) {
        hideProgress();
        if (responseModel.getResponse().getLeaderboard().isEmpty()) {
            mNoData.setVisibility(View.VISIBLE);
        } else mNoData.setVisibility(View.GONE);
        leaderBoardList.clear();
        if (responseModel.getResponse().getLeaderboard().size() > 0) {
            leaderBoardList.addAll(responseModel.getResponse().getLeaderboard());
            try {
                int myIndex = leaderBoardList.indexOf(new WordSearchLiveLeaderBoardlbResponse(mAppPreference.getProfileData().getId()));
                WordSearchLiveLeaderBoardlbResponse myResult = leaderBoardList.get(myIndex);
                leaderBoardList.remove(myResult);
                leaderBoardList.add(0, myResult);
                adapter = new LeaderBoardAdapter(null, this, leaderBoardList, mFrom);
                mLeaderBoardRv.setAdapter(adapter);
            } catch (Exception e) {

            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onWordSearchLiveLeaderboardFailure(ApiError error) {
        hideProgress();
    }

}