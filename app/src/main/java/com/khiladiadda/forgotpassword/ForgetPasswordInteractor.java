package com.khiladiadda.forgotpassword;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.ForgetPasswordRequest;
import com.khiladiadda.network.model.request.ForgotPasswordOtpRequest;
import com.khiladiadda.network.model.request.ResetPasswordRequest;
import com.khiladiadda.network.model.request.SendOTPRequest;
import com.khiladiadda.network.model.request.VerifyMobileRequest;
import com.khiladiadda.network.model.response.OtpResponse;

import rx.Subscription;

public class ForgetPasswordInteractor {

    public Subscription sendOtp(ForgetPasswordRequest request, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.forgetPassword(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription verifyOtp(ForgotPasswordOtpRequest request, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.verifyForgetPasswordOtp(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription resetPassword(ResetPasswordRequest request, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.resetPassword(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription sendOtpVerify(SendOTPRequest request, IApiListener<OtpResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.sendOTP(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription verifyMobile(VerifyMobileRequest request, IApiListener<OtpResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.verifyMobile(request)).subscribe(new SubscriberCallback<>(listener));
    }

}