package com.khiladiadda.network.model.response.hth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class LeaderBoardHthResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private List<LeaderBoardHthResponseDetails> response = null;

    public List<LeaderBoardHthResponseDetails> getResponse() {
        return response;
    }

    public void setResponse(List<LeaderBoardHthResponseDetails> response) {
        this.response = response;
    }
}
