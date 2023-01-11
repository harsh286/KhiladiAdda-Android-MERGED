package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class LudoAddaMainResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private  List<LudoAddaResponseDetails> response = null;

    public List<LudoAddaResponseDetails> getResponse() {
        return response;
    }

    public void setResponse(List<LudoAddaResponseDetails> response) {
        this.response = response;
    }
}
