package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifiBaseResponse {

    @SerializedName("response_status")
    @Expose
    private AadhaarBaseResponse responseStatus;

    public AadhaarBaseResponse getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(AadhaarBaseResponse responseStatus) {
        this.responseStatus = responseStatus;
    }

}
