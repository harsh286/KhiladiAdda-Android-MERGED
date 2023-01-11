package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyOtpRequest {

    @SerializedName("headers")
    @Expose
    private CaptchaHeader headers;
    @SerializedName("request")
    @Expose
    private VerifyOtpDetailsRequest request;

    public CaptchaHeader getHeaders() {
        return headers;
    }

    public void setHeaders(CaptchaHeader headers) {
        this.headers = headers;
    }

    public VerifyOtpDetailsRequest getRequest() {
        return request;
    }

    public void setRequest(VerifyOtpDetailsRequest request) {
        this.request = request;
    }

}
