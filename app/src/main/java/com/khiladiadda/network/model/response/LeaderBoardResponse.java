package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class LeaderBoardResponse extends BaseResponse {
    @SerializedName("response") @Expose private LeaderBoardDetails response;

    public LeaderBoardDetails getResponse() {
        return response;
    }

    public void setResponse(LeaderBoardDetails response) {
        this.response = response;
    }
}