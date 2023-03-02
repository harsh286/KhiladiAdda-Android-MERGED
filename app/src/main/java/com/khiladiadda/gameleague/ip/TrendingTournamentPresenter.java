package com.khiladiadda.gameleague.ip;

import com.khiladiadda.gameleague.interfaces.ITrendingTournamentPresenter;
import com.khiladiadda.gameleague.interfaces.ITrendingTournamentView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.droid_doresponse.MyTournamentResponse;
import com.khiladiadda.network.model.response.droid_doresponse.TrendingTournamentResponse;

import rx.Subscription;

public class TrendingTournamentPresenter implements ITrendingTournamentPresenter {
    private ITrendingTournamentView mTrendingTournamentView;
    private TrendingTournamentInteractor mTrendingTournamentInteractor;
    private Subscription mSubscription;

    public TrendingTournamentPresenter(ITrendingTournamentView mTrendingTournamentView) {
        this.mTrendingTournamentView = mTrendingTournamentView;
        mTrendingTournamentInteractor = new TrendingTournamentInteractor();
    }

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    //    Trending Tournament
    @Override
    public void getTrendingTournamentData() {
        mSubscription = mTrendingTournamentInteractor.getTrendingTournament(mTrendingTournamentListener);
    }

    private IApiListener<TrendingTournamentResponse> mTrendingTournamentListener = new IApiListener<TrendingTournamentResponse>() {
        @Override
        public void onSuccess(TrendingTournamentResponse response) {
            mTrendingTournamentView.onGameTrendingTournamentSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mTrendingTournamentView.onGameTrendingTournamentFailure(error);
        }
    };

    //My Tournament
    @Override
    public void getMyTournamentData() {
        mSubscription = mTrendingTournamentInteractor.getMyTournament(mMyTournamentListener);
    }

    private IApiListener<MyTournamentResponse> mMyTournamentListener = new IApiListener<MyTournamentResponse>() {
        @Override
        public void onSuccess(MyTournamentResponse response) {
            mTrendingTournamentView.onMyTournamentSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mTrendingTournamentView.onMyTournamentFailure(error);
        }
    };

    //FilterData
    @Override
    public void getDataFilter(int v) {
        mSubscription = mTrendingTournamentInteractor.getFilterDataList(mFilterTournamentListener, v);
    }

    private IApiListener<TrendingTournamentResponse> mFilterTournamentListener = new IApiListener<TrendingTournamentResponse>() {
        @Override
        public void onSuccess(TrendingTournamentResponse response) {
            mTrendingTournamentView.getFiltersTournamentSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mTrendingTournamentView.getFiltersTournamentFailed(error);
        }
    };
}
