package com.khiladiadda.clashx2.main;

import com.khiladiadda.clashx2.main.interfaces.ICxBannerPresenter;
import com.khiladiadda.clashx2.main.interfaces.ICxBannerView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.CxBannerMainResponse;

import rx.Subscription;

public class CxBannerPresenter implements ICxBannerPresenter {

    private ICxBannerView mView;
    private HTHInteractor mInteractor;
    private Subscription mSubscription;

    public CxBannerPresenter(ICxBannerView view) {
        mView = view;
        mInteractor = new HTHInteractor();
    }

    @Override
    public void getBannerResponse(String type) {
        mSubscription = mInteractor.getCxBanner(mGetBannerApiListener, type);
    }

    private IApiListener<CxBannerMainResponse> mGetBannerApiListener = new IApiListener<CxBannerMainResponse>() {
        @Override
        public void onSuccess(CxBannerMainResponse response) {
            mView.onCxBannerComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onCxBannerFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}