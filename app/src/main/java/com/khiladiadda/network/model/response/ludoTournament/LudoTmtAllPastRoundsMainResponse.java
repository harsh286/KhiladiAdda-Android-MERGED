package com.khiladiadda.network.model.response.ludoTournament;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class LudoTmtAllPastRoundsMainResponse extends BaseResponse {

    @SerializedName("response")
    @Expose
    private List<LudoTmtAllPastRoundsResponse> response;

    public List<LudoTmtAllPastRoundsResponse> getResponse() {
        return response;
    }

    public void setResponse(List<LudoTmtAllPastRoundsResponse> response) {
        this.response = response;
    }
}
