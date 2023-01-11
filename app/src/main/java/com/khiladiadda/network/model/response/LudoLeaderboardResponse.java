package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class LudoLeaderboardResponse extends BaseResponse {

    @SerializedName("response") @Expose private List<LudoLeaderboardDetails> response = null;

    public List<LudoLeaderboardDetails> getResponse() {
        return response;
    }

    public void setResponse(List<LudoLeaderboardDetails> response) {
        this.response = response;
    }

}
