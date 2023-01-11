package com.khiladiadda.network.model.response.ludoTournament;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class LudoTmtMyMatchMainResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private List<LudoTmtMyMatchResponse> response = null;

    public List<LudoTmtMyMatchResponse> getResponse() {
        return response;
    }

    public void setResponse(List<LudoTmtMyMatchResponse> response) {
        this.response = response;
    }
}
