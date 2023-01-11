package com.khiladiadda.socialverify;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.request.FBRegisterRequest;
import com.khiladiadda.network.model.request.GmailRegisterRequest;
import com.khiladiadda.network.model.request.OtpRequest;
import com.khiladiadda.network.model.request.ResendOtpRequest;
import com.khiladiadda.network.model.response.MasterResponse;
import com.khiladiadda.network.model.response.OtpResponse;
import com.khiladiadda.network.model.response.SocialResponse;

import rx.Subscription;

public class SocialVerifyInteractor {

    public Subscription doGmailRegister(GmailRegisterRequest request, IApiListener<SocialResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.gmailRegister(request)).subscribe(new SubscriberCallback<SocialResponse>(listener));
    }

    public Subscription doFbRegister(FBRegisterRequest request, IApiListener<SocialResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.fbRegister(request)).subscribe(new SubscriberCallback<SocialResponse>(listener));
    }

    public Subscription verifyOtp(OtpRequest request, IApiListener<OtpResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.verifyOtp(request)).subscribe(new SubscriberCallback<OtpResponse>(listener));
    }

    public Subscription resendOtp(ResendOtpRequest request, IApiListener<OtpResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.resendOTP(request)).subscribe(new SubscriberCallback<OtpResponse>(listener));
    }

    public Subscription getMaster(IApiListener<MasterResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getMaster()).subscribe(new SubscriberCallback<MasterResponse>(listener));
    }

}