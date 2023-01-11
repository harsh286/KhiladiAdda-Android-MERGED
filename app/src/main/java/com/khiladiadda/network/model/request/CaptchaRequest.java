package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CaptchaRequest {

    @SerializedName("headers")
    @Expose
    private CaptchaHeader headers;
    @SerializedName("request")
    @Expose
    private CaptchaDetailsRequest request;

    public CaptchaHeader getHeaders() {
        return headers;
    }

    public void setHeaders(CaptchaHeader headers) {
        this.headers = headers;
    }

    public CaptchaDetailsRequest getRequest() {
        return request;
    }

    public void setRequest(CaptchaDetailsRequest request) {
        this.request = request;
    }

}
