package com.khiladiadda.wordsearch.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.WordSearchLiveLeaderBoardMainResponse;
import com.khiladiadda.network.model.response.WordSearchLiveLeaderBoardlbResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.wordsearch.adapter.FinalLeaderBoardAdapter;
import com.khiladiadda.wordsearch.ip.WordSearchLiveLeaderboardPresenter;
import com.khiladiadda.wordsearch.listener.IWordSearchLiveLeaderboardPresenter;
import com.khiladiadda.wordsearch.listener.IWordSearchLiveLeaderboardView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class FinalLeaderBoardActivity extends BaseActivity implements IWordSearchLiveLeaderboardView {

    private IWordSearchLiveLeaderboardPresenter mWordSearchLiveLeaderboardPresenter;
    @BindView(R.id.tv_name)
    TextView mNameTv;
    @BindView(R.id.rv_leaderboard)
    RecyclerView mLeaderBoardRv;
    @BindView(R.id.cl_top_main)
    ConstraintLayout mTopMain;
    @BindView(R.id.iv_first_pic)
    ImageView mFirstPic;
    @BindView(R.id.iv_second_pic)
    ImageView mSecondPic;
    @BindView(R.id.iv_third_pic)
    ImageView mThirdPic;
    @BindView(R.id.tv_first_name)
    TextView mFirstNameTv;
    @BindView(R.id.tv_first_won)
    TextView mFirstWonTv;
    @BindView(R.id.tv_first_best_time)
    TextView mFirstBestTime;
    @BindView(R.id.tv_second_name)
    TextView mSecondNameTv;
    @BindView(R.id.tv_second_won)
    TextView mSecondWonTv;
    @BindView(R.id.tv_second_best_time)
    TextView mSecondBestTime;
    @BindView(R.id.tv_third_name)
    TextView mThirdNameTv;
    @BindView(R.id.tv_third_won)
    TextView mThirdWonTv;
    @BindView(R.id.tv_third_best_time)
    TextView mThirdBestTime;
    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.tv_first_word_count)
    TextView mFirstWordCountTV;
    @BindView(R.id.tv_second_word_count)
    TextView mSecondWordCountTV;
    @BindView(R.id.tv_third_word_count)
    TextView mThirdWordCountTV;
    private String mQuizId;
    private FinalLeaderBoardAdapter adapter;
    private List<WordSearchLiveLeaderBoardlbResponse> leaderBoardList = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_final_leader_board;
    }

    @Override
    protected void initViews() {
        mNameTv.setText("Final Leaderboard");
        mQuizId = getIntent().getStringExtra(AppConstant.WORD_SEARCH_QUIZ_ID);
        mWordSearchLiveLeaderboardPresenter = new WordSearchLiveLeaderboardPresenter(this, mQuizId);
        setUpRecyclerView();
    }

    @Override
    protected void initVariables() {
        mBackIv.setOnClickListener(this);
        getData();
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
            mWordSearchLiveLeaderboardPresenter.getLiveLeaderboard();
        } else {
            Toast.makeText(this, "" + R.string.error_internet, Toast.LENGTH_LONG).show();
        }
    }

    private void setUpRecyclerView() {
        mLeaderBoardRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FinalLeaderBoardAdapter(leaderBoardList);
        mLeaderBoardRv.setAdapter(adapter);
    }

    @Override
    public void onWordSearchLiveLeaderboardComplete(WordSearchLiveLeaderBoardMainResponse responseModel) {
        hideProgress();
        leaderBoardList.clear();
        if (responseModel.getResponse().getLeaderboard().size() > 0) {
            leaderBoardList.addAll(responseModel.getResponse().getLeaderboard());
            try {
                int myIndex = leaderBoardList.indexOf(new WordSearchLiveLeaderBoardlbResponse(mAppPreference.getProfileData().getId()));
                WordSearchLiveLeaderBoardlbResponse myResult = leaderBoardList.get(myIndex);
                leaderBoardList.remove(myResult);
                leaderBoardList.add(0, myResult);
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