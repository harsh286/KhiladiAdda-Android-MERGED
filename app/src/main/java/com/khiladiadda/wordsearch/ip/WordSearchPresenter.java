package com.khiladiadda.wordsearch.ip;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.WordSearchTrendingMainResponse;
import com.khiladiadda.wordsearch.listener.IWordSearchMainPresenter;
import com.khiladiadda.wordsearch.listener.IWordSearchMainView;

import rx.Subscription;

public class WordSearchPresenter implements IWordSearchMainPresenter {
    private IWordSearchMainView mView;
    private WordSearchInteractor mInteractor;
    private Subscription mTrendingSubscription;

    public WordSearchPresenter(IWordSearchMainView mView) {
        this.mView = mView;
        mInteractor = new WordSearchInteractor();
    }

    @Override
    public void getTrendingQuiz() {
        mTrendingSubscription = mInteractor.getTrending(mTrendingApiListener);
    }

    private IApiListener<WordSearchTrendingMainResponse> mTrendingApiListener = new IApiListener<WordSearchTrendingMainResponse>() {
        @Override
        public void onSuccess(WordSearchTrendingMainResponse response) {
            mView.onWordSearchQuizComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onWordSearchQuizFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mTrendingSubscription != null && !mTrendingSubscription.isUnsubscribed()) {
            mTrendingSubscription.unsubscribe();
        }
    }

}