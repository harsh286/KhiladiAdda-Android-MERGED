package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class QuizSubmitResponse extends BaseResponse {

    @SerializedName("response") @Expose private QuizSubmitResponseDetails response;

    public QuizSubmitResponseDetails getResponse() {
        return response;
    }

    public void setResponse(QuizSubmitResponseDetails response) {
        this.response = response;
    }

}