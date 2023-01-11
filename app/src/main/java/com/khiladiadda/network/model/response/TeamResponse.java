package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class TeamResponse extends BaseResponse {

    @SerializedName("response") @Expose private List<TeamName> response = null;

    public List<TeamName> getResponse() {
        return response;
    }

    public void setResponse(List<TeamName> response) {
        this.response = response;
    }
}