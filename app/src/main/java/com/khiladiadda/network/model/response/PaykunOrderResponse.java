package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class PaykunOrderResponse extends BaseResponse {
    @SerializedName("response") @Expose private PaykunOrderDetails response;

    public PaykunOrderDetails getResponse() {
        return response;
    }

    public void setResponse(PaykunOrderDetails response) {
        this.response = response;
    }
}
