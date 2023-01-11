package com.khiladiadda.otp;

import android.text.TextUtils;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.request.LoginVerifyOtpRequest;
import com.khiladiadda.network.model.request.OtpRequest;
import com.khiladiadda.network.model.request.ResendOtpRequest;
import com.khiladiadda.network.model.response.LoginResponse;
import com.khiladiadda.network.model.response.MasterResponse;
import com.khiladiadda.network.model.response.OtpResponse;
import com.khiladiadda.otp.interfaces.IOtpPresenter;
import com.khiladiadda.otp.interfaces.IOtpView;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;

import rx.Subscription;

public class OtpPresenter implements IOtpPresenter {

    private IOtpView mView;
    private OtpInteractor mInteractor;
    private Subscription mSubscription;

    public OtpPresenter(IOtpView view) {
        mView = view;
        mInteractor = new OtpInteractor();
    }

    @Override public void validateData() {
        String otp = mView.getOtp();
        if (TextUtils.isEmpty(otp)) {
            mView.onValidationFailure("Please provide OTP send to your mobile number");
            return;
        }
        if (otp.length() < 6) {
            mView.onValidationFailure("Please provide valid OTP (6 digit)");
            return;
        }
        mView.onValidationComplete();
    }

    @Override public void doVerifyOtp(String mobile, String referenceCode) {
        OtpRequest request = new OtpRequest(mobile, referenceCode, AppConstant.ANDORID, AppSharedPreference.getInstance().getString(AppConstant.UUID, ""), AppSharedPreference.getInstance().getDeviceToken());
        mSubscription = mInteractor.doVerifyOtp(request, mVerifyOtpApiListener);
    }

    @Override public void resendOtp(String mobile) {
        ResendOtpRequest request = new ResendOtpRequest(mobile);
        mSubscription = mInteractor.resendOtp(request, mResendOtpApiListener);
    }

    private IApiListener<OtpResponse> mVerifyOtpApiListener = new IApiListener<OtpResponse>() {
        @Override public void onSuccess(OtpResponse response) {
            mView.onVerifyOtpComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onVerifyOtpFailure(error);
        }
    };

    private IApiListener<OtpResponse> mResendOtpApiListener = new IApiListener<OtpResponse>() {
        @Override public void onSuccess(OtpResponse response) {
            mView.onResendOtpComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onResendOtpFailure(error);
        }
    };

    @Override public void doVerifyLoginOtp(String mobile, String password, String otp) {
        LoginVerifyOtpRequest request = new LoginVerifyOtpRequest(mobile, password, otp);
        mSubscription = mInteractor.doVerifyLoginOtp(request, mVerifyLoginOtpApiListener);
    }

    private IApiListener<LoginResponse> mVerifyLoginOtpApiListener = new IApiListener<LoginResponse>() {
        @Override public void onSuccess(LoginResponse response) {
            mView.onVerifyLoginOtpComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onVerifyLoginOtpFailure(error);
        }
    };

    @Override public void getMasterData() {
        mSubscription = mInteractor.getMaster(mMasterApiListener);
    }

    private IApiListener<MasterResponse> mMasterApiListener = new IApiListener<MasterResponse>() {
        @Override public void onSuccess(MasterResponse response) {
            mView.onMasterComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onMasterFailure(error);
        }
    };

    @Override public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}