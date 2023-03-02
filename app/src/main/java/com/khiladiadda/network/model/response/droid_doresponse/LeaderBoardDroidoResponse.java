package com.khiladiadda.network.model.response.droid_doresponse;

import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class LeaderBoardDroidoResponse extends BaseResponse {
    @SerializedName("response")
    private ResponseLeaderBoard responseLeaderBoard;

    public ResponseLeaderBoard getResponseLeaderBoard() {
        return responseLeaderBoard;
    }

    public void setResponseLeaderBoard(ResponseLeaderBoard responseLeaderBoard) {
        this.responseLeaderBoard = responseLeaderBoard;
    }

}
