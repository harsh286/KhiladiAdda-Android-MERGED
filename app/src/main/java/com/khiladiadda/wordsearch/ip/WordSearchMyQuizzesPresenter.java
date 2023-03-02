package com.khiladiadda.wordsearch.ip;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.WordSearchMyQuizzesMainResponse;
import com.khiladiadda.wordsearch.listener.IWordSearchMyQuizzesPresenter;
import com.khiladiadda.wordsearch.listener.IWordSearchMyQuizzesView;

import rx.Subscription;

public class WordSearchMyQuizzesPresenter implements IWordSearchMyQuizzesPresenter {
    private IWordSearchMyQuizzesView mMyQuizzesView;
    private WordSearchInteractor mInteractor;
    private Subscription mTrendingSubscription;

    public WordSearchMyQuizzesPresenter(IWordSearchMyQuizzesView mMyQuizzesView) {
        this.mMyQuizzesView = mMyQuizzesView;
        mInteractor = new WordSearchInteractor();
    }

    @Override
    public void getMyQuizzes() {
        mTrendingSubscription = mInteractor.getMyQuizzes(mCategoriesQuizApiListener);
    }

    private IApiListener<WordSearchMyQuizzesMainResponse> mCategoriesQuizApiListener = new IApiListener<WordSearchMyQuizzesMainResponse>() {
        @Override
        public void onSuccess(WordSearchMyQuizzesMainResponse response) {
            mMyQuizzesView.onWordSearchMyQuizzesComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mMyQuizzesView.onWordSearchMyQuizzesFailure(error);
        }
    };


    @Override
    public void destroy() {
        if (mTrendingSubscription != null && !mTrendingSubscription.isUnsubscribed()) {
            mTrendingSubscription.unsubscribe();
        }
    }

}
