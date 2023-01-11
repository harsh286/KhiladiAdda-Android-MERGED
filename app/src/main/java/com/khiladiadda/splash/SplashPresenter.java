package com.khiladiadda.splash;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.MasterResponse;
import com.khiladiadda.network.model.response.VersionResponse;
import com.khiladiadda.splash.interfaces.ISplashPresenter;
import com.khiladiadda.splash.interfaces.ISplashView;

import rx.Subscription;

public class SplashPresenter implements ISplashPresenter {

    private ISplashView mView;
    private SplashInteractor mInteractor;
    private Subscription mMasterSubscription;

    public SplashPresenter(ISplashView view) {
        mView = view;
        mInteractor = new SplashInteractor();
    }

    @Override public void getMasterData() {
        mMasterSubscription = mInteractor.getMaster(mMasterApiListener);
    }

    private IApiListener<MasterResponse> mMasterApiListener = new IApiListener<MasterResponse>() {
        @Override public void onSuccess(MasterResponse response) {
            mView.onMasterComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onMasterFailure(error);
        }
    };

    @Override public void getVersionDetails() {
        mMasterSubscription = mInteractor.getVersionDetails(mOpponentAPIListener);
    }

    private IApiListener<VersionResponse> mOpponentAPIListener = new IApiListener<VersionResponse>() {
        @Override public void onSuccess(VersionResponse response) {
            mView.onVersionSuccess(response);
        }

        @Override public void onError(ApiError error) {
            mView.onVersionFailure(error);
        }
    };

    @Override public void destroy() {
        if (mMasterSubscription != null && !mMasterSubscription.isUnsubscribed()) {
            mMasterSubscription.unsubscribe();
        }
    }

}