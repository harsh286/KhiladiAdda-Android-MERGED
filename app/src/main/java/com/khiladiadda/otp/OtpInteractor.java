package com.khiladiadda.otp;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.request.LoginVerifyOtpRequest;
import com.khiladiadda.network.model.request.OtpRequest;
import com.khiladiadda.network.model.request.ResendOtpRequest;
import com.khiladiadda.network.model.response.LoginResponse;
import com.khiladiadda.network.model.response.MasterResponse;
import com.khiladiadda.network.model.response.OtpResponse;

import rx.Subscription;

public class OtpInteractor {

    public Subscription doVerifyOtp(OtpRequest request, IApiListener<OtpResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.verifyOtp(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription resendOtp(ResendOtpRequest request, IApiListener<OtpResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.resendOTP(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getMaster(IApiListener<MasterResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getMaster()).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription doVerifyLoginOtp(LoginVerifyOtpRequest request, IApiListener<LoginResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.verifyOTPonLogin(request)).subscribe(new SubscriberCallback<>(listener));
    }

}