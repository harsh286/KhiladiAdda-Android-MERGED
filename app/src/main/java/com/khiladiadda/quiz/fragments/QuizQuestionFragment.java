package com.khiladiadda.quiz.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseFragment;
import com.khiladiadda.network.model.response.QuestionDetails;
import com.khiladiadda.network.model.response.QuestionOptions;
import com.khiladiadda.quiz.QuizQuestionActivity;
import com.khiladiadda.utility.AppConstant;

import java.util.List;

import butterknife.BindView;

public class QuizQuestionFragment extends BaseFragment {

    private static final int COUNTDOWN_TIME = 30;

    @BindView(R.id.tv_quiz_question) TextView mQuizQuestionTV;
    @BindView(R.id.tv_quiz_option_1) TextView mQuizOption1TV;
    @BindView(R.id.tv_quiz_option_2) TextView mQuizOption2TV;
    @BindView(R.id.tv_quiz_option_3) TextView mQuizOption3TV;
    @BindView(R.id.tv_quiz_option_4) TextView mQuizOption4TV;
    @BindView(R.id.tv_quiz_timer) TextView mQuizTimerTV;
    @BindView(R.id.pb_quiz_timer) ProgressBar mQuizTimerPB;
    @BindView(R.id.tv_quiz_question_number) TextView mQuestionNumberTV;

    private QuestionOptions mSelectedOption;
    private QuestionDetails mQuestionDetails;
    private int mTimerValue = COUNTDOWN_TIME;
    private Handler mHandler = new Handler();
    private int mCurrentQuestionNumber, mTotalQuestions;

    public QuizQuestionFragment() {
    }

    public static Fragment getInstance(QuestionDetails questionDetails, String quizId, int fragmentIndex, int totalFragments) {
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.EXTRA_CURRENT_INDEX, fragmentIndex);
        bundle.putInt(AppConstant.EXTRA_TOTAL, totalFragments);
        bundle.putParcelable(AppConstant.DATA, questionDetails);
        bundle.putString(AppConstant.EXTRA_QUIZ_ID, quizId);
        QuizQuestionFragment fragment = new QuizQuestionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override protected int getContentView() {
        return R.layout.fragment_quiz_question;
    }

    @Override protected void initViews(View view) {
        mQuizOption1TV.setOnClickListener(this);
        mQuizOption2TV.setOnClickListener(this);
        mQuizOption3TV.setOnClickListener(this);
        mQuizOption4TV.setOnClickListener(this);
    }

    @Override protected void initBundle(Bundle bundle) {
        mQuestionDetails = bundle.getParcelable(AppConstant.DATA);
        mCurrentQuestionNumber = bundle.getInt(AppConstant.EXTRA_CURRENT_INDEX);
        mTotalQuestions = bundle.getInt(AppConstant.EXTRA_TOTAL);
    }

    @Override protected void initVariables() {
        mQuizQuestionTV.setText(mQuestionDetails.getTitle());
        mQuizOption1TV.setText(mQuestionDetails.getOptions().get(0).getOption());
        mQuizOption2TV.setText(mQuestionDetails.getOptions().get(1).getOption());
        mQuizOption3TV.setText(mQuestionDetails.getOptions().get(2).getOption());
        mQuizOption4TV.setText(mQuestionDetails.getOptions().get(3).getOption());
        mQuestionNumberTV.setText(String.format(getString(R.string.format_quiz_question_number), mCurrentQuestionNumber, mTotalQuestions));
    }

    public QuestionOptions getSelectedAnswer() {
        return mSelectedOption;
    }

    public void startUpdateQuizTimer() {
        mHandler.postDelayed(mQuizTimerRunnable, 1000);
    }

    public int getTimeTaken() {
        return COUNTDOWN_TIME - mTimerValue;
    }

    public void stopQuizTimer() {
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override public void onClick(View v) {
        mQuizOption1TV.setSelected(false);
        mQuizOption2TV.setSelected(false);
        mQuizOption3TV.setSelected(false);
        mQuizOption4TV.setSelected(false);
        List<QuestionOptions> options = mQuestionDetails.getOptions();
        switch (v.getId()) {
            case R.id.tv_quiz_option_1:
                mQuizOption1TV.setSelected(true);
                mSelectedOption = options.get(0);
                break;
            case R.id.tv_quiz_option_2:
                mQuizOption2TV.setSelected(true);
                mSelectedOption = options.get(1);
                break;
            case R.id.tv_quiz_option_3:
                mQuizOption3TV.setSelected(true);
                mSelectedOption = options.get(2);
                break;
            case R.id.tv_quiz_option_4:
                mQuizOption4TV.setSelected(true);
                mSelectedOption = options.get(3);
                break;
        }
    }

    private Runnable mQuizTimerRunnable = new Runnable() {
        @Override public void run() {
            if (mTimerValue < 0) {
                stopQuizTimer();
                ((QuizQuestionActivity) mActivity).moveToNextQuestion();
            } else {
                mQuizTimerTV.setText(String.format(getString(R.string.format_quiz_timer), mTimerValue));
                mQuizTimerPB.setProgress((COUNTDOWN_TIME - mTimerValue));
                mTimerValue--;
                startUpdateQuizTimer();
            }
        }
    };

}