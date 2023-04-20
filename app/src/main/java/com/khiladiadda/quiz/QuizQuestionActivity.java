package com.khiladiadda.quiz;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.customviews.NonSwipeableViewPager;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.QuestionDetails;
import com.khiladiadda.network.model.response.QuizListDetails;
import com.khiladiadda.network.model.response.QuizSubmitResponse;
import com.khiladiadda.quiz.adapters.QuizQuestionPagerAdapter;
import com.khiladiadda.quiz.fragments.QuizQuestionFragment;
import com.khiladiadda.quiz.fragments.QuizQuestionImageFragment;
import com.khiladiadda.quiz.interfaces.IQuizSubmitPresenter;
import com.khiladiadda.quiz.interfaces.IQuizSubmitView;
import com.khiladiadda.quiz.result.QuizResultActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.ImageUtils;
import com.khiladiadda.utility.NetworkStatus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class QuizQuestionActivity extends BaseActivity implements IQuizSubmitView {

    @BindView(R.id.tv_activity_name) TextView mActivityNameTV;
    @BindView(R.id.vp_quiz) NonSwipeableViewPager mQuizQuestionsVP;
    @BindView(R.id.tv_quiz_next) TextView mNextTV;
    @BindView(R.id.tv_quiz_timer) TextView mQuizTimerTV;
    @BindView(R.id.iv_sound) ImageView mSoundIV;
    private List<Fragment> mFragmentList;
    private List<QuestionDetails> mQuestionDetails;
    private IQuizSubmitPresenter mQuizSubmitPresenter;
    private Handler mHandler = new Handler();
    private int mTimerValue = 0;
    private long mQuizStartTime;
    private long mQuizTotalTimeTaken;
    private String mQuizId, mFrom;
    private QuizListDetails mQuizDetails;

    @Override protected int getContentView() {
        return R.layout.activity_quiz_question;
    }

    @Override protected void initViews() {
        mFrom = getIntent().getStringExtra(AppConstant.FROM);
        mQuizDetails = getIntent().getParcelableExtra(AppConstant.DATA_QUIZ);
        mActivityNameTV.setText(mQuizDetails.getName());
        mQuizId = mQuizDetails.getId();
        mNextTV.setOnClickListener(this);
        mSoundIV.setOnClickListener(this);
    }

    @Override protected void initVariables() {
        mQuizSubmitPresenter = new QuizSubmitPresenter(this);
        mQuestionDetails = getIntent().getParcelableArrayListExtra(AppConstant.DATA);
        mFragmentList = new ArrayList<>();
        int totalQuestions = mQuestionDetails.size();
        for (int i = 0; i < totalQuestions; i++) {
            if (mFrom.equalsIgnoreCase(AppConstant.FROM_QUIZ_QUESTION_TEXT)) {
                mFragmentList.add(QuizQuestionFragment.getInstance(mQuestionDetails.get(i), mQuizId, i + 1, totalQuestions));
            } else {
                mFragmentList.add(QuizQuestionImageFragment.getInstance(mQuestionDetails.get(i), mQuizId, i + 1, totalQuestions));
            }
        }
        QuizQuestionPagerAdapter adapter = new QuizQuestionPagerAdapter(getSupportFragmentManager(), this, mFragmentList);
        mQuizQuestionsVP.setAdapter(adapter);
        mQuizQuestionsVP.setOffscreenPageLimit(1);
        mQuizStartTime = System.currentTimeMillis();
        if (mFrom.equalsIgnoreCase(AppConstant.FROM_QUIZ_QUESTION_TEXT)) {
            ((QuizQuestionFragment) mFragmentList.get(0)).startUpdateQuizTimer();
        } else {
            ((QuizQuestionImageFragment) mFragmentList.get(0)).startUpdateQuizTimer();
        }
        startUpdateQuizTimer();
    }

    public void startUpdateQuizTimer() {
        mHandler.postDelayed(mQuizTimerRunnable, 1000);
    }

    public void stopQuizTimer() {
        mHandler.removeCallbacksAndMessages(null);
    }

    private Runnable mQuizTimerRunnable = new Runnable() {
        @Override public void run() {
            mQuizTimerTV.setText(String.format(getString(R.string.format_quiz_timer), mTimerValue));
            mTimerValue++;
            startUpdateQuizTimer();
        }
    };

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_quiz_next:
                int currentFragmentIndex = mQuizQuestionsVP.getCurrentItem();

                if (mFrom.equalsIgnoreCase(AppConstant.FROM_QUIZ_QUESTION_TEXT)) {
                    QuizQuestionFragment quizQuestionFragment = (QuizQuestionFragment) mFragmentList.get(currentFragmentIndex);
                    quizQuestionFragment.stopQuizTimer();
                    QuestionDetails questionDetails = mQuestionDetails.get(currentFragmentIndex);
                    questionDetails.setSelectedOption(quizQuestionFragment.getSelectedAnswer());
                    questionDetails.setTimeTaken(quizQuestionFragment.getTimeTaken());
                } else {
                    QuizQuestionImageFragment quizQuestionFragment = (QuizQuestionImageFragment) mFragmentList.get(currentFragmentIndex);
                    quizQuestionFragment.stopQuizTimer();
                    QuestionDetails questionDetails = mQuestionDetails.get(currentFragmentIndex);
                    questionDetails.setSelectedOption(quizQuestionFragment.getSelectedAnswer());
                    questionDetails.setTimeTaken(quizQuestionFragment.getTimeTaken());
                }
                if (currentFragmentIndex < mFragmentList.size() - 1) {
                    mQuizQuestionsVP.setCurrentItem(currentFragmentIndex + 1);
                    if (mFrom.equalsIgnoreCase(AppConstant.FROM_QUIZ_QUESTION_TEXT)) {
                        ((QuizQuestionFragment) mFragmentList.get(currentFragmentIndex + 1)).startUpdateQuizTimer();
                    } else {
                        ((QuizQuestionImageFragment) mFragmentList.get(currentFragmentIndex + 1)).startUpdateQuizTimer();
                    }
                } else {
                    mQuizTotalTimeTaken = System.currentTimeMillis() - mQuizStartTime;
                    stopQuizTimer();
                    submitQuiz();
                }
                if (mQuizQuestionsVP.getCurrentItem() == mFragmentList.size() - 1) {
                    mNextTV.setText(R.string.txt_submit);
                }
                break;
            case R.id.iv_sound:
                break;
        }
    }

    private void submitQuiz() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            if (mFrom.equalsIgnoreCase(AppConstant.FROM_QUIZ_QUESTION_IMAGE)) {
                for (QuestionDetails questionDetail : mQuestionDetails) {
                    if (!TextUtils.isEmpty(questionDetail.getImage())) {
                        ImageUtils.deleteImage(AppConstant.APP_DIRECTORY_PATH + File.separator + mQuizDetails.getId() + "_" + questionDetail.getId() + ImageUtils.getExtension(questionDetail.getImage()));
                    }
                }
            }
            mQuizSubmitPresenter.doQuizSubmit(mQuizTotalTimeTaken, mQuestionDetails, mAppPreference.getCategoryId(), mQuizId);
        } else {
            Snackbar.make(mNextTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    public void moveToNextQuestion() {
        mNextTV.performClick();
    }

    @Override public void onQuizSubmitComplete(QuizSubmitResponse responseModel) {
        hideProgress();
        Intent i = new Intent(this, QuizResultActivity.class);
        i.putExtra(AppConstant.FROM, AppConstant.QUIZ_QUESTION);
        i.putExtra(AppConstant.DATA, responseModel.getResponse());
        i.putExtra(AppConstant.DATA_QUIZ, mQuizDetails);
        i.putExtra(AppConstant.QUIZ_TYPE, mFrom);
        startActivity(i);
        finish();
    }

    @Override public void onQuizSubmitFailure(ApiError error) {
        hideProgress();
    }

    @Override protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mQuizSubmitPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() { }

}