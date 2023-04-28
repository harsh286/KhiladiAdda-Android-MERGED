package com.khiladiadda.wallet;

import android.text.TextUtils;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.CashfreeChecksumRequest;
import com.khiladiadda.network.model.request.CashfreeSavePayment;
import com.khiladiadda.network.model.request.ChecksumRequest;
import com.khiladiadda.network.model.request.EaseBuzzHashRequest;
import com.khiladiadda.network.model.request.EaseBuzzSaveRequest;
import com.khiladiadda.network.model.request.PaySharpRequest;
import com.khiladiadda.network.model.request.PaymentRequest;
import com.khiladiadda.network.model.request.PayuChecksumRequest;
import com.khiladiadda.network.model.request.PayuSavePayment;
import com.khiladiadda.network.model.request.PhonepeCheckPaymentRequest;
import com.khiladiadda.network.model.request.PhonepeRequest;
import com.khiladiadda.network.model.request.RazorpayRequest;
import com.khiladiadda.network.model.response.ApexPayChecksumResponse;
import com.khiladiadda.network.model.response.CashfreeChecksumResponse;
import com.khiladiadda.network.model.response.ChecksumResponse;
import com.khiladiadda.network.model.response.GetGamerCashResponse;
import com.khiladiadda.network.model.response.InvoiceResponse;
import com.khiladiadda.network.model.response.NeokredResponse;
import com.khiladiadda.network.model.response.PaySharpResponse;
import com.khiladiadda.network.model.response.PaykunOrderResponse;
import com.khiladiadda.network.model.response.PaymentStatusRequest;
import com.khiladiadda.network.model.response.PayuChecksumResponse;
import com.khiladiadda.network.model.response.PhonePePaymentResponse;
import com.khiladiadda.network.model.response.PhonepeCheckPaymentResponse;
import com.khiladiadda.network.model.response.ProfileTransactionResponse;
import com.khiladiadda.network.model.response.RazorpayOrderIdResponse;
import com.khiladiadda.network.model.response.VersionResponse;
import com.khiladiadda.network.model.response.ZaakpayChecksumResponse;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.wallet.interfaces.IWalletPresenter;
import com.khiladiadda.wallet.interfaces.IWalletView;

import rx.Subscription;

public class WalletPresenter implements IWalletPresenter {

    private IWalletView mView;
    private WalletInteractor mInteractor;
    private Subscription mSubscription;

    public WalletPresenter(IWalletView view) {
        mView = view;
        mInteractor = new WalletInteractor();
    }

    @Override
    public void getProfile() {
        mSubscription = mInteractor.getProfile(mLoginApiListener);
    }

    private IApiListener<ProfileTransactionResponse> mLoginApiListener = new IApiListener<ProfileTransactionResponse>() {
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
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}