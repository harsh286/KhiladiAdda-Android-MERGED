package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class WordSearchLiveLeaderBoardMainResponse extends BaseResponse {

    @SerializedName("response")
    @Expose
    private WordSearchLiveLeaderBoardResponse response;

    public WordSearchLiveLeaderBoardResponse getResponse() {
        return response;
    }

    public void setResponse(WordSearchLiveLeaderBoardResponse response) {
        this.response = response;
    }
}
