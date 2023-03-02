package com.khiladiadda.wordsearch.ip;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.WordSearchLeaderBoardMainResponse;
import com.khiladiadda.wordsearch.listener.IWordSearchParticipantsPresenter;
import com.khiladiadda.wordsearch.listener.IWordSearchParticipantsView;

import rx.Subscription;

public class WordSearchQuizParticipants implements IWordSearchParticipantsPresenter {

    private IWordSearchParticipantsView mQuizParticipantView;
    private WordSearchInteractor mInteractor;
    private Subscription mTrendingSubscription;
    private String quizId;

    public WordSearchQuizParticipants(IWordSearchParticipantsView mQuizParticipantView, String quizId) {
        this.mQuizParticipantView = mQuizParticipantView;
        this.quizId = quizId;
        mInteractor = new WordSearchInteractor();
    }

    @Override
    public void getQuizParticipants() {
        mTrendingSubscription = mInteractor.getQuizParticipants(mQuizParticipantsApiListener, quizId);

    }

    private IApiListener<WordSearchLeaderBoardMainResponse> mQuizParticipantsApiListener = new IApiListener<WordSearchLeaderBoardMainResponse>() {
        @Override
        public void onSuccess(WordSearchLeaderBoardMainResponse response) {
            mQuizParticipantView.onWordSearchParticipantsComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mQuizParticipantView.onWordSearchParticipantsFailure(error);
        }
    };


    @Override
    public void destroy() {

    }
}
