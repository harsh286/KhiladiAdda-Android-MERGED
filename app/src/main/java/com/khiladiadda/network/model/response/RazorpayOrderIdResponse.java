package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class RazorpayOrderIdResponse extends BaseResponse {

    @SerializedName("response") @Expose private RazorpayOrderIdDetails response;

    public RazorpayOrderIdDetails getResponse() {
        return response;
    }

    public void setResponse(RazorpayOrderIdDetails response) {
        this.response = response;
    }
}