package com.khiladiadda.network.model.response.ludoTournament;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class LudoTmtAllTournamentMainResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private List<LudoTmtAllTournamentResponse> response = null;

    public List<LudoTmtAllTournamentResponse> getResponse() {
        return response;
    }

    public void setResponse(List<LudoTmtAllTournamentResponse> response) {
        this.response = response;
    }
}
