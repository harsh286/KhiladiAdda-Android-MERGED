package com.khiladiadda.league;

import com.khiladiadda.league.interfaces.ILeagueListPresenter;
import com.khiladiadda.league.interfaces.ILeagueListView;
import com.khiladiadda.network.model.response.LeagueListReponse;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;

import rx.Subscription;

public class LeagueListPresenter implements ILeagueListPresenter {

    private ILeagueListView mView;
    private LeagueListInteractor mInteractor;
    private Subscription mAddSubscription, mDeleteSubcription ;

    public LeagueListPresenter(ILeagueListView view) {
        mView = view;
        mInteractor = new LeagueListInteractor();
    }

    @Override
    public void getGameDetails(String catId, int type,String gameId) {
        mAddSubscription = mInteractor.getGame(catId,type,mAddApiListener,gameId);
    }

    private IApiListener<LeagueListReponse> mAddApiListener = new IApiListener<LeagueListReponse>() {
        @Override
        public void onSuccess(LeagueListReponse response) {
            mView.onGameComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onGameFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mAddSubscription != null && !mAddSubscription.isUnsubscribed()) {
            mAddSubscription.unsubscribe();
        }
    }

}