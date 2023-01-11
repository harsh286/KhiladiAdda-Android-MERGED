package com.khiladiadda.network.model.response.ludoTournament;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class LudoTmtJoinMainResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private LudoTmtJoinResponse response = null;

    public LudoTmtJoinResponse getResponse() {
        return response;
    }

    public void setResponse(LudoTmtJoinResponse response) {
        this.response = response;
    }
}