package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class HelpResponse extends BaseResponse {

    @SerializedName("response") @Expose private List<HelpDetails> response = null;

    public List<HelpDetails> getResponse() {
        return response;
    }

    public void setResponse(List<HelpDetails> response) {
        this.response = response;
    }

}