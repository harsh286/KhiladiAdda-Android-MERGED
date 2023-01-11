package com.khiladiadda.forgotpassword.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.OtpResponse;

public interface IForgetPasswordView {

    String getMobileNumber();

    void onValidationComplete();

    void onValidationFailure(String errorMsg);

    void onSendOtpComplete(BaseResponse responseModel);

    void onSendOtpFailure(ApiError error);

    void onVerifyOtpComplete(BaseResponse responseModel);

    void onVerifyOtpFailure(ApiError error);

    void onResetPasswordComplete(BaseResponse responseModel);

    void onResetPasswordFailure(ApiError error);

    void onSendOtpVerifyComplete(OtpResponse responseModel);

    void onSendOtpVerifyFailure(ApiError error);

    void onVerifyMobileComplete(OtpResponse responseModel);

    void onVerifyMobileFailure(ApiError error);

}
