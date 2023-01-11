package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CaptchaDetailsRequest {

    @SerializedName("api_key")
    @Expose
    private String apiKey;
    @SerializedName("request_id")
    @Expose
    private String requestId;
    @SerializedName("hash")
    @Expose
    private String hash;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public CaptchaDetailsRequest(String apiKey, String requestId, String hash) {
        this.apiKey = apiKey;
        this.requestId = requestId;
        this.hash = hash;
    }
}
