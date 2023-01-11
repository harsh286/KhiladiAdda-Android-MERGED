package com.khiladiadda.profile.update;

import android.content.ContentResolver;
import android.net.Uri;
import android.text.TextUtils;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.PanInfo;
import com.khiladiadda.network.model.request.PanUpdateRequest;
import com.khiladiadda.network.model.request.ProfileRequest;
import com.khiladiadda.network.model.request.SurepassGenerateOtpRequest;
import com.khiladiadda.network.model.request.SurepassResponse;
import com.khiladiadda.network.model.request.VerifyOtpSurepass;
import com.khiladiadda.network.model.response.ProfileResponse;
import com.khiladiadda.network.model.response.UploadImageResponse;
import com.khiladiadda.network.model.response.WIthdrawLimitResponse;
import com.khiladiadda.profile.update.interfaces.IUpdateProfilePresenter;
import com.khiladiadda.profile.update.interfaces.IUpdateProfileView;
import com.khiladiadda.network.model.request.AadhaarDetailsRequest;
import com.khiladiadda.network.model.request.AadhaarRequest;
import com.khiladiadda.network.model.response.AadharCaptchaResponse;
import com.khiladiadda.network.model.response.AadharDetailsResponse;
import com.khiladiadda.network.model.request.AadharUpdateRequest;
import com.khiladiadda.network.model.request.CaptchaDetailsRequest;
import com.khiladiadda.network.model.request.CaptchaHeader;
import com.khiladiadda.network.model.request.CaptchaRequest;
import com.khiladiadda.network.model.request.CheckAadharRequest;
import com.khiladiadda.network.model.request.GetOtpDetailsRequest;
import com.khiladiadda.network.model.request.GetOtpRequest;
import com.khiladiadda.network.model.request.UpdateAadhaarRequest;
import com.khiladiadda.network.model.response.VerifiBaseResponse;
import com.khiladiadda.network.model.request.VerifyOtpDetailsRequest;
import com.khiladiadda.network.model.request.VerifyOtpRequest;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscription;

public class UpdateProfilePresenter implements IUpdateProfilePresenter {

    private IUpdateProfileView mView;
    private UpdateProfileInteractor mInteractor;
    private Subscription mSubscription;

    public UpdateProfilePresenter(IUpdateProfileView mView) {
        this.mView = mView;
        mInteractor = new UpdateProfileInteractor();
    }

    @Override
    public void validateData() {
        String name = mView.getName();
        String userName = mView.getUsername();
        String emailAddress = mView.getEmailAddress();

        if (TextUtils.isEmpty(name)) {
            mView.onValidationFailure("Name cannot be empty");
            return;
        }
        if (TextUtils.isEmpty(userName)) {
            mView.onValidationFailure("Username cannot be empty");
            return;
        }
//        if (TextUtils.isEmpty(emailAddress)) {
//            mView.onValidationFailure("Email address cannot be empty");
//            return;
//        }
//        if (!AppUtilityMethods.isEmailValidator(emailAddress)) {
//            mView.onValidationFailure("Please provide proper email address");
//            return;
//        }
        mView.onValidationComplete();
    }

    @Override
    public void doUpdateProfile(String image) {
        ProfileRequest request = new ProfileRequest(image, mView.getName(), mView.getUsername(), mView.getEmailAddress(), "", mView.getCountry(), mView.getState(), mView.getGender());
        mSubscription = mInteractor.updateProfile(request, mLoginApiListener);
    }

    private IApiListener<ProfileResponse> mLoginApiListener = new IApiListener<ProfileResponse>() {
        @Override
        public void onSuccess(ProfileResponse response) {
            mView.onUpdateProfileComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onUpdateProfileFailure(error);
        }
    };

    @Override
    public void uploadImage(Uri organizerImageUri, File organizerImageFile, ContentResolver contentResolver, int type) {
        RequestBody bannerImageRequestBody = RequestBody.create(MediaType.parse(contentResolver.getType(organizerImageUri)), organizerImageFile);
        MultipartBody.Part bannerImagePart = MultipartBody.Part.createFormData("file", organizerImageFile.getName(), bannerImageRequestBody);
        mSubscription = mInteractor.uploadImage(mUploadImageApiListener, bannerImagePart, type);
    }

    private IApiListener<UploadImageResponse> mUploadImageApiListener = new IApiListener<UploadImageResponse>() {
        @Override
        public void onSuccess(UploadImageResponse response) {
            mView.onUploadImageComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onUploadImageFailure(error);
        }
    };

    @Override
    public void doUpdatePAN(String panURL, String panName, String panNumber) {
        PanInfo panInfo = new PanInfo(panName, panURL, panNumber);
        PanUpdateRequest request = new PanUpdateRequest(panInfo);
        mSubscription = mInteractor.updatePAN(request, mPanApiListener);
    }

    private IApiListener<ProfileResponse> mPanApiListener = new IApiListener<ProfileResponse>() {
        @Override
        public void onSuccess(ProfileResponse response) {
            mView.onUpdatePANComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onUpdatePANFailure(error);
        }
    };

    @Override
    public void checkAadhar(String aadharNumber) {
        CheckAadharRequest adhar = new CheckAadharRequest(aadharNumber);
        mSubscription = mInteractor.checkAadhar(adhar, mAadharApiListener);
    }

    private IApiListener<BaseResponse> mAadharApiListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onCheckAdharComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onCheckAadharFailure(error);
        }
    };

    @Override
    public void getCaptcha(String requestId, String hashSequence) {
        CaptchaRequest request = new CaptchaRequest();
        CaptchaHeader captchaHeader = new CaptchaHeader(AppConstant.AADHAR_CLIENT_ID, AppConstant.AADHAR_CLIENT_ID, "", "", requestId, "", "", AppConstant.AADHAR_SELF, "", "", "", "", "", AppConstant.AADHAR_DEFAULT, AppConstant.AADHAR_DEFAULT);
        request.setHeaders(captchaHeader);
        CaptchaDetailsRequest captchaDetailsRequest = new CaptchaDetailsRequest(AppConstant.AADHAR_API_KEY, requestId, hashSequence);
        request.setRequest(captchaDetailsRequest);
        mSubscription = mInteractor.getCaptcha(request, mCaptchaApiListener);
    }

    private IApiListener<AadharCaptchaResponse> mCaptchaApiListener = new IApiListener<AadharCaptchaResponse>() {
        @Override
        public void onSuccess(AadharCaptchaResponse response) {
            mView.onGetCaptchaComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onGetCaptchaFailure(error);
        }

    };

    @Override
    public void getAadhaarOtp(String requestId, String uuid, String aadhaar, String captcha) {
        GetOtpRequest request = new GetOtpRequest();
        long time = System.currentTimeMillis();
        String timeStamp = String.valueOf(time);
        CaptchaHeader captchaHeader = new CaptchaHeader(AppConstant.AADHAR_CLIENT_ID, AppConstant.AADHAR_CLIENT_ID, "", "", requestId, "", timeStamp, AppConstant.AADHAR_SELF, AppConstant.AADHAR_TRIAL, AppConstant.AADHAR_DEFAULT, AppConstant.AADHAR_DEFAULT, AppConstant.AADHAR_DEFAULT, "", AppConstant.AADHAR_DEFAULT, AppConstant.AADHAR_DEFAULT);
        request.setHeaders(captchaHeader);
        GetOtpDetailsRequest captchaDetailsRequest = new GetOtpDetailsRequest(uuid, aadhaar, captcha, "OTP", "YES");
        request.setRequest(captchaDetailsRequest);
        mSubscription = mInteractor.getAadhaarOTP(request, mGetOTPApiListener);
    }

    private IApiListener<VerifiBaseResponse> mGetOTPApiListener = new IApiListener<VerifiBaseResponse>() {
        @Override
        public void onSuccess(VerifiBaseResponse response) {
            mView.onGetAadhaarOtpComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onGetAadhaarOtpFailure(error);
        }

    };

    @Override
    public void verifyAadhaarOtp(String requestId, String uuid, String otp, String shareCode, int from) {
        if (from == 1) {
            VerifyOtpRequest request = new VerifyOtpRequest();
            CaptchaHeader captchaHeader = new CaptchaHeader(AppConstant.AADHAR_CLIENT_ID, AppConstant.AADHAR_CLIENT_ID, "", "", requestId, "", "", AppConstant.AADHAR_SELF, "", "", "", "", "", AppConstant.AADHAR_DEFAULT, AppConstant.AADHAR_DEFAULT);
            request.setHeaders(captchaHeader);
            VerifyOtpDetailsRequest captchaDetailsRequest = new VerifyOtpDetailsRequest(uuid, otp, shareCode);
            request.setRequest(captchaDetailsRequest);
            mSubscription = mInteractor.verifyAadhaarOtp(request, mVerifyOTPApiListener);
        } else {
            VerifyOtpSurepass verifyOtpSurepass = new VerifyOtpSurepass();
            verifyOtpSurepass.setOtp(otp);
            mSubscription = mInteractor.verifyAadhaarOtpSurepass(verifyOtpSurepass, mVerifyOTPSurepassApiListener);
        }
    }

    private IApiListener<VerifiBaseResponse> mVerifyOTPApiListener = new IApiListener<VerifiBaseResponse>() {
        @Override
        public void onSuccess(VerifiBaseResponse response) {
            mView.onVerifyAadhaarOtpComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onVerifyAadhaarOtpFailure(error);
        }

    };

    private IApiListener<ProfileResponse> mVerifyOTPSurepassApiListener = new IApiListener<ProfileResponse>() {
        @Override
        public void onSuccess(ProfileResponse response) {
            mView.onVerifyAadhaarOtpSurepassComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onVerifyAadhaarOtpSurepassFailure(error);
        }

    };

    @Override
    public void getAadhaarKYC(String requestId, String uuid, String hashSequence) {
        AadhaarRequest request = new AadhaarRequest();
        CaptchaHeader captchaHeader = new CaptchaHeader(AppConstant.AADHAR_CLIENT_ID, AppConstant.AADHAR_CLIENT_ID, "", "", requestId, "", "", AppConstant.AADHAR_SELF, "", "", "", "", "", AppConstant.AADHAR_DEFAULT, AppConstant.AADHAR_DEFAULT);
        request.setHeaders(captchaHeader);
        AadhaarDetailsRequest captchaDetailsRequest = new AadhaarDetailsRequest(uuid, AppConstant.AADHAR_API_KEY, hashSequence);
        request.setRequest(captchaDetailsRequest);
        mSubscription = mInteractor.getAadhaarKYCDetails(request, mGetAadhaarKYCApiListener);
    }

    private IApiListener<AadharDetailsResponse> mGetAadhaarKYCApiListener = new IApiListener<AadharDetailsResponse>() {
        @Override
        public void onSuccess(AadharDetailsResponse response) {
            mView.onGetAadhaarKYCComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onGetAadhaarKYCFailure(error);
        }
    };

    @Override
    public void updateAadhaarServer(String name, String url, String urlAadhar, String address, String email, String gender, String dob, String aadhaarNo, String motherName, String fatherName, int from) {
        UpdateAadhaarRequest updateAadhaarRequest = new UpdateAadhaarRequest(name, url, urlAadhar, email, dob, gender, address, fatherName, motherName, aadhaarNo);
        if (from == 2) {
            AadharUpdateRequest request = new AadharUpdateRequest(updateAadhaarRequest);
            mSubscription = mInteractor.updateAadhaarOnServer(request, mUpdateAadhaarApiListener);
        } else {
            AadharUpdateRequest request = new AadharUpdateRequest(updateAadhaarRequest);
            mSubscription = mInteractor.updateAadhar(request, mPanApiListener);
        }
    }

    private IApiListener<ProfileResponse> mUpdateAadhaarApiListener = new IApiListener<ProfileResponse>() {
        @Override
        public void onSuccess(ProfileResponse response) {
            mView.onUpdateAadharComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onUpdateAadhaarFailure(error);
        }
    };

    @Override
    public void getWithdrawLimit() {
        mSubscription = mInteractor.getWithdrawLimit(mWithdrawLimitApiListener);
    }

    private IApiListener<WIthdrawLimitResponse> mWithdrawLimitApiListener = new IApiListener<WIthdrawLimitResponse>() {
        @Override
        public void onSuccess(WIthdrawLimitResponse response) {
            mView.onAadharVerifyViaComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onAadharVerifyViaFailed(error);
        }
    };

    @Override
    public void verifyBeneficiary(String beneficiaryId) {
        mSubscription = mInteractor.verifyBeneficiary(mBeneficiaryApiListener, beneficiaryId);
    }

    private IApiListener<BaseResponse> mBeneficiaryApiListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onVerifyBeneficiaryComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onVerifyBeneficiaryFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}