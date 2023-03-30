package com.khiladiadda.withdrawcoins;

import android.text.TextUtils;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.AddBeneficiaryRequest;
import com.khiladiadda.network.model.request.AddBeneficieryRazorpay;
import com.khiladiadda.network.model.request.ManualWithdrawRequest;
import com.khiladiadda.network.model.request.RaceConditionPayoutRequest;
import com.khiladiadda.network.model.request.RazorpayTransferAmountRequest;
import com.khiladiadda.network.model.request.ResendOtpRequest;
import com.khiladiadda.network.model.request.SendOTPRequest;
import com.khiladiadda.network.model.request.TransferAmountRequest;
import com.khiladiadda.network.model.response.AddBeneficiaryResponse;
import com.khiladiadda.network.model.response.BeneficiaryResponse;
import com.khiladiadda.network.model.response.BeneficiaryVerifyResponse;
import com.khiladiadda.network.model.response.ManualWithdrawResponse;
import com.khiladiadda.network.model.response.OtpResponse;
import com.khiladiadda.network.model.response.PayoutResponse;
import com.khiladiadda.network.model.response.TdsResponse;
import com.khiladiadda.network.model.response.WIthdrawLimitResponse;
import com.khiladiadda.withdrawcoins.interfaces.IWithdrawPresenter;
import com.khiladiadda.withdrawcoins.interfaces.IWithdrawView;

import rx.Subscription;

public class WithdrawPresenter implements IWithdrawPresenter {

    private IWithdrawView mView;
    private WithdrawInteractor mInteractor;
    private Subscription mSubscription;

    public WithdrawPresenter(IWithdrawView view) {
        mView = view;
        mInteractor = new WithdrawInteractor();
    }

    @Override
    public void validateData() {
        String amount = mView.getAmount();
        if (TextUtils.isEmpty(amount)) {
            mView.onValidationFailure("Amount cannot be empty");
            return;
        }
        if (amount.equalsIgnoreCase("0")) {
            mView.onValidationFailure("Amount should be greater than 0");
            return;
        }
        mView.onValidationComplete();
    }

    @Override
    public void getWithdrawLimit() {
        mSubscription = mInteractor.getWithdrawLimit(mWithdrawLimitApiListener);
    }

    private IApiListener<WIthdrawLimitResponse> mWithdrawLimitApiListener = new IApiListener<WIthdrawLimitResponse>() {
        @Override
        public void onSuccess(WIthdrawLimitResponse response) {
            mView.onWithdrawLimitComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onWithdrawLimitFailed(error);
        }
    };

    @Override
    public void getBeneficiaryList() {
        mSubscription = mInteractor.getWithdraw(mGetApiListener);
    }

    @Override
    public void onCashfreeAddBeneficiary(String bankAccount, String ifscCode, String address, String transferMode, String vpa, String name, int transferType) {
        AddBeneficiaryRequest request = new AddBeneficiaryRequest();
        request.setTransferMode(transferMode);
        request.setVpa(vpa);
        request.setBankAccount(bankAccount);
        request.setIfsc(ifscCode);
        request.setAddress(address);
        request.setName(name);
        request.setTransferType(transferType);
        mSubscription = mInteractor.addCashfreeBeneficiary(request, mCashfreeAddBeneficiaryApiListener);
    }

    @Override
    public void onCashfreeTransfer(String beneficiaryId, String amount, String otp) {
        TransferAmountRequest request = new TransferAmountRequest();
        request.setAmount(amount);
        request.setOtp(otp);
        mSubscription = mInteractor.transferAmount(request, beneficiaryId, mTransferApiListener);
    }

    @Override
    public void onRazorpayAddBeneficiary(AddBeneficieryRazorpay request) {
        mSubscription = mInteractor.addRazorpayBeneficiary(request, mAddRazorpayBeneficiaryApiListener);
    }

    @Override
    public void onRazorpayTransfer(String beneficiaryId, String paymentMode, String amount, String otp, String paytmId) {
        RazorpayTransferAmountRequest request = new RazorpayTransferAmountRequest();
        request.setFundAccountId(beneficiaryId);
        request.setMode(paymentMode);
        request.setAmount(amount);
        request.setOtp(otp);
        request.setBeneId(paytmId);
        mSubscription = mInteractor.transferRazorpayAmount(request, mRazorpayTransferApiListener);
    }

    @Override
    public void deleteBeneficiary(String id) {
        mSubscription = mInteractor.deleteBeneficiary(id, mDeleteApiListener);
    }

    private IApiListener<BaseResponse> mDeleteApiListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onDeleteComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onDeleteFailed(error);
        }
    };

    private IApiListener<AddBeneficiaryResponse> mCashfreeAddBeneficiaryApiListener = new IApiListener<AddBeneficiaryResponse>() {
        @Override
        public void onSuccess(AddBeneficiaryResponse response) {
            mView.onAddCashfreeBeneficiaryComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onAddCashfreeBeneficiaryFailed(error);
        }
    };

    private IApiListener<BeneficiaryResponse> mGetApiListener = new IApiListener<BeneficiaryResponse>() {
        @Override
        public void onSuccess(BeneficiaryResponse response) {
            mView.onGetBeneficiaryListComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onGetBeneficiaryListFailure(error);
        }
    };

    private IApiListener<PayoutResponse> mTransferApiListener = new IApiListener<PayoutResponse>() {
        @Override
        public void onSuccess(PayoutResponse response) {
            mView.onCashfreeTransferComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onCashfreeTransferFailed(error);
        }
    };

    private IApiListener<AddBeneficiaryResponse> mAddRazorpayBeneficiaryApiListener = new IApiListener<AddBeneficiaryResponse>() {
        @Override
        public void onSuccess(AddBeneficiaryResponse response) {
            mView.onAddRazorpayBeneficiaryComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onAddRazorpayBeneficiaryFailed(error);
        }
    };
    private IApiListener<PayoutResponse> mRazorpayTransferApiListener = new IApiListener<PayoutResponse>() {
        @Override
        public void onSuccess(PayoutResponse response) {
            mView.onRazorpayTransferComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onRazorpayTransferFailed(error);
        }
    };

    @Override
    public void doManualWithdraw(ManualWithdrawRequest request) {
        mSubscription = mInteractor.doManualWithdraw(request, mWIthdrawApiListener);
    }

    @Override
    public void getManualWithdraw() {
        mSubscription = mInteractor.getManualWithdraw(mGetManualApiListener);
    }

    private IApiListener<BaseResponse> mWIthdrawApiListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onManualWithdrawComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onManualWithdrawFailure(error);
        }
    };

    private IApiListener<ManualWithdrawResponse> mGetManualApiListener = new IApiListener<ManualWithdrawResponse>() {
        @Override
        public void onSuccess(ManualWithdrawResponse response) {
            mView.onGetManualWithdrawComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onGetManualWithdrawFailure(error);
        }
    };

    @Override
    public void resendOtp(String mobile) {
        ResendOtpRequest request = new ResendOtpRequest(mobile);
        mSubscription = mInteractor.resendOtp(request, mResendOtpApiListener);
    }

    private IApiListener<OtpResponse> mResendOtpApiListener = new IApiListener<OtpResponse>() {
        @Override
        public void onSuccess(OtpResponse response) {
            mView.onResendOtpComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onResendOtpFailure(error);
        }
    };

    @Override
    public void sendOtp(String mobile) {
        SendOTPRequest request = new SendOTPRequest(mobile);
        mSubscription = mInteractor.sendOtp(request, mSendOtpAPIListener);
    }

    private IApiListener<BaseResponse> mSendOtpAPIListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onSendOtpComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onSendOtpFailure(error);
        }
    };

    @Override
    public void verifyBeneficiary(String beneficiaryId) {
        mSubscription = mInteractor.verifyBeneficiary(mBeneficiaryApiListener, beneficiaryId);
    }

    private IApiListener<BeneficiaryVerifyResponse> mBeneficiaryApiListener = new IApiListener<BeneficiaryVerifyResponse>() {
        @Override
        public void onSuccess(BeneficiaryVerifyResponse response) {
            mView.onVerifyBeneficiaryComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onVerifyBeneficiaryFailure(error);
        }
    };

    @Override
    public void onApexPayTransfer(String beneficiaryId, String amount, String otp) {
        mSubscription = mInteractor.onApexPayTransfer(mApexPayApiListener, beneficiaryId, amount, otp);
    }

    private IApiListener<PayoutResponse> mApexPayApiListener = new IApiListener<PayoutResponse>() {
        @Override
        public void onSuccess(PayoutResponse response) {
            mView.onApexPayTransferComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onApexPayTransferFailed(error);
        }
    };


    @Override
    public void onPaySharpTransfer(String beneficiaryId, String amount, String otp) {
        mSubscription = mInteractor.onPaySharpTransfer(mPaySharpApiListener, beneficiaryId, amount, otp);
    }

    private IApiListener<PayoutResponse> mPaySharpApiListener = new IApiListener<PayoutResponse>() {
        @Override
        public void onSuccess(PayoutResponse response) {
            mView.onPaySharpTransferComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onPaySharpTransferFailed(error);
        }
    };

    @Override
    public void onAddBeneficiary(String bankAccount, String ifscCode, String address, String transferMode, String vpa, String name, int transferType, String benType) {
        AddBeneficiaryRequest request = new AddBeneficiaryRequest();
        request.setTransferMode(transferMode);
        request.setVpa(vpa);
        request.setBankAccount(bankAccount);
        request.setIfsc(ifscCode);
        request.setAddress(address);
        request.setName(name);
        request.setTransferType(transferType);
        request.setBenType(benType);
        mSubscription = mInteractor.addBeneficiary(request, mAddBeneficiaryApiListener);
    }

    private IApiListener<AddBeneficiaryResponse> mAddBeneficiaryApiListener = new IApiListener<AddBeneficiaryResponse>() {
        @Override
        public void onSuccess(AddBeneficiaryResponse response) {
            mView.onAddBeneficiaryComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onAddBeneficiaryFailed(error);
        }
    };

    @Override
    public void onAddBeneficiaryEasebuzz(String bankAccount, String ifscCode, String transferMode, String vpa, int transferType, int from) {
        AddBeneficiaryRequest request = new AddBeneficiaryRequest();
        request.setTransferType(transferType);
        request.setTransferMode(transferMode);
        request.setVpa(vpa);
        request.setBankAccount(bankAccount);
        request.setIfsc(ifscCode);
        if (from == 5) {
            mSubscription = mInteractor.addBeneficiaryEasebuzz(request, mAddBeneficiaryEasebuzzApiListener);
        } else {
            mSubscription = mInteractor.addBeneficiaryNoekred(request, mAddBeneficiaryEasebuzzApiListener);
        }
    }

    private IApiListener<AddBeneficiaryResponse> mAddBeneficiaryEasebuzzApiListener = new IApiListener<AddBeneficiaryResponse>() {
        @Override
        public void onSuccess(AddBeneficiaryResponse response) {
            mView.onAddBeneficiaryComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onAddBeneficiaryFailed(error);
        }
    };

    @Override
    public void onEasebuzzTransfer(String beneficiaryId, double amount, String otp, int from) {
        if (from == 5) {
            mSubscription = mInteractor.onEasebuzzTransfer(mEasebuzzApiListener, beneficiaryId, amount, otp);
        } else {
            mSubscription = mInteractor.onNeokredTransfer(mEasebuzzApiListener, beneficiaryId, amount, otp);
        }
    }

    private IApiListener<PayoutResponse> mEasebuzzApiListener = new IApiListener<PayoutResponse>() {
        @Override
        public void onSuccess(PayoutResponse response) {
            mView.onApexPayTransferComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onApexPayTransferFailed(error);
        }
    };

    @Override
    public void onRaceConditionTransfer(RaceConditionPayoutRequest raceConditionPayoutRequest) {
        mSubscription = mInteractor.onRaceConditionPayout(mRaceConditionApiListener, raceConditionPayoutRequest);

    }

    private IApiListener<PayoutResponse> mRaceConditionApiListener = new IApiListener<PayoutResponse>() {
        @Override
        public void onSuccess(PayoutResponse response) {
            mView.onRaceConditionComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onRaceConditionFailed(error);
        }
    };


    @Override
    public void onRaceConditionTransferFinal(String beneficiaryId, String amount, String otp) {
        mSubscription = mInteractor.onRaceConditionFinalPayout(mRaceConditionFinalApiListener, beneficiaryId, amount, otp);

    }

    private IApiListener<PayoutResponse> mRaceConditionFinalApiListener = new IApiListener<PayoutResponse>() {
        @Override
        public void onSuccess(PayoutResponse response) {
            mView.onRaceConditionComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onRaceConditionFailed(error);
        }
    };


    @Override
    public void onIPayTransfer(String beneficiaryId, String amount, String otp, String lat, String lon) {
        mSubscription = mInteractor.onInstantPayTransfer(mIPayApiListener, beneficiaryId, amount, otp, lat, lon);
    }

    private IApiListener<PayoutResponse> mIPayApiListener = new IApiListener<PayoutResponse>() {
        @Override
        public void onSuccess(PayoutResponse response) {
            mView.onIPayComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onIPayFailed(error);
        }
    };

    @Override
    public void checkTDS(int amount) {
        mSubscription = mInteractor.checkTDS(mCheckTDSApiListener, amount);
    }

    private IApiListener<TdsResponse> mCheckTDSApiListener = new IApiListener<TdsResponse>() {
        @Override
        public void onSuccess(TdsResponse response) {
            mView.onCheckTDSComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onCheckTDSFailed(error);
        }
    };

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}