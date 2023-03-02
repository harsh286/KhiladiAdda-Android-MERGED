package com.khiladiadda.wordsearch.ip;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.WordSearchStartMainResponse;
import com.khiladiadda.wordsearch.listener.IWordSearchStartPresenter;
import com.khiladiadda.wordsearch.listener.IWordSearchStartView;

import rx.Subscription;

public class WordSearchStartPresenter implements IWordSearchStartPresenter {

    private IWordSearchStartView mStartView;
    private WordSearchInteractor mInteractor;
    private Subscription mTrendingSubscription;
    private String quizId;

    public WordSearchStartPresenter(IWordSearchStartView mStartView, String quizId) {
        this.mStartView = mStartView;
        this.quizId = quizId;
        mInteractor = new WordSearchInteractor();
    }

    @Override
    public void getStartQuiz() {
        mTrendingSubscription = mInteractor.getStartQuiz(mCategoriesQuizApiListener, quizId);
    }

    private IApiListener<WordSearchStartMainResponse> mCategoriesQuizApiListener = new IApiListener<WordSearchStartMainResponse>() {
        @Override
        public void onSuccess(WordSearchStartMainResponse response) {
            mStartView.onWordSearchStartComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mStartView.onWordSearchStartFailure(error);
        }
    };


    @Override
    public void destroy() {
        if (mTrendingSubscription != null && !mTrendingSubscription.isUnsubscribed()) {
            mTrendingSubscription.unsubscribe();
        }
    }

}
