package com.khiladiadda.ludoTournament.ip;

import com.khiladiadda.ludoTournament.LudoTmtInteractor;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtRoundsDetailsMainResponse;

import rx.Subscription;

public class LudoTmtRoundsPresenter implements ILudoTmtRoundsPresenter {
    private ILudoTmtRoundsView mView;
    private LudoTmtInteractor mInteractor;
    private Subscription mSubscription;

    public LudoTmtRoundsPresenter(ILudoTmtRoundsView view) {
        mView = view;
        mInteractor = new LudoTmtInteractor();
    }

    @Override
    public void getTournamentRounds(String id) {
        mSubscription = mInteractor.onLudoTournamentRound(ludoTmtRoundsMainResponseIApiListener, id);
    }

    private IApiListener<LudoTmtRoundsDetailsMainResponse> ludoTmtRoundsMainResponseIApiListener = new IApiListener<LudoTmtRoundsDetailsMainResponse>() {
        @Override
        public void onSuccess(LudoTmtRoundsDetailsMainResponse response) {
            mView.onGetRoundsTournamentComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onGetRoundsTournamentFailure(error);
        }
    };


    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
