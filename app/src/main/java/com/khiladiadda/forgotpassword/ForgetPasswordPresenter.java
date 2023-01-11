package com.khiladiadda.forgotpassword;

import android.text.TextUtils;

import com.khiladiadda.R;
import com.khiladiadda.forgotpassword.interfaces.IForgetPasswordPresenter;
import com.khiladiadda.forgotpassword.interfaces.IForgetPasswordView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.ForgetPasswordRequest;
import com.khiladiadda.network.model.request.ForgotPasswordOtpRequest;
import com.khiladiadda.network.model.request.ResetPasswordRequest;
import com.khiladiadda.network.model.request.SendOTPRequest;
import com.khiladiadda.network.model.request.VerifyMobileRequest;
import com.khiladiadda.network.model.response.OtpResponse;
import com.khiladiadda.utility.AppUtilityMethods;

import rx.Subscription;

public class ForgetPasswordPresenter implements IForgetPasswordPresenter {

    private IForgetPasswordView mView;
    private ForgetPasswordInteractor mInteractor;
    private Subscription mSubscription;

    public ForgetPasswordPresenter(IForgetPasswordView view) {
        mView = view;
        mInteractor = new ForgetPasswordInteractor();
    }

    @Override public void validateData() {
        String mobileNumber = mView.getMobileNumber();
        if (TextUtils.isEmpty(mobileNumber)) {
            mView.onValidationFailure("Mobile number cannot be empty");
            return;
        }
        if (mobileNumber.length() != 10) {
            mView.onValidationFailure("Mobile number cannot be less than 10 digit");
            return;
        }
        if (!AppUtilityMethods.isMobileValidator(mobileNumber)) {
            mView.onValidationFailure("Please provide valid mobile number(10 digit)");
            return;
        }
        mView.onValidationComplete();
    }

    @Override public void sendOtp(String mobile) {
        ForgetPasswordRequest request = new ForgetPasswordRequest(mobile);
        mSubscription = mInteractor.sendOtp(request, mSendOtpAPIListener);
    }

    @Override public void verifyOtp(String mobile, String referenceCode) {
        ForgotPasswordOtpRequest request = new ForgotPasswordOtpRequest(mobile, referenceCode);
        mSubscription = mInteractor.verifyOtp(request, mVerifyOtpAPIListener);
    }

    @Override public void resetPassword(String mobile, String otp, String password) {
        ResetPasswordRequest request = new ResetPasswordRequest(mobile, otp, password);
        mSubscription = mInteractor.resetPassword(request, mChnagePasswordAPIListener);
    }

    @Override public void sendOtpVerify(String mobile) {
        SendOTPRequest request = new SendOTPRequest(mobile);
        mSubscription = mInteractor.sendOtpVerify(request, mSendOtpVerifyAPIListener);
    }

    @Override public void verifyMobile(String mobile, String otp) {
        VerifyMobileRequest request = new VerifyMobileRequest(mobile, otp);
        mSubscription = mInteractor.verifyMobile(request, mVerifyMobileAPIListener);
    }

    private IApiListener<BaseResponse> mSendOtpAPIListener = new IApiListener<BaseResponse>() {
        @Override public void onSuccess(BaseResponse response) {
            mView.onSendOtpComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onSendOtpFailure(error);
        }
    };

    private IApiListener<BaseResponse> mVerifyOtpAPIListener = new IApiListener<BaseResponse>() {
        @Override public void onSuccess(BaseResponse response) {
            mView.onVerifyOtpComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onVerifyOtpFailure(error);
        }
    };

    private IApiListener<BaseResponse> mChnagePasswordAPIListener = new IApiListener<BaseResponse>() {
        @Override public void onSuccess(BaseResponse response) {
            mView.onResetPasswordComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onResetPasswordFailure(error);
        }
    };

    private IApiListener<OtpResponse> mSendOtpVerifyAPIListener = new IApiListener<OtpResponse>() {
        @Override public void onSuccess(OtpResponse response) {
            mView.onSendOtpVerifyComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onSendOtpVerifyFailure(error);
        }
    };

    private IApiListener<OtpResponse> mVerifyMobileAPIListener = new IApiListener<OtpResponse>() {
        @Override public void onSuccess(OtpResponse response) {
            mView.onVerifyMobileComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onVerifyMobileFailure(error);
        }
    };

    @Override public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}