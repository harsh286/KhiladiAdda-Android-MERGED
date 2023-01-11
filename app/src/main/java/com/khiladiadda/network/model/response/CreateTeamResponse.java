package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.CreateTeam;

public class CreateTeamResponse extends BaseResponse {

    @SerializedName("response") @Expose private CreateTeam response;

    public CreateTeam getResponse() {
        return response;
    }

    public void setResponse(CreateTeam response) {
        this.response = response;
    }

}