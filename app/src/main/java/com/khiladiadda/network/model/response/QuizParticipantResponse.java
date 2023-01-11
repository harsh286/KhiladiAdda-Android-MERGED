package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class QuizParticipantResponse extends BaseResponse {

    @SerializedName("response") @Expose private List<QuizParticipant> response = null;

    public List<QuizParticipant> getResponse() {
        return response;
    }

    public void setResponse(List<QuizParticipant> response) {
        this.response = response;
    }

}
