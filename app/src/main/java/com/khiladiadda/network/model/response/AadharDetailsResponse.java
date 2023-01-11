package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AadharDetailsResponse {

    @SerializedName("response_status")
    @Expose
    private AadhaarBaseResponse responseStatus;

    @SerializedName("response_data")
    @Expose
    private AadhaarKYCDetailsResponse responseData;

    public AadhaarBaseResponse getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(AadhaarBaseResponse responseStatus) {
        this.responseStatus = responseStatus;
    }

    public AadhaarKYCDetailsResponse getResponseData() {
        return responseData;
    }

    public void setResponseData(AadhaarKYCDetailsResponse responseData) {
        this.responseData = responseData;
    }

}
