package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.ArrayList;

public class QuizQuestionResponse extends BaseResponse {

    @SerializedName("response") @Expose private ArrayList<QuestionDetails> response = new ArrayList<>();

    public ArrayList<QuestionDetails> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<QuestionDetails> response) {
        this.response = response;
    }
}
