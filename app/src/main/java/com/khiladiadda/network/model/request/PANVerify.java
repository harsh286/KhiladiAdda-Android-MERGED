package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PANVerify {

    @SerializedName("headers")
    @Expose
    private PanVerifyHeader headers;
    @SerializedName("request")
    @Expose
    private PanVerifyRequest request;

    public PanVerifyHeader getHeaders() {
        return headers;
    }

    public void setHeaders(PanVerifyHeader headers) {
        this.headers = headers;
    }

    public PanVerifyRequest getRequest() {
        return request;
    }

    public void setRequest(PanVerifyRequest request) {
        this.request = request;
    }

}