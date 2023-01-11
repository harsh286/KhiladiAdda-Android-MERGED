package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class FBParticipantResponse extends BaseResponse {

    @SerializedName("response")
    @Expose
    private List<FBParticipantList> response = null;

    public List<FBParticipantList> getResponse() {
        return response;
    }

    public void setResponse(List<FBParticipantList> response) {
        this.response = response;
    }
}
