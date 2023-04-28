package com.khiladiadda.gamercash.ip;

import com.khiladiadda.gamercash.interfaces.ISwitchGamerCashPresenter;
import com.khiladiadda.gamercash.interfaces.ISwitchGamerCashView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.gamer_cash.SwitchGamerCashRequest;
import com.khiladiadda.network.model.response.gamer_cash.SwitchGamerCashResponse;

import rx.Subscription;

public class SwitchGamerCashPresenter implements ISwitchGamerCashPresenter {
    private ISwitchGamerCashView mSwitchGamerCashView;
    private SwitchGamerCashInteractor mSwitchGamerCashInteractor;
    private Subscription mSubscription;

    public SwitchGamerCashPresenter(ISwitchGamerCashView mSwitchGamerCashView) {
        this.mSwitchGamerCashView = mSwitchGamerCashView;
        mSwitchGamerCashInteractor = new SwitchGamerCashInteractor();
    }

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void getSwitchGamerCashUserData(SwitchGamerCashRequest switchGamerCashRequest) {
        mSubscription = mSwitchGamerCashInteractor.getSwitchGamerCash(mSwitchGamerCashListener, switchGamerCashRequest);
    }

    private IApiListener<SwitchGamerCashResponse> mSwitchGamerCashListener = new IApiListener<SwitchGamerCashResponse>() {
        @Override
        public void onSuccess(SwitchGamerCashResponse response) {
            mSwitchGamerCashView.onSwitchGamerCashSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mSwitchGamerCashView.onSwitchGamerCashFailure(error);
        }
    };

}