package com.khiladiadda.quiz.splash;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.quiz.splash.interfaces.IQuizSplashPresenter;
import com.khiladiadda.quiz.splash.interfaces.IQuizSplashView;
import com.khiladiadda.network.model.response.QuizQuestionResponse;

import rx.Subscription;

public class QuizSplashPresenter implements IQuizSplashPresenter {

    private IQuizSplashView mView;
    private QuizSplashInteractor mInteractor;
    private Subscription mQuestionsSubscription;

    public QuizSplashPresenter(IQuizSplashView mView) {
        this.mView = mView;
        mInteractor = new QuizSplashInteractor();
    }

//    @Override
//    public void getQuestions(String id) {
//        mQuestionsSubscription = mInteractor.getQuestions(id, mCategoryApiListener);
//    }
//
//    private IApiListener<QuizQuestionResponse> mCategoryApiListener = new IApiListener<QuizQuestionResponse>() {
//        @Override
//        public void onSuccess(QuizQuestionResponse response) {
//            mView.onQuestionsComplete(response);
//        }
//
//        @Override
//        public void onError(ApiError error) {
//            mView.onQuestionsFailure(error);
//        }
//    };

    @Override
    public void getQuizTime(String id) {
        mQuestionsSubscription = mInteractor.getQuizTime(id, mCategoryApiListener);
    }

    private IApiListener<BaseResponse> mCategoryApiListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onQuizTimeComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onQuizTimeFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mQuestionsSubscription != null && !mQuestionsSubscription.isUnsubscribed()) {
            mQuestionsSubscription.unsubscribe();
        }
    }
}
