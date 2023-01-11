package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.network.model.BaseResponse;

public class PaySharpResponse extends BaseResponse {

    @SerializedName("response")
    @Expose
    private PaySharpData response;

    public PaySharpData getResponse() {
        return response;
    }

    public void setResponse(PaySharpData response) {
        this.response = response;
    }

}