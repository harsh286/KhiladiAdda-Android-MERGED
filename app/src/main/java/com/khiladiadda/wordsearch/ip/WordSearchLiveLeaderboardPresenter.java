package com.khiladiadda.wordsearch.ip;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.WordSearchLiveLeaderBoardMainResponse;
import com.khiladiadda.wordsearch.listener.IWordSearchLiveLeaderboardPresenter;
import com.khiladiadda.wordsearch.listener.IWordSearchLiveLeaderboardView;

import rx.Subscription;

public class WordSearchLiveLeaderboardPresenter implements IWordSearchLiveLeaderboardPresenter {
    private IWordSearchLiveLeaderboardView mLiveLeaderboardView;
    private WordSearchInteractor mInteractor;
    private Subscription mTrendingSubscription;
    private String quizId;

    public WordSearchLiveLeaderboardPresenter(IWordSearchLiveLeaderboardView mLiveLeaderboardView, String quizId) {
        this.mLiveLeaderboardView = mLiveLeaderboardView;
        this.quizId = quizId;
        mInteractor = new WordSearchInteractor();
    }

    @Override
    public void getLiveLeaderboard() {
        mTrendingSubscription = mInteractor.getLiveLeaderBoard(mCategoriesQuizApiListener, quizId);
    }

    private IApiListener<WordSearchLiveLeaderBoardMainResponse> mCategoriesQuizApiListener = new IApiListener<WordSearchLiveLeaderBoardMainResponse>() {
        @Override
        public void onSuccess(WordSearchLiveLeaderBoardMainResponse response) {
            mLiveLeaderboardView.onWordSearchLiveLeaderboardComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mLiveLeaderboardView.onWordSearchLiveLeaderboardFailure(error);
        }
    };


    @Override
    public void destroy() {
        if (mTrendingSubscription != null && !mTrendingSubscription.isUnsubscribed()) {
            mTrendingSubscription.unsubscribe();
        }
    }

}
