package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class WithdrawResponse extends BaseResponse {

    @SerializedName("response") @Expose private List<WIthdrawDetails> response = null;

    public List<WIthdrawDetails> getResponse() {
        return response;
    }

    public void setResponse(List<WIthdrawDetails> response) {
        this.response = response;
    }

}