package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class PayuChecksumResponse extends BaseResponse {

    @SerializedName("response") @Expose private String response;
    @SerializedName("response_1") @Expose private PayuDetails response1;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public PayuDetails getResponse1() {
        return response1;
    }

    public void setResponse1(PayuDetails response1) {
        this.response1 = response1;
    }

}