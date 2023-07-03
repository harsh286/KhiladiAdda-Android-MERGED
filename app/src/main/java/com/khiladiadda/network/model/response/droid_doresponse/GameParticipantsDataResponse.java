package com.khiladiadda.network.model.response.droid_doresponse;

import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class GameParticipantsDataResponse extends BaseResponse {

    @SerializedName("response")
    private List<ResponseItem> response = null;

    public List<ResponseItem> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseItem> response) {
        this.response = response;
    }
}

