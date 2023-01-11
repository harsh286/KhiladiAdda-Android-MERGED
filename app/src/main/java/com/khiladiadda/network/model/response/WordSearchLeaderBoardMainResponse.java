package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class WordSearchLeaderBoardMainResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private List<WordSearchLeaderBoardResponse> response = null;

    public List<WordSearchLeaderBoardResponse> getResponse() {
        return response;
    }

    public void setResponse(List<WordSearchLeaderBoardResponse> response) {
        this.response = response;
    }
}
