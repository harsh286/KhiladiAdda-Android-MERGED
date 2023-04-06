package com.khiladiadda.network.model.response.deposite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class DepositLimitMainResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private DepositLimitResponse response;

    public DepositLimitResponse getResponse() {
        return response;
    }

    public void setResponse(DepositLimitResponse response) {
        this.response = response;
    }

}