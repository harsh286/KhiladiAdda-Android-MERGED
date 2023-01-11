package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class MasterResponse extends BaseResponse {

    @SerializedName("response") @Expose private MasterDetails response;
    public MasterDetails getResponse() {
        return response;
    }

    public void setResponse(MasterDetails response) {
        this.response = response;
    }

}