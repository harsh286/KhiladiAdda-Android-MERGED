package com.khiladiadda.fanleague;

import com.khiladiadda.network.model.response.CricScorce;
import com.khiladiadda.network.model.response.MatchResponse;
import com.khiladiadda.fanleague.interfaces.IMyFanLeaguePresenter;
import com.khiladiadda.fanleague.interfaces.IMyFanLeagueView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.CricAPiListerner;
import com.khiladiadda.network.model.CricApiError;

import rx.Subscription;

public class MyFanLeaguePresenter implements IMyFanLeaguePresenter {

    private IMyFanLeagueView mView;
    private MyFanLeagueInteractor mInteractor;
    private Subscription mAddSubscription;

    public MyFanLeaguePresenter(IMyFanLeagueView view) {
        mView = view;
        mInteractor = new MyFanLeagueInteractor();
    }

    @Override
    public void getMyFanLeague(boolean upcoming, boolean past, boolean live) {
        mAddSubscription = mInteractor.getMyFanLeague(mAddApiListener, upcoming, past, live);
    }

    @Override
    public void getLiveScore(String api, int matchid) {
        mAddSubscription = mInteractor.getlive(mCricAPiLister, api, matchid);
    }

    private IApiListener<MatchResponse> mAddApiListener = new IApiListener<MatchResponse>() {
        @Override
        public void onSuccess(MatchResponse response) {
            mView.onMyFanLeagueComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onMyFanLeagueFailure(error);
        }
    };

    private CricAPiListerner<CricScorce> mCricAPiLister = new CricAPiListerner<CricScorce>() {
        @Override
        public void onSuccess(CricScorce response) {
            mView.onLiveScoreComplete(response);
        }

        @Override
        public void onError(CricApiError error) {
            mView.onLiveScoreFailure(error);

        }
    };

    @Override
    public void destroy() {
        if (mAddSubscription != null && !mAddSubscription.isUnsubscribed()) {
            mAddSubscription.unsubscribe();
        }
    }

}