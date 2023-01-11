package com.khiladiadda.league.myleague;

import com.khiladiadda.league.myleague.interfaces.IMyLeaguePresenter;
import com.khiladiadda.league.myleague.interfaces.IMyLeagueView;
import com.khiladiadda.network.model.response.MyLeagueResponse;
import com.khiladiadda.network.model.response.MyTeamResponse;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;

import rx.Subscription;

public class MyLeaguePresenter implements IMyLeaguePresenter {

    private IMyLeagueView mView;
    private MyLeagueInteractor mInteractor;
    private Subscription mAddSubscription, mMyTeamSubscription ;

    public MyLeaguePresenter(IMyLeagueView view) {
        mView = view;
        mInteractor = new MyLeagueInteractor();
    }

    @Override
    public void getMyLeague(String id, boolean upcoming, boolean past, boolean live) {
        mAddSubscription = mInteractor.getMyLeague(mAddApiListener, id, upcoming, past, live);
    }

    @Override
    public void getMyTeam(String gameId) {
        mMyTeamSubscription = mInteractor.getMyTeam(gameId, mMyTeamApiListener);
    }

    private IApiListener<MyLeagueResponse> mAddApiListener = new IApiListener<MyLeagueResponse>() {
        @Override
        public void onSuccess(MyLeagueResponse response) {
            mView.onMyLeagueComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onMyLeagueFailure(error);
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
    public void destroy() {
        if (mAddSubscription != null && !mAddSubscription.isUnsubscribed()) {
            mAddSubscription.unsubscribe();
        }
        if (mMyTeamSubscription != null && !mMyTeamSubscription.isUnsubscribed()) {
            mMyTeamSubscription.unsubscribe();
        }
    }

}