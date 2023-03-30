package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class CbHistoryRankMainResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private CbHistoryRankResponse response;

    public CbHistoryRankResponse getResponse() {
        return response;
    }

    public void setResponse(CbHistoryRankResponse response) {
        this.response = response;
    }
}
