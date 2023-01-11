package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class NotifyResponse extends BaseResponse {

    @SerializedName("response") @Expose private List<String> response = null;

    public List<String> getResponse() {
        return response;
    }

    public void setResponse(List<String> response) {
        this.response = response;
    }

}