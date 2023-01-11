package com.khiladiadda.league.details;

import com.khiladiadda.league.details.interfaces.ILeagueDetailsPresenter;
import com.khiladiadda.league.details.interfaces.ILeagueDetailsView;
import com.khiladiadda.network.model.request.AddCredential;
import com.khiladiadda.network.model.response.CreateTeamResponse;
import com.khiladiadda.network.model.response.MyTeamResponse;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.ProfileTransactionResponse;
import com.khiladiadda.network.model.response.StartQuizResponse;

import rx.Subscription;

public class LeagueDetailsPresenter implements ILeagueDetailsPresenter {

    private ILeagueDetailsView mView;
    private LeagueDetailsInteractor mInteractor;
    private Subscription mStartSubscription, mCreateSubscription, mMyTeamSubscription;

    public LeagueDetailsPresenter(ILeagueDetailsView view) {
        mView = view;
        mInteractor = new LeagueDetailsInteractor();
    }

    @Override
    public void startLeague(AddCredential addCredential, String gameId) {
        mStartSubscription = mInteractor.startLeague(addCredential, gameId, mAddApiListener);
    }

    @Override
    public void createTeam(AddCredential addCredential, String gameId) {
        mCreateSubscription = mInteractor.createTeam(addCredential, gameId, mCreateApiListener);
    }

    @Override
    public void getMyTeam(String gameId) {
        mMyTeamSubscription = mInteractor.getMyTeam(gameId, mMyTeamApiListener);
    }

    private IApiListener<StartQuizResponse> mAddApiListener = new IApiListener<StartQuizResponse>() {
        @Override
        public void onSuccess(StartQuizResponse response) {
            mView.onStartLeagueComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onStartLeagueFailure(error);
        }
    };

    private IApiListener<CreateTeamResponse> mCreateApiListener = new IApiListener<CreateTeamResponse>() {
        @Override
        public void onSuccess(CreateTeamResponse response) {
            mView.onCreateTeamComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onCreateTeamFailure(error);
        }
    };

    private IApiListener<MyTeamResponse> mMyTeamApiListener = new IApiListener<MyTeamResponse>() {
        @Override
        public void onSuccess(MyTeamResponse response) {
            mView.onMyTeamComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onMyTeamFailure(error);
        }
    };

    @Override
    public void getProfile() {
        mStartSubscription = mInteractor.getProfile(mLoginApiListener);
    }

    private IApiListener<ProfileTransactionResponse> mLoginApiListener = new IApiListener<ProfileTransactionResponse>() {
        @Override
        public void onSuccess(ProfileTransactionResponse response) {
            mView.onProfileComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onProfileFailure(error);
        }
    };


    @Override
    public void destroy() {
        if (mStartSubscription != null && !mStartSubscription.isUnsubscribed()) {
            mStartSubscription.unsubscribe();
        }
        if (mCreateSubscription != null && !mCreateSubscription.isUnsubscribed()) {
            mCreateSubscription.unsubscribe();
        }
        if (mMyTeamSubscription != null && !mMyTeamSubscription.isUnsubscribed()) {
            mMyTeamSubscription.unsubscribe();
        }
    }

}