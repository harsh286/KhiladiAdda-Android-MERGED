package com.khiladiadda.profile;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.ChangePasswordRequest;
import com.khiladiadda.network.model.request.UpdateDOB;
import com.khiladiadda.network.model.request.VoucherRequest;
import com.khiladiadda.network.model.response.ProfileResponse;
import com.khiladiadda.network.model.response.ProfileTransactionResponse;
import com.khiladiadda.profile.interfaces.IProfilePresenter;
import com.khiladiadda.profile.interfaces.IProfileView;

import rx.Subscription;

public class ProfilePresenter implements IProfilePresenter {

    private IProfileView mView;
    private ProfileInteractor mInteractor;
    private Subscription mSubscription;

    public ProfilePresenter(IProfileView mView) {
        this.mView = mView;
        mInteractor = new ProfileInteractor();
    }

    @Override public void doUpdatePassword(String mobile, String password, String oldPassword) {
        ChangePasswordRequest request = new ChangePasswordRequest(mobile, password, oldPassword);
        mSubscription = mInteractor.updatePassword(request, mPasswordApiListener);
    }

    private IApiListener<BaseResponse> mPasswordApiListener = new IApiListener<BaseResponse>() {
        @Override public void onSuccess(BaseResponse response) {
            mView.onUpdatePasswordComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onUpdatePasswordFailure(error);
        }
    };

    @Override public void doUpdateDOB(String dob) {
        UpdateDOB request = new UpdateDOB(dob);
        mSubscription = mInteractor.updateDOB(request, mDobApiListener);
    }

    private IApiListener<ProfileResponse> mDobApiListener = new IApiListener<ProfileResponse>() {
        @Override public void onSuccess(ProfileResponse response) {
            mView.onUpdateDOBComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onUpdateDOBFailure(error);
        }
    };

    @Override public void getProfile() {
        mSubscription = mInteractor.getProfile(mProfileApiListener);
    }

    private IApiListener<ProfileTransactionResponse> mProfileApiListener = new IApiListener<ProfileTransactionResponse>() {
        @Override public void onSuccess(ProfileTransactionResponse response) {
            mView.onProfileComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onProfileFailure(error);
        }
    };

    @Override
    public void applyVocher(String code, String pin) {
        VoucherRequest request = new VoucherRequest();
        request.setCode(code);
        request.setPin(pin);
        mSubscription = mInteractor.applyVocher(mVoucherApiListener, request);
    }

    private IApiListener<BaseResponse> mVoucherApiListener = new IApiListener<BaseResponse>() {
        @Override public void onSuccess(BaseResponse response) {
            mView.onApplyVoucherComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onApplyVoucherFailure(error);
        }
    };

    @Override
    public void doSendOTP(String email) {
        mSubscription = mInteractor.sendOtp(mSendOtpApiListener, email);
    }

    private IApiListener<BaseResponse> mSendOtpApiListener = new IApiListener<BaseResponse>() {
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
    public void doVerifyEmail(String email, String otp) {
        mSubscription = mInteractor.verifyEmail(mVerifyEmailApiListener, email, otp);
    }

    private IApiListener<BaseResponse> mVerifyEmailApiListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onVerifyEmailComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onVerifyEmailFailure(error);
        }
    };

    @Override
    public void updateEmail(String email) {
        mSubscription = mInteractor.updateEmail(mUpdateEmailApiListener, email);
    }

    private IApiListener<BaseResponse> mUpdateEmailApiListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onUpdateEmailComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onUpdateEmailFailure(error);
        }
    };

    
    @Override public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}