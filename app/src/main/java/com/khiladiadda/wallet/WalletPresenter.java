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
import com.khiladiadda.network.model.request.RazorpayRequest;
import com.khiladiadda.network.model.request.VoucherRequest;
import com.khiladiadda.network.model.response.ApexPayChecksumResponse;
import com.khiladiadda.network.model.response.CashfreeChecksumResponse;
import com.khiladiadda.network.model.response.ChecksumResponse;
import com.khiladiadda.network.model.response.InvoiceResponse;
import com.khiladiadda.network.model.response.NeokredResponse;
import com.khiladiadda.network.model.response.PaySharpResponse;
import com.khiladiadda.network.model.response.PaykunOrderResponse;
import com.khiladiadda.network.model.response.PaymentStatusRequest;
import com.khiladiadda.network.model.response.PayuChecksumResponse;
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
    public void getPaytmChecksum(String orderId, String amount, String callbackURL, String couponCode) {
        ChecksumRequest request = new ChecksumRequest(AppSharedPreference.getInstance().getProfileData().getId(), amount, AppSharedPreference.getInstance().getEmail(), AppSharedPreference.getInstance().getMobile(), orderId, callbackURL, couponCode);
        mSubscription = mInteractor.getPaytmChecksumhash(request, mPaytmChecksumApiListener);
    }

    @Override
    public void savePaytmPayment(PaymentRequest request) {
        mSubscription = mInteractor.savePaytmPayment(request, mPaytmPaymentApiListener);
    }

    @Override
    public void saveCashfreePayment(CashfreeSavePayment cashfreeSavePayment) {
        mSubscription = mInteractor.saveCashfreePayment(cashfreeSavePayment, mCashfreeSaveApiListener);
    }

    @Override
    public void getProfile() {
        mSubscription = mInteractor.getProfile(mLoginApiListener);
    }

    @Override
    public void getCashfreeChecksum(String amount, String couponCode) {
        CashfreeChecksumRequest request = new CashfreeChecksumRequest();
        request.setOrderAmount(Long.parseLong(amount));
        request.setCoupon(couponCode);
        mSubscription = mInteractor.getCashfreeChechsumhash(request, mCashfreeChecksumApiListener);
    }

    @Override
    public void getPayuChecksum(String amount, String couponCode) {
        PayuChecksumRequest request = new PayuChecksumRequest();
        request.setOrderAmount(amount + ".0");
        mSubscription = mInteractor.getPayuChechsumhash(request, mPayuChecksumApiListener);
    }

    @Override
    public void savePayuPayment(PayuSavePayment payuSavePayment) {
        mSubscription = mInteractor.savePayuPayment(payuSavePayment, mPayuSaveApiListener);
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

    private IApiListener<ChecksumResponse> mPaytmChecksumApiListener = new IApiListener<ChecksumResponse>() {
        @Override
        public void onSuccess(ChecksumResponse response) {
            mView.onPaytmChecksumComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onPaytmChecksumFailure(error);
        }
    };

    private IApiListener<BaseResponse> mPaytmPaymentApiListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onPaytmPaymentComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onPaytmPaymentFailure(error);
        }
    };

    private IApiListener<BaseResponse> mCashfreeSaveApiListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onCashfreePaymentComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onCashfreePaymentFailure(error);
        }
    };

    private IApiListener<CashfreeChecksumResponse> mCashfreeChecksumApiListener = new IApiListener<CashfreeChecksumResponse>() {
        @Override
        public void onSuccess(CashfreeChecksumResponse response) {
            mView.onCashfreeChecksumComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onCashfreeChecksumFailure(error);
        }
    };

    private IApiListener<PayuChecksumResponse> mPayuChecksumApiListener = new IApiListener<PayuChecksumResponse>() {
        @Override
        public void onSuccess(PayuChecksumResponse response) {
            mView.onPayuChecksumComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onPayuChecksumFailure(error);
        }
    };

    private IApiListener<BaseResponse> mPayuSaveApiListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onPayuPaymentComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onPayuPaymentFailure(error);
        }
    };

    @Override
    public void getVersionDetails() {
        mSubscription = mInteractor.getVersionDetails(mOpponentAPIListener);
    }

    private IApiListener<VersionResponse> mOpponentAPIListener = new IApiListener<VersionResponse>() {
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
    public void getRazorPayOrderId(String amount, String couponCode) {
        CashfreeChecksumRequest request = new CashfreeChecksumRequest();
        request.setOrderAmount(Long.parseLong(amount));
        request.setCoupon(couponCode);
        mSubscription = mInteractor.getRazorpay(request, mRazorpayOrderIdAPIListener);
    }

    @Override
    public void saveRazorPayment(RazorpayRequest payment) {
        mSubscription = mInteractor.saveRazorpay(payment, mRazorpayPaymentAPIListener);
    }

    private IApiListener<RazorpayOrderIdResponse> mRazorpayOrderIdAPIListener = new IApiListener<RazorpayOrderIdResponse>() {
        @Override
        public void onSuccess(RazorpayOrderIdResponse response) {
            mView.onRazorpayOrderIdComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onRazorpayOrderIdFailure(error);
        }
    };

    private IApiListener<BaseResponse> mRazorpayPaymentAPIListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onRazorpayPaymentComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onRazorpayPaymentFailure(error);
        }
    };

    @Override
    public void getPaykunOrderId(String amount, String couponCode) {
        CashfreeChecksumRequest request = new CashfreeChecksumRequest();
        request.setOrderAmount(Long.parseLong(amount));
        request.setCoupon(couponCode);
        mSubscription = mInteractor.getPaykunOrderId(request, mPaykunOrderIdAPIListener);
    }

    @Override
    public void savePaykunPayment(String paymentId, String orderId, String couponCode) {
        mSubscription = mInteractor.savePaykunPayment(paymentId, orderId, couponCode, mPaykunPaymentAPIListener);
    }

    private IApiListener<PaykunOrderResponse> mPaykunOrderIdAPIListener = new IApiListener<PaykunOrderResponse>() {
        @Override
        public void onSuccess(PaykunOrderResponse response) {
            mView.onPaykunOrderIdComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onPaykunOrderIdFailure(error);
        }
    };

    private IApiListener<BaseResponse> mPaykunPaymentAPIListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onPaykunPaymentComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onPaykunPaymentFailure(error);
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
    public void getZaakpayChecksum() {
        mSubscription = mInteractor.getZaakpayChecksumhash(mZaakpayPaymentAPIListener);
    }

    private IApiListener<ZaakpayChecksumResponse> mZaakpayPaymentAPIListener = new IApiListener<ZaakpayChecksumResponse>() {
        @Override
        public void onSuccess(ZaakpayChecksumResponse response) {
            mView.onZaakpayChecksumComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onZaakpayChecksumFailure(error);
        }
    };

    @Override
    public void getApexPayChecksum(String amount, String couponCode) {
        mSubscription = mInteractor.getApexPayChecksumhash(amount, couponCode, mApexPayPaymentAPIListener);
    }

    private IApiListener<ApexPayChecksumResponse> mApexPayPaymentAPIListener = new IApiListener<ApexPayChecksumResponse>() {
        @Override
        public void onSuccess(ApexPayChecksumResponse response) {
            mView.onApexPayChecksumComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onApexPayChecksumFailure(error);
        }
    };

    @Override
    public void getApexPayStatus(String orderId, String couponCode) {
        PaymentStatusRequest request = new PaymentStatusRequest();
        request.setOrderId(orderId);
        request.setCoupon(couponCode);
        mSubscription = mInteractor.getApexPayStatus(mApexPayAPIListener, request);
    }

    private IApiListener<ApexPayChecksumResponse> mApexPayAPIListener = new IApiListener<ApexPayChecksumResponse>() {
        @Override
        public void onSuccess(ApexPayChecksumResponse response) {
            mView.onApexPayComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onApexPayFailure(error);
        }
    };

    @Override
    public void getPayuHash(String hash, String orderId) {
        mSubscription = mInteractor.getPayuHash(hash, orderId, mPaUHashAPIListener);
    }

    private IApiListener<PayuChecksumResponse> mPaUHashAPIListener = new IApiListener<PayuChecksumResponse>() {
        @Override
        public void onSuccess(PayuChecksumResponse response) {
            mView.onPayUHashComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onPayUHashFailure(error);
        }
    };

    @Override
    public void getPaySharp(PaySharpRequest request, String couponCode) {
        mSubscription = mInteractor.getPaySharp(request, mPaySharpAPIListener, couponCode);
    }

    private IApiListener<PaySharpResponse> mPaySharpAPIListener = new IApiListener<PaySharpResponse>() {
        @Override
        public void onSuccess(PaySharpResponse response) {
            mView.onPaySharpComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onPaySharpFailure(error);
        }
    };

    @Override
    public void getPaySharpStatus(String orderId, String couponCode) {
        PaymentStatusRequest request = new PaymentStatusRequest();
        request.setOrderId(orderId);
        request.setCoupon(couponCode);
        mSubscription = mInteractor.getPaySharpStatus(request, mPaySharpStatusAPIListener);
    }

    @Override
    public void getPayuCheckum(String trans, long amnt) {

    }

    private IApiListener<BaseResponse> mPaySharpStatusAPIListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onPaySharpStatusComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onPaySharpStatusFailure(error);
        }
    };

    @Override
    public void getEasebuzzHash(double amount, String coupon) {
        EaseBuzzHashRequest request = new EaseBuzzHashRequest();
        request.setAmount(amount);
        request.setCoupon(coupon);
        mSubscription = mInteractor.getEaseBuzzHash(request, mEaseBuzzAPIListener);
    }

    private IApiListener<ChecksumResponse> mEaseBuzzAPIListener = new IApiListener<ChecksumResponse>() {
        @Override
        public void onSuccess(ChecksumResponse response) {
            mView.onEaseBuzzChecksumComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onEaseBuzzChecksumFailure(error);
        }
    };

    @Override
    public void saveEasebuzzPayment(double amount, String coupon, String orderId, String status) {
        EaseBuzzSaveRequest request = new EaseBuzzSaveRequest(amount, orderId, coupon, status);
        mSubscription = mInteractor.saveEaseBuzzPayment(request, mEaseBuzzSaveAPIListener);
    }

    private IApiListener<BaseResponse> mEaseBuzzSaveAPIListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onEaseBuzzPaymentComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onEaseBuzzPaymentFailure(error);
        }
    };

    @Override
    public void checkNeokredPG(double amount, String couponCode) {
        EaseBuzzSaveRequest request = new EaseBuzzSaveRequest(amount, couponCode);
        mSubscription = mInteractor.checkNeokredPG(request, mNeokredAPIListener);
    }

    private IApiListener<NeokredResponse> mNeokredAPIListener = new IApiListener<NeokredResponse>() {
        @Override
        public void onSuccess(NeokredResponse response) {
            mView.onNeokredComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onNeokredFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}