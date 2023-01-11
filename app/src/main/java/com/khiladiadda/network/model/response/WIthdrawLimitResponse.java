package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class WIthdrawLimitResponse extends BaseResponse {

    @SerializedName("response")
    @Expose
    private WithdrawLimitDetails response;

    public WithdrawLimitDetails getResponse() {
        return response;
    }

    public void setResponse(WithdrawLimitDetails response) {
        this.response = response;
    }

}