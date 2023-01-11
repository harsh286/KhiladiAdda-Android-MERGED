package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class ReferResponse extends BaseResponse {
    @SerializedName("response") @Expose private List<ReferDetail> response = null;

    public List<ReferDetail> getResponse() {
        return response;
    }

    public void setResponse(List<ReferDetail> response) {
        this.response = response;
    }

}