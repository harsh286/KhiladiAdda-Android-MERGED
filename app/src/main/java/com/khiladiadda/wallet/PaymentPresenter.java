package com.khiladiadda.wallet;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.BajajPayEncryptedRequest;
import com.khiladiadda.network.model.request.CashfreeChecksumRequest;
import com.khiladiadda.network.model.request.CashfreeSavePayment;
import com.khiladiadda.network.model.request.ChecksumRequest;
import com.khiladiadda.network.model.request.EaseBuzzHashRequest;
import com.khiladiadda.network.model.request.EaseBuzzSaveRequest;
import com.khiladiadda.network.model.request.LinkBajajWalletRequest;
import com.khiladiadda.network.model.request.PaySharpRequest;
import com.khiladiadda.network.model.request.PaymentRequest;
import com.khiladiadda.network.model.request.PayuChecksumRequest;
import com.khiladiadda.network.model.request.PayuSavePayment;
import com.khiladiadda.network.model.request.PhonepeCheckPaymentRequest;
import com.khiladiadda.network.model.request.PhonepeRequest;
import com.khiladiadda.network.model.request.RazorpayRequest;
import com.khiladiadda.network.model.request.UpdateBalanceRequest;
import com.khiladiadda.network.model.response.ApexPayChecksumResponse;
import com.khiladiadda.network.model.response.BajajPayGetBalanceResponse;
import com.khiladiadda.network.model.response.BajajPayResponse;
import com.khiladiadda.network.model.response.BajajPayVerifyOtpResponse;
import com.khiladiadda.network.model.response.CashfreeChecksumResponse;
import com.khiladiadda.network.model.response.ChecksumResponse;
import com.khiladiadda.network.model.response.GetGamerCashResponse;
import com.khiladiadda.network.model.response.NeokredResponse;
import com.khiladiadda.network.model.response.PaySharpResponse;
import com.khiladiadda.network.model.response.PaykunOrderResponse;
import com.khiladiadda.network.model.response.PaymentStatusRequest;
import com.khiladiadda.network.model.response.PayuChecksumResponse;
import com.khiladiadda.network.model.response.PhonePePaymentResponse;
import com.khiladiadda.network.model.response.PhonepeCheckPaymentResponse;
import com.khiladiadda.network.model.response.RazorpayOrderIdResponse;
import com.khiladiadda.network.model.response.UpdateBalanceResponse;
import com.khiladiadda.network.model.response.ZaakpayChecksumResponse;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.wallet.interfaces.IPaymentPresenter;
import com.khiladiadda.wallet.interfaces.IPaymentView;

import rx.Subscription;

public class PaymentPresenter implements IPaymentPresenter {

    private IPaymentView mView;
    private PaymentInteractor mInteractor;
    private Subscription mSubscription;

    public PaymentPresenter(IPaymentView view) {
        mView = view;
        mInteractor = new PaymentInteractor();
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
    public void getEasebuzzHash(double amount, String couponCode) {
        EaseBuzzHashRequest request = new EaseBuzzHashRequest();
        request.setAmount(amount);
        request.setCoupon(couponCode);
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


    @Override
    public void getPaymentCheckData(PhonepeCheckPaymentRequest phonepeRequest) {
        mSubscription = mInteractor.getPhonepeCheckData(mPaymentCheckApiListener, phonepeRequest);
    }

    private IApiListener<PhonepeCheckPaymentResponse> mPaymentCheckApiListener = new IApiListener<PhonepeCheckPaymentResponse>() {
        @Override
        public void onSuccess(PhonepeCheckPaymentResponse response) {
            mView.onPhonePePaymentCheckComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onPhonePePaymentCheckFailure(error);
        }
    };

    @Override
    public void getPaymentUrlData(PhonepeRequest phonepeRequest) {
        mSubscription = mInteractor.getPaymentUrlData(mPaymentApiListener, phonepeRequest);
    }

    private IApiListener<PhonePePaymentResponse> mPaymentApiListener = new IApiListener<PhonePePaymentResponse>() {
        @Override
        public void onSuccess(PhonePePaymentResponse response) {
            mView.onPhonepePaymentComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onPhonepePaymentFailure(error);
        }
    };

    @Override
    public void getGamerCashUserData() {
        mSubscription = mInteractor.getGamerData(mPayGamerCashListener);
    }

    private IApiListener<GetGamerCashResponse> mPayGamerCashListener = new IApiListener<GetGamerCashResponse>() {
        @Override
        public void onSuccess(GetGamerCashResponse response) {
            mView.onGetGamerCashSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onGetGamerCashFailure(error);
        }
    };

    @Override
    public void getCashfreeStatus(String orderId) {
        mSubscription = mInteractor.getCashfreeStatus(mCashfreeStatusListener, orderId);
    }

    private IApiListener<BaseResponse> mCashfreeStatusListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onCashfreeStatusSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onCashfreeStatusFailure(error);
        }
    };


    /**
     * GetBalance
     */
    @Override
    public void getBajajPayBalance(BajajPayEncryptedRequest bajajPayRequest) {
        mSubscription = mInteractor.getBajajPayBalance(mBajajPayBalanceListener, bajajPayRequest);
    }

    private IApiListener<BajajPayGetBalanceResponse> mBajajPayBalanceListener = new IApiListener<BajajPayGetBalanceResponse>() {
        @Override
        public void onSuccess(BajajPayGetBalanceResponse response) {
            mView.onBajajPayGetBalanceSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onBajajPayGetBalanceFailure(error);
        }
    };


    /**
     * VerifyOTP
     */
    @Override
    public void verifyOTPData(BajajPayEncryptedRequest payEncryptedRequest) {
        mSubscription = mInteractor.verifyOTP(mBajajPayListenerVerifyOtp, payEncryptedRequest);
    }

    private IApiListener<BajajPayVerifyOtpResponse> mBajajPayListenerVerifyOtp = new IApiListener<BajajPayVerifyOtpResponse>() {
        @Override
        public void onSuccess(BajajPayVerifyOtpResponse response) {
            mView.onBajajPayVerifyOTPSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onBajajPayVerifyOTPFailure(error);
        }
    };
    @Override
    public void getLinkBajajWallet(LinkBajajWalletRequest linkBajajWalletRequest) {
        mSubscription = mInteractor.linkBajajWallet(mLinkBajajWalletListener,linkBajajWalletRequest);
    }

    private IApiListener<BaseResponse>mLinkBajajWalletListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onLinkBajajSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onLinkBajajFailure(error);
        }
    };

    /**
     * otpGenerator
     */
    @Override
    public void getBajajPayData(BajajPayEncryptedRequest bajajPayRequest) {
        mSubscription = mInteractor.getBajajPayOTP(mBajajPayListener, bajajPayRequest);
    }

    private IApiListener<BajajPayResponse> mBajajPayListener = new IApiListener<BajajPayResponse>() {
        @Override
        public void onSuccess(BajajPayResponse response) {
            mView.onBajajPayGetOTPSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onBajajPayGetOTPFailure(error);
        }
    };

    /**
     * Resend Otp Request To BajajPay
     */

    @Override
    public void getResendOtp(BajajPayEncryptedRequest bajajPayEncryptedRequest) {
        mSubscription = mInteractor.getBajajPayResendOtp(mBajajPayResendOtpListener, bajajPayEncryptedRequest);
    }

    private IApiListener<BajajPayResponse> mBajajPayResendOtpListener = new IApiListener<BajajPayResponse>() {
        @Override
        public void onSuccess(BajajPayResponse response) {
            mView.onBajajPayResendOTPSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onBajajPayResendOTPFailure(error);
        }
    };

    /**
     * Payment Api for OTP on DebitTransaction BajajPay
     */
    @Override
    public void doPayBajajPayAuthOTP(BajajPayEncryptedRequest bajajPayEncryptedRequest) {
        mSubscription = mInteractor.doBajajPayment(mDoBajajPayListener, bajajPayEncryptedRequest);
    }

    private IApiListener<BajajPayResponse> mDoBajajPayListener = new IApiListener<BajajPayResponse>() {
        @Override
        public void onSuccess(BajajPayResponse response) {
            mView.onDoBajajPaymentSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onDoBajajPaymentFailure(error);
        }
    };

    /**
     * Call For DebitTransaction To BajajPay
     */

    @Override
    public void callDeBitTransaction(BajajPayEncryptedRequest bajajPayDebitTransactionRequest) {
        mSubscription = mInteractor.getBajajPayDebitResponse(mBajajPayDebitListener, bajajPayDebitTransactionRequest);
    }

    private IApiListener<BajajPayResponse> mBajajPayDebitListener = new IApiListener<BajajPayResponse>() {
        @Override
        public void onSuccess(BajajPayResponse response) {
            mView.onBajajPayDebitSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onBajajPayDebitFailure(error);
        }
    };

    /**
     * InsufficientBalance
     */
    @Override
    public void inSufficientBalance(BajajPayEncryptedRequest bajajPayInsufficientBalanceRequest) {
        mSubscription = mInteractor.getBajajPayAddMoney(mBajajPayInsufficientBalanceDebitListener, bajajPayInsufficientBalanceRequest);
    }

    private IApiListener<BajajPayResponse> mBajajPayInsufficientBalanceDebitListener = new IApiListener<BajajPayResponse>() {
        @Override
        public void onSuccess(BajajPayResponse response) {
            mView.onInsufficientBalanceBajajPaySuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onInsufficientBalanceBajajPayFailure(error);
        }
    };

    /**
     * update Balance Khiladi Adda Api
     */
    @Override
    public void updateBalance(UpdateBalanceRequest updateBalanceRequest) {
        mSubscription = mInteractor.getUpdateBalanceKhiladiBajajPay(mUpdateBalanceListener, updateBalanceRequest);
    }

    private IApiListener<UpdateBalanceResponse> mUpdateBalanceListener = new IApiListener<UpdateBalanceResponse>() {
        @Override
        public void onSuccess(UpdateBalanceResponse response) {
            mView.onUpdateBalanceKhiladiAdda(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onUpdateBalanceKhiladiAddaFailure(error);
        }
    };

    /**
     * delink Wallet BajajPay From khiladiAdda
     */
    @Override
    public void delinkWallet(BajajPayEncryptedRequest bajajPayDeLinkWalletRequest) {
        mSubscription = mInteractor.deLinkWalletBP(mBajajPaydeLinkWalletListener, bajajPayDeLinkWalletRequest);
    }

    private IApiListener<BajajPayResponse> mBajajPaydeLinkWalletListener = new IApiListener<BajajPayResponse>() {
        @Override
        public void onSuccess(BajajPayResponse response) {
            mView.onDeLinkWalletSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onDeLinkWalletFailure(error);
        }
    };

    @Override
    public void checkBajajValidation(String amount, String coupon) {
        PayuChecksumRequest request = new PayuChecksumRequest();
        request.setOrderAmount(amount);
        request.setCoupon(coupon);
        mSubscription = mInteractor.checkBajajValidation(mBajajSeverListener, request);
    }

    private IApiListener<BaseResponse> mBajajSeverListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onBajajValidationSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onBajajValidationFailure(error);
        }
    };

}