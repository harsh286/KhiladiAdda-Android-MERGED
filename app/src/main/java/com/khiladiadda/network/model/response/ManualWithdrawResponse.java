package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class ManualWithdrawResponse extends BaseResponse {

    @SerializedName("response") @Expose private List<ManualWithdrawDetails> response = null;

    public List<ManualWithdrawDetails> getResponse() {
        return response;
    }

    public void setResponse(List<ManualWithdrawDetails> response) {
        this.response = response;
    }

}