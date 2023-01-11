package com.khiladiadda.socialverify;

import android.text.TextUtils;

import com.khiladiadda.login.LoginActivity;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.FBRegisterRequest;
import com.khiladiadda.network.model.request.GmailRegisterRequest;
import com.khiladiadda.network.model.request.OtpRequest;
import com.khiladiadda.network.model.request.ResendOtpRequest;
import com.khiladiadda.network.model.response.OtpResponse;
import com.khiladiadda.network.model.response.SocialResponse;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.socialverify.interfaces.ISocialVerifyPresenter;
import com.khiladiadda.socialverify.interfaces.ISocialVerifyView;
import com.khiladiadda.network.model.request.FirebaseRequest;
import com.khiladiadda.network.model.response.MasterResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;

import rx.Subscription;

public class SocialVerifyPresenter implements ISocialVerifyPresenter {

    private ISocialVerifyView mView;
    private SocialVerifyInteractor mInteractor;
    private Subscription mSubscription;

    public SocialVerifyPresenter(ISocialVerifyView view) {
        mView = view;
        mInteractor = new SocialVerifyInteractor();
    }

    @Override
    public void validateData() {
        String mobileNumber = mView.getMobileNumber();
        if (TextUtils.isEmpty(mobileNumber)) {
            mView.onValidationFailure("Mobile number cannot be empty");
            return;
        }
        if (mobileNumber.length() < 10) {
            mView.onValidationFailure("Mobile number must be 10 digit");
            return;
        }
        mView.onValidationComplete();
    }

    @Override
    public void doGmailRegister(GmailRegisterRequest request) {
        mSubscription = mInteractor.doGmailRegister(request, mGmailApiListener);
    }

    @Override
    public void doFbRegister(FBRegisterRequest request) {
          mSubscription = mInteractor.doFbRegister(request, mFbApiListener);
    }
    @Override
    public void verifyOtp(String mobile, String referenceCode) {
        OtpRequest request = new OtpRequest(mobile, referenceCode, AppConstant.ANDORID, AppSharedPreference.getInstance().getString(AppConstant.UUID, ""), AppSharedPreference.getInstance().getDeviceToken());
        mSubscription = mInteractor.verifyOtp(request, mVerifyOtpApiListener);
    }

    @Override
    public void resendOtp(String mobile) {
        ResendOtpRequest request = new ResendOtpRequest(mobile);
        mSubscription = mInteractor.resendOtp(request, mResendOtpApiListener);
    }

    @Override
    public void getMasterData() {
        mSubscription = mInteractor.getMaster(mMasterApiListener);
    }

    private IApiListener<MasterResponse> mMasterApiListener = new IApiListener<MasterResponse>() {
        @Override
        public void onSuccess(MasterResponse response) { mView.onMasterComplete(response); }

        @Override
        public void onError(ApiError error) {
            mView.onMasterFailure(error);
        }
    };

    private IApiListener<SocialResponse> mGmailApiListener = new IApiListener<SocialResponse>() {
        @Override
        public void onSuccess(SocialResponse response) {
            mView.onGmailLoginComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onGmailLoginFailure(error);
        }
    };

    private IApiListener<SocialResponse> mFbApiListener = new IApiListener<SocialResponse>() {
        @Override
        public void onSuccess(SocialResponse response) {
            mView.onFbLoginComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onFbLoginFailure(error);
        }
    };

    private IApiListener<OtpResponse> mVerifyOtpApiListener = new IApiListener<OtpResponse>() {
        @Override
        public void onSuccess(OtpResponse response) {
            mView.onVerifyOtpComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onVerifyOtpFailure(error);
        }
    };

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
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}