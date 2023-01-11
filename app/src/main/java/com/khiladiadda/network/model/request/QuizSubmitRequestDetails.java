package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizSubmitRequestDetails {

    @SerializedName("question_id") @Expose private String questionId;
    @SerializedName("option_id") @Expose private String optionId;

    public QuizSubmitRequestDetails(String questionId, String optionId) {
        this.questionId = questionId;
        this.optionId = optionId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

}
