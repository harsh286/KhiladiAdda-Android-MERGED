package com.khiladiadda.ludoTournament.ip;

import com.khiladiadda.ludoTournament.LudoTmtInteractor;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtAllPastRoundsMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtRoundsDetailsMainResponse;

import rx.Subscription;

public class LudoTmtPastRoundsPresenter implements ILudoTmtPastRoundsPresenter{
    private ILudoTmtPastRoundsView mView;
    private LudoTmtInteractor mInteractor;
    private Subscription mSubscription;

    public LudoTmtPastRoundsPresenter(ILudoTmtPastRoundsView mView) {
        this.mView = mView;
        this.mInteractor = new LudoTmtInteractor();
    }

    @Override
    public void getTournamentPastRounds(String id) {
        mSubscription = mInteractor.onLudoTournamentPastRound(ludoTmtPastRoundsMainResponseIApiListener, id);

    }

    private IApiListener<LudoTmtAllPastRoundsMainResponse> ludoTmtPastRoundsMainResponseIApiListener = new IApiListener<LudoTmtAllPastRoundsMainResponse>() {
        @Override
        public void onSuccess(LudoTmtAllPastRoundsMainResponse response) {
            mView.onGetPastRoundsTournamentComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onGetPastRoundsTournamentFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
