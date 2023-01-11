package com.khiladiadda.network.model.response.droid_doresponse;

import com.khiladiadda.gameleague.interfaces.ITournamentDetailPresenter;
import com.khiladiadda.gameleague.interfaces.ITournamentDetailView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;

import rx.Subscription;

public class TournamentDetailPresenter implements ITournamentDetailPresenter {
    private ITournamentDetailView mTournamentDetailView;
    private TournamentDetailInteractor mTournamentDetailInteractor;
    private Subscription mSubscription;

    public TournamentDetailPresenter(ITournamentDetailView mTournamentDetailView) {
        this.mTournamentDetailView = mTournamentDetailView;
        mTournamentDetailInteractor = new TournamentDetailInteractor();
    }

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void getTournamentData(String id) {
        TournamentDetailRequest tournamentDetailRequest=new TournamentDetailRequest();
        tournamentDetailRequest.setTournamentId(id);
        mSubscription = mTournamentDetailInteractor.getTournamentDetail(tournamentDetailRequest,mTournamentDetailListener);
    }

    private IApiListener<TournamentDetailResponse> mTournamentDetailListener = new IApiListener<TournamentDetailResponse>() {
        @Override
        public void onSuccess(TournamentDetailResponse response) {
            mTournamentDetailView.onTournamentDetailSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mTournamentDetailView.onTournamentDetailFailure(error);
        }
    };

}
