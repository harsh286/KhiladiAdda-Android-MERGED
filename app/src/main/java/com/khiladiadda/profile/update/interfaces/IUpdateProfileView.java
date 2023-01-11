package com.khiladiadda.profile.update.interfaces;

import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.SurepassResponse;
import com.khiladiadda.network.model.response.ProfileResponse;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.UploadImageResponse;
import com.khiladiadda.network.model.response.AadharCaptchaResponse;
import com.khiladiadda.network.model.response.AadharDetailsResponse;
import com.khiladiadda.network.model.response.VerifiBaseResponse;
import com.khiladiadda.network.model.response.WIthdrawLimitResponse;

public interface IUpdateProfileView {

    String getName();

    String getUsername();

    String getEmailAddress();

    String getMobile();

    String getGender();

    String getState();

    String getCountry();

    void onValidationComplete();

    void onValidationFailure(String errorMsg);

    void onUploadImageComplete(UploadImageResponse responseModel);

    void onUploadImageFailure(ApiError error);

    void onUpdateProfileComplete(ProfileResponse responseModel);

    void onUpdateProfileFailure(ApiError error);

    void onUpdatePANComplete(ProfileResponse responseModel);

    void onUpdatePANFailure(ApiError error);

    void onGetCaptchaComplete(AadharCaptchaResponse responseModel);

    void onGetCaptchaFailure(ApiError error);

    void onGetAadhaarOtpComplete(VerifiBaseResponse responseModel);

    void onGetAadhaarOtpFailure(ApiError error);

    void onVerifyAadhaarOtpComplete(VerifiBaseResponse responseModel);

    void onVerifyAadhaarOtpFailure(ApiError error);

    void onGetAadhaarKYCComplete(AadharDetailsResponse responseModel);

    void onGetAadhaarKYCFailure(ApiError error);

    void onUpdateAadharComplete(ProfileResponse responseModel);

    void onUpdateAadhaarFailure(ApiError error);

    void onCheckAdharComplete(BaseResponse responseModel);

    void onCheckAadharFailure(ApiError error);

    void onAadharVerifyViaComplete(WIthdrawLimitResponse response);

    void onAadharVerifyViaFailed(ApiError error);

    void onVerifyAadhaarOtpSurepassComplete(ProfileResponse responseModel);

    void onVerifyAadhaarOtpSurepassFailure(ApiError error);

    void onVerifyBeneficiaryComplete(BaseResponse responseModel);

    void onVerifyBeneficiaryFailure(ApiError error);
    
}