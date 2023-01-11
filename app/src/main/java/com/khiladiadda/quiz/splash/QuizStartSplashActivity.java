package com.khiladiadda.quiz.splash;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.QuestionDetails;
import com.khiladiadda.network.model.response.QuizListDetails;
import com.khiladiadda.quiz.QuizQuestionActivity;
import com.khiladiadda.quiz.splash.interfaces.IQuizSplashPresenter;
import com.khiladiadda.quiz.splash.interfaces.IQuizSplashView;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.ImageUtils;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;

public class QuizStartSplashActivity extends BaseActivity implements IQuizSplashView {

    @BindView(R.id.iv_count)
    ImageView mCountIV;
    @BindView(R.id.iv_ready)
    ImageView mReadyIV;

    private Handler handler = new Handler();
    private int[] mImageResources = {R.drawable.start_2,R.drawable.start_one, R.drawable.go};
    private int mCount = 0, mImageCount = 0;
    private ArrayList<QuestionDetails> mQuestionDetails;
    private boolean mCountDownComplete;
    private QuizListDetails mQuizDetails;
    private IQuizSplashPresenter mPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_quiz_start_splash;
    }

    @Override
    protected void initViews() {
        mPresenter = new QuizSplashPresenter(this);
        mQuizDetails = getIntent().getParcelableExtra(AppConstant.DATA);
        mQuestionDetails = getIntent().getParcelableArrayListExtra(AppConstant.DATA_QUESTION);
        if (getIntent().getBooleanExtra(AppConstant.FROM_QUIZ_QUESTION_IMAGE, false)) {
            downloadBitmapFromURL();
        } else {
            updateQuizTimer();
        }
    }

    private void updateQuizTimer() {
        handler.postDelayed(mRunnable, 2000);
    }

    @Override
    protected void initVariables() {
    }

    @Override
    public void onClick(View v) {
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mCount < mImageResources.length) {
                mCountIV.setImageResource(mImageResources[mCount]);
                mCount++;
                updateQuizTimer();
            } else {
                mCountDownComplete = true;
                updateTime();
//                launchQuiz();
                handler.removeCallbacksAndMessages(null);
            }
        }
    };

    private void downloadBitmapFromURL() {
        showProgress(getString(R.string.progres_loading_quiz));
        for (QuestionDetails questionDetail : mQuestionDetails) {
            //            Log.e("starting download ", "->" + questionDetail.getImage());
            Glide.with(this).asBitmap().load(questionDetail.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(new CustomTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    ImageUtils.saveImageToFile(resource, AppConstant.APP_DIRECTORY_PATH + File.separator + mQuizDetails.getId() + "_" + questionDetail.getId() + ImageUtils.getExtension(questionDetail.getImage()));
                    mImageCount++;
                    //                    Log.e("img count" + mImageCount, "ques size" + mQuestionDetails.size());
                    if (mImageCount >= mQuestionDetails.size()) {
                        hideProgress();
                        updateQuizTimer();
                    }
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {
                }
            });
        }
    }

    private void launchQuiz() {
        if (mCountDownComplete && mQuestionDetails != null) {
            Intent intent = new Intent(QuizStartSplashActivity.this, QuizQuestionActivity.class);
            intent.putParcelableArrayListExtra(AppConstant.DATA, mQuestionDetails);
            intent.putExtra(AppConstant.DATA_QUIZ, mQuizDetails);
            if (mImageCount > 0) {
                intent.putExtra(AppConstant.FROM, AppConstant.FROM_QUIZ_QUESTION_IMAGE);
            } else {
                intent.putExtra(AppConstant.FROM, AppConstant.FROM_QUIZ_QUESTION_TEXT);
            }
            startActivity(intent);
            finish();
        }
    }

    private void updateTime() {
        mPresenter.getQuizTime(mQuizDetails.getId());
    }

    @Override
    public void onQuizTimeComplete(BaseResponse responseModel) {
        launchQuiz();
    }

    @Override
    public void onQuizTimeFailure(ApiError error) {
        launchQuiz();
    }


    @Override
    public void onBackPressed() {
    }

}