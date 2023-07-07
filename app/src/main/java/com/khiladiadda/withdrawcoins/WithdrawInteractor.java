package com.khiladiadda.withdrawcoins;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
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

import rx.Subscription;

public class WithdrawInteractor {

    public Subscription addCashfreeBeneficiary(AddBeneficiaryRequest request, IApiListener<AddBeneficiaryResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.addCashfreeBeneficiary(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription transferAmount(TransferAmountRequest request, String id, IApiListener<PayoutResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.transferAmount(request, id)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getWithdraw(IApiListener<BeneficiaryResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getBeneficiaryList()).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription deleteBeneficiary(String id, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.deleteBeneficiary(id)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription doManualWithdraw(ManualWithdrawRequest request, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.doManualWithdraw(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getManualWithdraw(IApiListener<ManualWithdrawResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getManualWithdraw()).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription resendOtp(ResendOtpRequest request, IApiListener<OtpResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.resendOTP(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription sendOtp(SendOTPRequest request, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.sendOTP(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription addRazorpayBeneficiary(AddBeneficieryRazorpay request, IApiListener<AddBeneficiaryResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.addRazorpayBeneficiary(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription transferRazorpayAmount(RazorpayTransferAmountRequest request, IApiListener<PayoutResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.transferRazorpayAmount(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getWithdrawLimit(IApiListener<WIthdrawLimitResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getWithdrawLimit()).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription verifyBeneficiary(IApiListener<BeneficiaryVerifyResponse> listener, String beneficiaryId) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.verifyBeneficiary(beneficiaryId)).subscribe(new SubscriberCallback<>(listener));
    }
    public Subscription onApexPayTransfer(IApiListener<PayoutResponse> listener, String beneficiaryId, String amount, String otp) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.onApexPayTransfer(beneficiaryId, amount, otp)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription onPaySharpTransfer(IApiListener<PayoutResponse> listener, String beneficiaryId, String amount, String otp) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.onPaySharpTransfer(beneficiaryId, amount, otp)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription addBeneficiary(AddBeneficiaryRequest request, IApiListener<AddBeneficiaryResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.addBeneficiary(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription addBeneficiaryEasebuzz(AddBeneficiaryRequest request, IApiListener<AddBeneficiaryResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.addBeneficiaryEasebuzz(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription onEasebuzzTransfer(IApiListener<PayoutResponse> listener, String beneficiaryId, double amount, String otp) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.onEasebuzzTransfer(beneficiaryId, amount, otp)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription addBeneficiaryNoekred(AddBeneficiaryRequest request, IApiListener<AddBeneficiaryResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.addBeneficiaryNeokred(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription onNeokredTransfer(IApiListener<PayoutResponse> listener, String beneficiaryId, double amount, String otp) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.onNeokredTransfer(beneficiaryId, amount, otp)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription onRaceConditionPayout(IApiListener<PayoutResponse> listener, RaceConditionPayoutRequest raceConditionPayoutRequest) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.onRaceFreeTransfer(raceConditionPayoutRequest)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription onRaceConditionFinalPayout(IApiListener<PayoutResponse> listener, String beneficiaryId, String amount, String otp) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.onRaceFreeTransferFinal(beneficiaryId, amount, otp)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription onInstantPayTransfer(IApiListener<PayoutResponse> listener, String beneficiaryId, String amount, String otp, String latitude, String longitude) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.onInstantPayTransfer(beneficiaryId, amount, otp, latitude, longitude)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription checkTDS(IApiListener<TdsResponse> listener, int amount) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.onCheckTDS(amount)).subscribe(new SubscriberCallback<>(listener));
    }

}