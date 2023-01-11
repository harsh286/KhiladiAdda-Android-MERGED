package com.khiladiadda.wordsearch.ip;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.WordSearchCategoriesQuizzesMainResponse;
import com.khiladiadda.wordsearch.listener.IWordSearchCategoryPresenter;
import com.khiladiadda.wordsearch.listener.IWordSearchCategoryQuizView;

import rx.Subscription;

public class WordSearchCategoriesPresenter implements IWordSearchCategoryPresenter {

    private IWordSearchCategoryQuizView mCategoryQuizView;
    private WordSearchInteractor mInteractor;
    private Subscription mTrendingSubscription;
    private String quizId;

    public WordSearchCategoriesPresenter(IWordSearchCategoryQuizView mCategoryQuizView, String quizId) {
        this.mCategoryQuizView = mCategoryQuizView;
        this.quizId = quizId;
        mInteractor = new WordSearchInteractor();
    }

    @Override
    public void getCategoriesQuiz() {
        mTrendingSubscription = mInteractor.getCategoryQuiz(mCategoriesQuizApiListener, quizId);
    }

    private IApiListener<WordSearchCategoriesQuizzesMainResponse> mCategoriesQuizApiListener = new IApiListener<WordSearchCategoriesQuizzesMainResponse>() {
        @Override
        public void onSuccess(WordSearchCategoriesQuizzesMainResponse response) {
            mCategoryQuizView.onWordSearchQuizComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mCategoryQuizView.onWordSearchQuizFailure(error);
        }
    };


    @Override
    public void destroy() {
        if (mTrendingSubscription != null && !mTrendingSubscription.isUnsubscribed()) {
            mTrendingSubscription.unsubscribe();
        }
    }

}
