package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.ArrayList;

public class StartQuizResponse extends BaseResponse {

    @SerializedName("response") @Expose private StartQuizDetailsResponse response;
    @SerializedName("questions") @Expose private ArrayList<QuestionDetails> questionDetailsArrayList;


    public StartQuizDetailsResponse getResponse() {
        return response;
    }

    public void setResponse(StartQuizDetailsResponse response) {
        this.response = response;
    }

    public ArrayList<QuestionDetails> getQuestionDetailsArrayList() {
        return questionDetailsArrayList;
    }

    public void setQuestionDetailsArrayList(ArrayList<QuestionDetails> questionDetailsArrayList) {
        this.questionDetailsArrayList = questionDetailsArrayList;
    }

    public StartQuizResponse(boolean status, String message, StartQuizDetailsResponse response, ArrayList<QuestionDetails> questionDetailsArrayList) {
        this.response = response;
        this.questionDetailsArrayList = questionDetailsArrayList;
    }

}