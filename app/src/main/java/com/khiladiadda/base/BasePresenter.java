package com.khiladiadda.base;

import com.khiladiadda.base.interfaces.IBaseVersionPresenter;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.VersionResponse;

import rx.Subscription;

public class BasePresenter implements IBaseVersionPresenter {

    private BaseActivity mView;
    private BaseInteractor mInteractor;
    private Subscription mSubscription;

    public BasePresenter(BaseActivity mView) {
        this.mView = mView;
        mInteractor = new BaseInteractor();
    }

    @Override public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override public void getVersionDetails() {
        mSubscription = mInteractor.getVersionDetails(mOpponentAPIListener);
    }

    private IApiListener<VersionResponse> mOpponentAPIListener = new IApiListener<VersionResponse>() {
        @Override public void onSuccess(VersionResponse response) {
            mView.onVersionSuccess(response);
        }

        @Override public void onError(ApiError error) {
            mView.onVersionFailure(error);
        }
    };

}