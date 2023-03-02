package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class WordSearchTrendingMainResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private WordSearchTrendingResponse response;

    public WordSearchTrendingResponse getResponse() {
        return response;
    }

    public void setResponse(WordSearchTrendingResponse response) {
        this.response = response;
    }


}
