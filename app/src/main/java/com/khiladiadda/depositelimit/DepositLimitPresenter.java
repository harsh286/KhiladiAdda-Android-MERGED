package com.khiladiadda.depositelimit;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.request.deposite.DepositLimitRequest;
import com.khiladiadda.network.model.response.deposite.DepositLimitMainResponse;
import com.khiladiadda.network.model.response.deposite.FetchDepositLimitMainResponse;

import rx.Subscription;

public class DepositLimitPresenter implements IDepositeLimitPresenter {

    private IDepositView mView;
    private DepositInteractor mInteractor;
    private Subscription mSubscription;

    public DepositLimitPresenter(IDepositView mView) {
        this.mView = mView;
        this.mInteractor = new DepositInteractor();
    }

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void getAddDepositPresenter(DepositLimitRequest depositLimitRequest) {
        mSubscription = mInteractor.getDepositAdd(depositLimitRequest, mAddDepositLimitApiListener);
    }

    @Override
    public void getFetchDepositPresenter() {
        mSubscription = mInteractor.getFetchDepositAdd(mFetchDepositLimitApiListener);

    }

    private IApiListener<DepositLimitMainResponse> mAddDepositLimitApiListener = new IApiListener<DepositLimitMainResponse>() {
        @Override
        public void onSuccess(DepositLimitMainResponse response) {
            mView.onDepositLimitComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onDepositLimitFailure(error);
        }
    };

    private IApiListener<FetchDepositLimitMainResponse> mFetchDepositLimitApiListener = new IApiListener<FetchDepositLimitMainResponse>() {
        @Override
        public void onSuccess(FetchDepositLimitMainResponse response) {
            mView.onFetchDepositLimitComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onFetchDepositLimitFailure(error);
        }
    };

}
