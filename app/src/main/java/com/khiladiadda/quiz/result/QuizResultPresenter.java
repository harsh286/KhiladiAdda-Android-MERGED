package com.khiladiadda.quiz.result;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.quiz.result.interfaces.IQuizResultPresenter;
import com.khiladiadda.quiz.result.interfaces.IQuizResultView;
import com.khiladiadda.network.model.response.LeaderBoardResponse;
import com.khiladiadda.network.model.request.StartQuizRequest;
import com.khiladiadda.network.model.response.StartQuizResponse;

import rx.Subscription;

public class QuizResultPresenter implements IQuizResultPresenter {

    private IQuizResultView mView;
    private QuizResultInteractor mInteractor;
    private Subscription mStartQuizCSubscription, mLeaderBoardSubscription;

    public QuizResultPresenter(IQuizResultView mView) {
        this.mView = mView;
        mInteractor = new QuizResultInteractor();
    }

    @Override
    public void startQuiz(String id) {
        StartQuizRequest request = new StartQuizRequest(id);
        mStartQuizCSubscription = mInteractor.startQuiz(request, mStartQuizApiListener);
    }

    @Override
    public void getLeaderBoard(String id, int page, int limit) {
        mLeaderBoardSubscription = mInteractor.getLeaderBoard(id, mLeaderBoardApiListener, page, limit);
    }

    private IApiListener<StartQuizResponse> mStartQuizApiListener = new IApiListener<StartQuizResponse>() {
        @Override
        public void onSuccess(StartQuizResponse response) {
            mView.onStartQuizComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onStartQuizFailure(error);
        }
    };

    private IApiListener<LeaderBoardResponse> mLeaderBoardApiListener = new IApiListener<LeaderBoardResponse>() {
        @Override
        public void onSuccess(LeaderBoardResponse response) {
            mView.onLeaderBoardComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onLeaderBoardFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mStartQuizCSubscription != null && !mStartQuizCSubscription.isUnsubscribed()) {
            mStartQuizCSubscription.unsubscribe();
        }
        if (mLeaderBoardSubscription != null && !mLeaderBoardSubscription.isUnsubscribed()) {
            mLeaderBoardSubscription.unsubscribe();
        }
    }
}
