package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class ParticipantResponse extends BaseResponse {

    @SerializedName("response") @Expose private List<ParticipantDetails> response = null;

    public List<ParticipantDetails> getResponse() {
        return response;
    }

    public void setResponse(List<ParticipantDetails> response) {
        this.response = response;
    }

}