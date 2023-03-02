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
    private Boolean mFrom;

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

//        if (leaderBoardList.size() > 3) {
//            if (leaderBoardList.get(0).getRank().equals(leaderBoardList.get(0).getMyRank()))
//            int myIndex = leaderBoardList.indexOf(new WordSearchLiveLeaderBoardlbResponse(mAppPreference.getProfileData().getId()));
//            WordSearchLiveLeaderBoardlbResponse myResult = leaderBoardList.get(myIndex);
//            mTopMain.setVisibility(View.VISIBLE);
//            Glide.with(this).load(leaderBoardList.get(0).getUser().getDp()).placeholder(R.drawable.profile).into(mFirstPic);
//            mFirstNameTv.setText(leaderBoardList.get(0).getUser().getName());
//            mFirstWonTv.setText("Won: " + leaderBoardList.get(0).getWinningAmount().toString() + " coins");
//            mFirstBestTime.setText("Best Time: " + timeConverter(leaderBoardList.get(0).getTimeTaken()));
//            mFirstWordCountTV.setText("Word Count:" + leaderBoardList.get(0).getRightAnswers());
//            Glide.with(this).load(leaderBoardList.get(1).getUser().getDp()).placeholder(R.drawable.profile).into(mSecondPic);
//            mSecondNameTv.setText(leaderBoardList.get(1).getUser().getName());
//            mSecondWonTv.setText("Won: " + leaderBoardList.get(1).getWinningAmount().toString() + " coins");
//            mSecondBestTime.setText("Best Time: " + timeConverter(leaderBoardList.get(1).getTimeTaken()));
//            mSecondWordCountTV.setText("Word Count:" + leaderBoardList.get(1).getRightAnswers());
//            Glide.with(this).load(leaderBoardList.get(2).getUser().getDp()).placeholder(R.drawable.profile).into(mThirdPic);
//            mThirdNameTv.setText(leaderBoardList.get(2).getUser().getName());
//            mThirdWonTv.setText("Won: " + leaderBoardList.get(2).getWinningAmount().toString() + " coins");
//            mThirdBestTime.setText("Best Time: " + timeConverter(leaderBoardList.get(2).getTimeTaken()));
//            mThirdWordCountTV.setText("Word Count:" + leaderBoardList.get(2).getRightAnswers());
//            adapter = new FinalLeaderBoardAdapter(leaderBoardList.subList(3,leaderBoardList.size()));
//            mLeaderBoardRv.setAdapter(adapter);
//            if (responseModel.getResponse().getLeaderboard().size() > 0)
//                leaderBoardList.addAll(responseModel.getResponse().getLeaderboard());
//                try {
//
//                    leaderBoardList.remove(myResult);
//                    leaderBoardList.add(0, myResult);
//                } catch (Exception e) {
//
//                }
//                adapter.notifyDataSetChanged();
//
//        } else {
//            if (responseModel.getResponse().getLeaderboard().size() > 0) {
//                leaderBoardList.addAll(responseModel.getResponse().getLeaderboard());
//                try {
//                    int myIndex = leaderBoardList.indexOf(new WordSearchLiveLeaderBoardlbResponse(mAppPreference.getProfileData().getId()));
//                    WordSearchLiveLeaderBoardlbResponse myResult = leaderBoardList.get(myIndex);
//                    leaderBoardList.remove(myResult);
//                    leaderBoardList.add(0, myResult);
//                } catch (Exception e) {
//
//                }
//                adapter.notifyDataSetChanged();
//                mTopMain.setVisibility(View.GONE);
//
//            }
//        }
        }
    }

    @Override
    public void onWordSearchLiveLeaderboardFailure(ApiError error) {
       hideProgress();

    }

    private String timeConverter(long millis) {
        return String.format("%d.%02ds",
                TimeUnit.MILLISECONDS.toSeconds(millis),
                millis -
                        (TimeUnit.MILLISECONDS.toSeconds(millis) * 1000));
    }
}