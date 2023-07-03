package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOtpRequest {
    @SerializedName("headers")
    @Expose
    private CaptchaHeader headers;
    @SerializedName("request")
    @Expose
    private GetOtpDetailsRequest request;

    public CaptchaHeader getHeaders() {
        return headers;
    }

    public void setHeaders(CaptchaHeader headers) {
        this.headers = headers;
    }

    public GetOtpDetailsRequest getRequest() {
        return request;
    }

    public void setRequest(GetOtpDetailsRequest request) {
        this.request = request;
    }

}
