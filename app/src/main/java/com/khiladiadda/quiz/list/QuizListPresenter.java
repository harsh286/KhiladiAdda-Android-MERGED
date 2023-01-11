package com.khiladiadda.quiz.list;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.request.StartQuizRequest;
import com.khiladiadda.network.model.response.QuizListResponse;
import com.khiladiadda.network.model.response.StartQuizResponse;
import com.khiladiadda.network.model.response.UserQuizPlayedResponse;
import com.khiladiadda.quiz.list.interfaces.IQuizListPresenter;
import com.khiladiadda.quiz.list.interfaces.IQuizListView;

import rx.Subscription;

public class QuizListPresenter implements IQuizListPresenter {

    private IQuizListView mView;
    private QuizListInteractor mInteractor;
    private Subscription mQuizListSubscription, mStartQuizCSubscription;

    public QuizListPresenter(IQuizListView mView) {
        this.mView = mView;
        mInteractor = new QuizListInteractor();
    }

    @Override public void getQuizList(String id, boolean upcoming, boolean past, boolean live) {
        mQuizListSubscription = mInteractor.getQuizList(id, mQuizListApiListener, upcoming, past, live);
    }

    private IApiListener<QuizListResponse> mQuizListApiListener = new IApiListener<QuizListResponse>() {
        @Override public void onSuccess(QuizListResponse response) {
            mView.onQuizListComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onQuizListFailure(error);
        }
    };

    @Override public void startQuiz(String id) {
        StartQuizRequest request = new StartQuizRequest(id);
        mStartQuizCSubscription = mInteractor.startQuiz(request, mStartQuizApiListener);
    }

    private IApiListener<StartQuizResponse> mStartQuizApiListener = new IApiListener<StartQuizResponse>() {
        @Override public void onSuccess(StartQuizResponse response) {
            mView.onStartQuizComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onStartQuizFailure(error);
        }
    };

    @Override public void getUserPlayedQuiz(String id) {
        mStartQuizCSubscription = mInteractor.getUserPlayedQuiz(mUserPlayApiListener, id);
    }

    private IApiListener<UserQuizPlayedResponse> mUserPlayApiListener = new IApiListener<UserQuizPlayedResponse>() {
        @Override public void onSuccess(UserQuizPlayedResponse response) {
            mView.onUserPlayedQuizComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onUserPlayedQuizFailure(error);
        }
    };

    @Override public void destroy() {
        if (mQuizListSubscription != null && !mQuizListSubscription.isUnsubscribed()) {
            mQuizListSubscription.unsubscribe();
        }
        if (mStartQuizCSubscription != null && !mStartQuizCSubscription.isUnsubscribed()) {
            mStartQuizCSubscription.unsubscribe();
        }
    }
}
