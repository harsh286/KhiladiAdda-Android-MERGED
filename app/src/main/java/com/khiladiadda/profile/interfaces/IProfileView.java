package com.khiladiadda.profile.interfaces;

import com.khiladiadda.network.model.response.ProfileResponse;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.ProfileTransactionResponse;

public interface IProfileView {

    void onUpdatePasswordComplete(BaseResponse responseModel);

    void onUpdatePasswordFailure(ApiError error);

    void onUpdateDOBComplete(ProfileResponse responseModel);

    void onUpdateDOBFailure(ApiError error);

    void onProfileComplete(ProfileTransactionResponse responseModel);

    void onProfileFailure(ApiError error);

    void onApplyVoucherComplete(BaseResponse responseModel);

    void onApplyVoucherFailure(ApiError error);

    void onSendOtpComplete(BaseResponse responseModel);

    void onSendOtpFailure(ApiError error);

    void onVerifyEmailComplete(BaseResponse responseModel);

    void onVerifyEmailFailure(ApiError error);

    void onUpdateEmailComplete(BaseResponse responseModel);

    void onUpdateEmailFailure(ApiError error);

}