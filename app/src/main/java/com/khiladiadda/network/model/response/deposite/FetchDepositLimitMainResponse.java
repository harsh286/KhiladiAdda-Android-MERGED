package com.khiladiadda.network.model.response.deposite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class FetchDepositLimitMainResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private FetchDepositLimitResponse response;

    public FetchDepositLimitResponse getResponse() {
        return response;
    }

    public void setResponse(FetchDepositLimitResponse response) {
        this.response = response;
    }
}
