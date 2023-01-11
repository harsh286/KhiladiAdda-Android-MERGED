package com.khiladiadda.network.model.response.wordsearch_new;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class WordSearchCategoryMainResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private List<WordSearchCategoryResponse> response = null;

    public List<WordSearchCategoryResponse> getResponse() {
        return response;
    }

    public void setResponse(List<WordSearchCategoryResponse> response) {
        this.response = response;
    }
}
