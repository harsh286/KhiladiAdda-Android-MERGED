package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.FBParticipantData;

import java.util.List;

public class FBBattleParticipantResponse extends BaseResponse {

    @SerializedName("response") @Expose private List<FBParticipantData> response = null;

    public List<FBParticipantData> getResponse() {
        return response;
    }

    public void setResponse(List<FBParticipantData> response) {
        this.response = response;
    }

}