package com.khiladiadda.login;

import android.text.TextUtils;

import com.khiladiadda.login.interfaces.ILoginPresenter;
import com.khiladiadda.login.interfaces.ILoginView;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.FBVerifyRequest;
import com.khiladiadda.network.model.request.GmailVerifyRequest;
import com.khiladiadda.network.model.request.LoginRequest;
import com.khiladiadda.network.model.response.LoginResponse;
import com.khiladiadda.network.model.response.SocialResponse;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.network.model.response.MasterResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;

import rx.Subscription;

public class LoginPresenter implements ILoginPresenter {

    private ILoginView mView;
    private LoginInteractor mInteractor;
    private Subscription mSubscription;

    public LoginPresenter(ILoginView mView) {
        this.mView = mView;
        mInteractor = new LoginInteractor();
    }

    @Override
    public void validateData() {
        String emailAddress = mView.getEmailAddress();
//        String password = mView.getPassword();

        if (TextUtils.isEmpty(emailAddress)) {
            mView.onValidationFailure("Mobile number cannot be empty");
            return;
        }
        if (emailAddress.length() < 10) {
            mView.onValidationFailure("Mobile number must be 10 digit");
            return;
        }
        if (!AppUtilityMethods.isMobileValidator(emailAddress)) {
            mView.onValidationFailure("Please provide valid mobile number");
            return;
        }
//        if (TextUtils.isEmpty(password)) {
//            mView.onValidationFailure("Password cannot be empty");
//            return;
//        }
//        if (password.length() < 6 || password.length() > 17) {
//            mView.onValidationFailure("Password cannot be less than 6 digit and greater than 15 digit");
//            return;
//        }
        mView.onValidationComplete();
    }

    @Override
    public void doLogin(String emailAddress) {
        LoginRequest request = new LoginRequest(mView.getEmailAddress(), AppConstant.ANDORID, AppSharedPreference.getInstance().getString(AppConstant.UUID, ""), AppSharedPreference.getInstance().getDeviceToken());
        mSubscription = mInteractor.doLogin(request, mLoginApiListener);
    }

    @Override
    public void doGmailLogin(String gmailId) {
        GmailVerifyRequest request = new GmailVerifyRequest(gmailId, AppConstant.ANDORID, AppSharedPreference.getInstance().getString(AppConstant.UUID, ""), AppSharedPreference.getInstance().getDeviceToken());
        mSubscription = mInteractor.doGmailLogin(request, mGmailApiListener);
    }

    @Override
    public void doFbLogin(String token) {
        FBVerifyRequest request = new FBVerifyRequest(token, AppConstant.ANDORID, AppSharedPreference.getInstance().getString(AppConstant.UUID, ""), AppSharedPreference.getInstance().getDeviceToken());
        mSubscription = mInteractor.doFbLogin(request, mFbApiListener);
    }

    @Override
    public void getMasterData() {
        mSubscription = mInteractor.getMaster(mMasterApiListener);
    }

    private IApiListener<MasterResponse> mMasterApiListener = new IApiListener<MasterResponse>() {
        @Override
        public void onSuccess(MasterResponse response) {
            mView.onMasterComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onMasterFailure(error);
        }
    };

    private IApiListener<BaseResponse> mLoginApiListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onLoginComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onLoginFailure(error);
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

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}