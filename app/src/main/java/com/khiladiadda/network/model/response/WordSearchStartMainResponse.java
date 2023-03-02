package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class WordSearchStartMainResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private WordSearchStartResponse response;

    public WordSearchStartResponse getResponse() {
        return response;
    }

    public void setResponse(WordSearchStartResponse response) {
        this.response = response;
    }
}
