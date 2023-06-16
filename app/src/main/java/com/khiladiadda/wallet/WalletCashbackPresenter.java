package com.khiladiadda.wallet;

import android.text.TextUtils;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.BajajPayRemainingRequest;
import com.khiladiadda.network.model.response.InvoiceResponse;
import com.khiladiadda.network.model.response.ProfileTransactionResponse;
import com.khiladiadda.network.model.response.RemainingLimitResponse;
import com.khiladiadda.network.model.response.VersionResponse;
import com.khiladiadda.wallet.interfaces.IWalletCashbackPresenter;
import com.khiladiadda.wallet.interfaces.IWalletCashbackView;

import rx.Subscription;

public class WalletCashbackPresenter implements IWalletCashbackPresenter {

    private IWalletCashbackView mView;
    private WalletCashbackInteractor mInteractor;
    private Subscription mSubscription;

    public WalletCashbackPresenter(IWalletCashbackView view) {
        mView = view;
        mInteractor = new WalletCashbackInteractor();
    }

    @Override
    public void validateData() {
        String amount = mView.getAmount();
        if (TextUtils.isEmpty(amount) || amount.equalsIgnoreCase("0")) {
            mView.onValidationFailure("Please add amount to proceed.\nMinimum amount to be added is Rs.10");
            return;
        }
        int amounts = Integer.parseInt(amount);
        if (amounts < 10) {
            mView.onValidationFailure("Amount cannot be less than Rs.10\nMinimum amount to be added is Rs.10");
            return;
        }
        mView.onValidationComplete();
    }

    @Override
    public void getProfile() {
        mSubscription = mInteractor.getProfile(mProfileApiListener);
    }

    private IApiListener<ProfileTransactionResponse> mProfileApiListener = new IApiListener<ProfileTransactionResponse>() {
        @Override
        public void onSuccess(ProfileTransactionResponse response) {
            mView.onProfileComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onProfileFailure(error);
        }
    };

    @Override
    public void getVersionDetails() {
        mSubscription = mInteractor.getVersionDetails(mVersionAPIListener);
    }

    private IApiListener<VersionResponse> mVersionAPIListener = new IApiListener<VersionResponse>() {
        @Override
        public void onSuccess(VersionResponse response) {
            mView.onVersionSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onVersionFailure(error);
        }
    };

    @Override
    public void getInvoice(String id) {
        mSubscription = mInteractor.getInvoice(mInvoiceApiListener, id);
    }

    private IApiListener<InvoiceResponse> mInvoiceApiListener = new IApiListener<InvoiceResponse>() {
        @Override
        public void onSuccess(InvoiceResponse response) {
            mView.onInvoiceComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onInvoiceFailure(error);
        }
    };

    @Override
    public void applyCoupon(String couponCode) {
        mSubscription = mInteractor.applyCoupon(mCouponApiListener, couponCode);
    }

    private IApiListener<BaseResponse> mCouponApiListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onApplyCouponComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onApplyCouponFailure(error);
        }
    };

    @Override
    public void getRemainingLimit() {
        mSubscription = mInteractor.getRemainingLimit(mRemainingLimitApiListener);
    }

    private IApiListener<RemainingLimitResponse> mRemainingLimitApiListener = new IApiListener<RemainingLimitResponse>() {
        @Override
        public void onSuccess(RemainingLimitResponse response) {
            mView.onRemainingLimitComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onRemainingLimitFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}