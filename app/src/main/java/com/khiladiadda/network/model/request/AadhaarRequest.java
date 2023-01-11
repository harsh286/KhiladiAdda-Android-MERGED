package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AadhaarRequest {

    @SerializedName("headers")
    @Expose
    private CaptchaHeader headers;
    @SerializedName("request")
    @Expose
    private AadhaarDetailsRequest request;

    public CaptchaHeader getHeaders() {
        return headers;
    }

    public void setHeaders(CaptchaHeader headers) {
        this.headers = headers;
    }

    public AadhaarDetailsRequest getRequest() {
        return request;
    }

    public void setRequest(AadhaarDetailsRequest request) {
        this.request = request;
    }

}
