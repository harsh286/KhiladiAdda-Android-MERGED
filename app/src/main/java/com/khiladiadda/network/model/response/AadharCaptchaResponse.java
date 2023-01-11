package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AadharCaptchaResponse {
    @SerializedName("response_status")
    @Expose
    private AadhaarBaseResponse responseStatus;

    @SerializedName("response_data")
    @Expose
    private CaptchaResponse responseData;

    public AadhaarBaseResponse getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(AadhaarBaseResponse responseStatus) {
        this.responseStatus = responseStatus;
    }

    public CaptchaResponse getResponseData() {
        return responseData;
    }

    public void setResponseData(CaptchaResponse responseData) {
        this.responseData = responseData;
    }

}
