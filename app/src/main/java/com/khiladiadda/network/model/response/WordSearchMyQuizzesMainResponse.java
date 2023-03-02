package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class WordSearchMyQuizzesMainResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private List<WordSearchMyQuizzesResponse> response = null;

    public List<WordSearchMyQuizzesResponse> getResponse() {
        return response;
    }

    public void setResponse(List<WordSearchMyQuizzesResponse> response) {
        this.response = response;
    }
}
