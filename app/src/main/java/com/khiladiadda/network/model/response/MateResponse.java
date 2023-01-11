package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class MateResponse extends BaseResponse {

    @SerializedName("response") @Expose private MateDetails response;

    public MateDetails getResponse() {
        return response;
    }

    public void setResponse(MateDetails response) {
        this.response = response;
    }

}