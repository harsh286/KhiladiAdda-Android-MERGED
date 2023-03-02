package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class LeaderboardMainResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private List<LeaderboardSubResponse> response;

    public List<LeaderboardSubResponse> getResponse() {
        return response;
    }

    public void setResponse(List<LeaderboardSubResponse> response) {
        this.response = response;
    }
}
