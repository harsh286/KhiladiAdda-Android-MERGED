package com.khiladiadda.registration.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;

public interface IRegistrationView {

    String getName();

    String getMobileNumber();

    String getEmail();

    String getReferenceNo();

    void onValidationComplete();

    void onValidationFailure(String errorMsg);

    void onRegisterComplete(BaseResponse responseModel);

    void onRegisterFailure(ApiError error);

}