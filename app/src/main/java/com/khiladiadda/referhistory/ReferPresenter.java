package com.khiladiadda.referhistory;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.ReferResponse;
import com.khiladiadda.referhistory.interfaces.IReferPresenter;
import com.khiladiadda.referhistory.interfaces.IReferView;

import rx.Subscription;

public class ReferPresenter implements IReferPresenter {

    private IReferView mView;
    private ReferInteractor mInteractor;
    private Subscription mLikedSubscription;

    public ReferPresenter(IReferView view) {
        mView = view;
        mInteractor = new ReferInteractor();
    }

    @Override public void getRefer() {
        mLikedSubscription = mInteractor.getRefer(mLikedAPIListener);
    }

    private IApiListener<ReferResponse> mLikedAPIListener = new IApiListener<ReferResponse>() {
        @Override public void onSuccess(ReferResponse response) {
            mView.onReferComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onReferFailure(error);
        }
    };

    @Override public void destroy() {
        if (mLikedSubscription != null && !mLikedSubscription.isUnsubscribed()) {
            mLikedSubscription.unsubscribe();
        }
    }

}