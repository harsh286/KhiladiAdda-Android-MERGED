package com.khiladiadda.quiz.result;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.LeaderBoard;
import com.khiladiadda.network.model.response.LeaderBoardResponse;
import com.khiladiadda.network.model.response.QuizListDetails;
import com.khiladiadda.network.model.response.QuizSubmitResponseDetails;
import com.khiladiadda.network.model.response.StartQuizResponse;
import com.khiladiadda.quiz.result.adapter.LeaderBoardRVAdapter;
import com.khiladiadda.quiz.result.interfaces.IQuizResultPresenter;
import com.khiladiadda.quiz.result.interfaces.IQuizResultView;
import com.khiladiadda.quiz.splash.QuizStartSplashActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class QuizResultActivity extends BaseActivity implements IQuizResultView {

    @BindView(R.id.iv_back) ImageView mBackIV;
    @BindView(R.id.tv_activity_name) TextView mActivityNameTV;
    @BindView(R.id.iv_notification) ImageView mNotificationIV;
    @BindView(R.id.tv_correct) TextView mCorrectTV;
    @BindView(R.id.tv_wrong) TextView mWrongTV;
    @BindView(R.id.iv_replay) ImageView mReplayIV;
    @BindView(R.id.iv_view_prize_distribution) ImageView mViewPrizeDistributionIV;
    @BindView(R.id.iv_view_prize_money) ImageView mViewPrizeMoneyIV;
    @BindView(R.id.rv_leaderboard) RecyclerView mLeaderBoardRV;
    @BindView(R.id.ll_first) LinearLayout mScoreLL;
    @BindView(R.id.ll_second) LinearLayout mSecondLL;
    @BindView(R.id.ll_leaderboard) LinearLayout mLeaderBoardLL;
    @BindView(R.id.rv_view_leaderboard) RecyclerView mViewLeaderBoardRV;
    @BindView(R.id.tv_final) TextView mFinalTV;

    private IQuizResultPresenter mPresenter;
    private LeaderBoardRVAdapter mAdapter;
    private String mQuizId;
    private QuizListDetails mQuizDetails;
    private List<LeaderBoard> leaderBoardList;
    private boolean mQuizType;

    @Override protected int getContentView() {
        return R.layout.activity_quiz_result;
    }

    @Override protected void initViews() {
        mActivityNameTV.setText(getString(R.string.text_quiz_result));
        String mFrom = getIntent().getStringExtra(AppConstant.FROM);
        mQuizDetails = getIntent().getParcelableExtra(AppConstant.DATA_QUIZ);
        mQuizId = mQuizDetails.getId();
        if (mFrom.equalsIgnoreCase(AppConstant.QUIZ_QUESTION)) {
            QuizSubmitResponseDetails details = getIntent().getParcelableExtra(AppConstant.DATA);
            String quizType = getIntent().getStringExtra(AppConstant.QUIZ_TYPE);
            mQuizType = quizType.equalsIgnoreCase(AppConstant.FROM_QUIZ_QUESTION_IMAGE);
            mWrongTV.setText(String.valueOf(details.getWrongAnswers()));
            mCorrectTV.setText(String.valueOf(details.getRightAnswers()));
        } else {
            mActivityNameTV.setText(getString(R.string.text_quiz_leaderboard));
            mViewLeaderBoardRV.setVisibility(View.VISIBLE);
            mFinalTV.setVisibility(View.VISIBLE);
            mLeaderBoardLL.setVisibility(View.GONE);
            mScoreLL.setVisibility(View.GONE);
            mSecondLL.setVisibility(View.GONE);
        }
        if (mQuizDetails.isResultDeclared()) {
            mActivityNameTV.setText(R.string.text_final_leadeboard);
            mFinalTV.setText(R.string.text_result_declared_quiz_lederboard);
        }
    }

    @Override protected void initVariables() {
        mPresenter = new QuizResultPresenter(this);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mReplayIV.setOnClickListener(this);
        mViewPrizeDistributionIV.setOnClickListener(this);
        mViewPrizeMoneyIV.setOnClickListener(this);

        leaderBoardList = new ArrayList<>();
        mAdapter = new LeaderBoardRVAdapter(this, leaderBoardList, mQuizDetails.isResultDeclared());
        mLeaderBoardRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mLeaderBoardRV.setAdapter(mAdapter);
        mViewLeaderBoardRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mViewLeaderBoardRV.setAdapter(mAdapter);

        showProgress(getString(R.string.txt_progress_authentication));
        int mPage = 0;
        mPresenter.getLeaderBoard(mQuizId, mPage, AppConstant.PAGE_SIZE);
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.iv_replay:
                if (new NetworkStatus(this).isInternetOn()) {
                    showProgress(getString(R.string.txt_progress_authentication));
                    mPresenter.startQuiz(mQuizId);
                    Properties properties = new Properties();
                    properties
                            .addAttribute("Quiz", "Quiz Replay");
                    MoEAnalyticsHelper.INSTANCE.trackEvent(this, "Quiz", properties);
                } else {
                    Snackbar.make(mReplayIV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_view_prize_distribution:
                Intent intent = new Intent(this, PrizeBreakthroughActivity.class);
                intent.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_QUIZ);
                intent.putExtra(AppConstant.DATA_QUIZ, mQuizDetails);
                startActivity(intent);
                break;
            case R.id.iv_view_prize_money:
                View customView = LayoutInflater.from(this).inflate(R.layout.bonus_info_popup, null);
                PopupWindow mPopupWindow = new PopupWindow(customView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                if (Build.VERSION.SDK_INT >= 21) {
                    mPopupWindow.setElevation(5.0f);
                }
                mPopupWindow.setOutsideTouchable(true);
                mPopupWindow.setFocusable(true);
                mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ((TextView) customView.findViewById(R.id.tv_bonus_info)).setText("Prize amount is " + String.valueOf(mQuizDetails.getPrizemoney()) + " coins for this quiz.");
                mPopupWindow.showAsDropDown(mViewPrizeMoneyIV, (int) -(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics())), 0, Gravity.END);
                break;
        }
    }

    @Override public void onStartQuizComplete(StartQuizResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            if (responseModel.getResponse().getNAttempts() < 4) {
                mAppPreference.setIsProfile(false);
                mAppPreference.setBoolean(AppConstant.IS_QUIZ_PLAYED, true);
                Intent details = new Intent(this, QuizStartSplashActivity.class);
                details.putExtra(AppConstant.DATA, mQuizDetails);
                details.putParcelableArrayListExtra(AppConstant.DATA_QUESTION, responseModel.getQuestionDetailsArrayList());
                details.putExtra(AppConstant.FROM_QUIZ_QUESTION_IMAGE, mQuizType);
                startActivity(details);
                finish();
            } else {
                AppUtilityMethods.showMsg(this, getString(R.string.text_played_max_limit), false);
            }
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override public void onStartQuizFailure(ApiError error) {
        hideProgress();
    }

    @Override public void onLeaderBoardComplete(LeaderBoardResponse responseModel) {
        hideProgress();
        leaderBoardList.clear();
        if (responseModel.getResponse().getLeaderboard().size() > 0) {
            leaderBoardList.addAll(responseModel.getResponse().getLeaderboard());
            try {
                int myIndex = leaderBoardList.indexOf(new LeaderBoard(mAppPreference.getProfileData().getId()));
                LeaderBoard myResult = leaderBoardList.get(myIndex);
                leaderBoardList.remove(myResult);
                leaderBoardList.add(0, myResult);
            }catch (Exception e){

            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override public void onLeaderBoardFailure(ApiError error) {
        hideProgress();
    }

    @Override protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }

}