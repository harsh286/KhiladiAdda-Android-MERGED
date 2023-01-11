package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class SquadLeaderbordResponse extends BaseResponse {

    @SerializedName("response") @Expose private List<SquadLeaderboard> response = null;

    public List<SquadLeaderboard> getResponse() {
        return response;
    }

    public void setResponse(List<SquadLeaderboard> response) {
        this.response = response;
    }

}
