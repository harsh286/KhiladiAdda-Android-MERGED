package com.khiladiadda.network.model.request;

import com.google.gson.annotations.SerializedName;

public class BajajPayEncryptedRequest {
    @SerializedName("encRequest")
    public String encRequest;

    public BajajPayEncryptedRequest(String encRequest) {
        this.encRequest = encRequest;
    }

    public String getEncRequest() {
        return encRequest;
    }

    public void setEncRequest(String encRequest) {
        this.encRequest = encRequest;
    }


}
