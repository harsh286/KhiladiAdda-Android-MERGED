package com.khiladiadda.wordsearch.ip;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.wordsearch_new.WordSearchCategoryMainResponse;
import com.khiladiadda.wordsearch.listener.IWordSearchCategoriesPresenter;
import com.khiladiadda.wordsearch.listener.IWordSearchCategoryView;

import rx.Subscription;

public class WordSearchTournamentCategoriesPresenter implements IWordSearchCategoriesPresenter {
    private IWordSearchCategoryView mCategoryQuizView;
    private WordSearchInteractor mInteractor;
    private Subscription mTrendingSubscription;
    private String quizId;

    public WordSearchTournamentCategoriesPresenter(IWordSearchCategoryView mCategoryQuizView) {
        this.mCategoryQuizView = mCategoryQuizView;
        mInteractor = new WordSearchInteractor();
    }

    @Override
    public void getCategoriesTournament() {
        mTrendingSubscription = mInteractor.getTournamentCategories(mCategoriesQuizApiListener);
    }

    private IApiListener<WordSearchCategoryMainResponse> mCategoriesQuizApiListener = new IApiListener<WordSearchCategoryMainResponse>() {
        @Override
        public void onSuccess(WordSearchCategoryMainResponse response) {
            mCategoryQuizView.onWordSearchComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mCategoryQuizView.onWordSearchFailure(error);
        }
    };


    @Override
    public void destroy() {
        if (mTrendingSubscription != null && !mTrendingSubscription.isUnsubscribed()) {
            mTrendingSubscription.unsubscribe();
        }
    }

}
