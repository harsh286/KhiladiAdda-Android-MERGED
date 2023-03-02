package com.khiladiadda.gamercash.ip;

import com.khiladiadda.gamercash.interfaces.IGamerCashView;
import com.khiladiadda.gamercash.interfaces.IVerifyingGamerCashUserPresenter;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.GamerCashResponse;

import rx.Subscription;

public class GamerCashUserPresenter implements IVerifyingGamerCashUserPresenter {
    private IGamerCashView mGamerCashView;
    private GamerCashUserInteractor mGamerCashUserInteractor;
    private Subscription mSubscription;

    public GamerCashUserPresenter(IGamerCashView mGamerCashView) {
        this.mGamerCashView = mGamerCashView;
        mGamerCashUserInteractor = new GamerCashUserInteractor();
    }

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void getGamerCashUserData() {
        mSubscription = mGamerCashUserInteractor.getGamerCash(mGamerCashUserListener);
    }


    private IApiListener<GamerCashResponse> mGamerCashUserListener = new IApiListener<GamerCashResponse>() {
        @Override
        public void onSuccess(GamerCashResponse response) {
            mGamerCashView.onGamerCashSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mGamerCashView.onGamerCashFailure(error);
        }
    };


}
