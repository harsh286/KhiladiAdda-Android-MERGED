package com.khiladiadda.login;

import com.khiladiadda.login.interfaces.ITrueCallerPresenter;
import com.khiladiadda.login.interfaces.ITrueCallerView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;

import rx.Subscription;

public class TrueCallerPresenter implements ITrueCallerPresenter {
    private ITrueCallerView mView;
    private TrueCallerInteractor mInteractor;
    private Subscription mSubscription;

    public TrueCallerPresenter(ITrueCallerView mView) {
        this.mView = mView;
        mInteractor = new TrueCallerInteractor();
    }


    @Override
    public void callTrueCallerApi(TrueCallerRequest request) {
        mSubscription = mInteractor.getTrueCallerdata(request, mTrueCallerApiListener);
    }

    private IApiListener<TrueCallerResponse> mTrueCallerApiListener = new IApiListener<TrueCallerResponse>() {
        @Override
        public void onSuccess(TrueCallerResponse response) {
            mView.onTrueCallerCompletion(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onTrueCallerFailure(error);
        }
    };


    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}
