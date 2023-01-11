package com.khiladiadda.network.model.response.droid_doresponse;

import com.khiladiadda.gameleague.interfaces.IParticipantsPresenter;
import com.khiladiadda.gameleague.interfaces.IParticipantsView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;

import rx.Subscription;

public class ParticipantsPresenter implements IParticipantsPresenter {
    private IParticipantsView mParticipantsView;
    private ParticipantsInteractor mParticipantsInteractor;
    private Subscription mSubscription;

    public ParticipantsPresenter(IParticipantsView mParticipantsView) {
        this.mParticipantsView = mParticipantsView;
        mParticipantsInteractor = new ParticipantsInteractor();
    }

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void getParticipantsData(String id) {
        mSubscription = mParticipantsInteractor.getParticipants(mParticipantsListener, id);
    }

    private IApiListener<GameParticipantsDataResponse> mParticipantsListener = new IApiListener<GameParticipantsDataResponse>() {
        @Override
        public void onSuccess(GameParticipantsDataResponse response) {
            mParticipantsView.onParticipantsSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mParticipantsView.onParticipantsFailure(error);
        }
    };

}
