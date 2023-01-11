package com.khiladiadda.login.interfaces;

import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.LoginResponse;
import com.khiladiadda.network.model.response.SocialResponse;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.MasterResponse;

public interface ILoginView {

    String getEmailAddress();

    String getPassword();

    void onValidationComplete();

    void onValidationFailure(String errorMsg);

    void onLoginComplete(BaseResponse responseModel);

    void onLoginFailure(ApiError error);

    void onGmailLoginComplete(SocialResponse responseModel);

    void onGmailLoginFailure(ApiError error);

    void onFbLoginComplete(SocialResponse responseModel);

    void onFbLoginFailure(ApiError error);

    void onMasterComplete(MasterResponse responseModel);

    void onMasterFailure(ApiError error);

}