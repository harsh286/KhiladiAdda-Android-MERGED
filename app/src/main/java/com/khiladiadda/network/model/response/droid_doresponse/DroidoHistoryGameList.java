package com.khiladiadda.network.model.response.droid_doresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class DroidoHistoryGameList extends BaseResponse {

    @SerializedName("response")
    @Expose
    private List<GameHistoryDroido> gameHistoryDroidoList = null;

    public List<GameHistoryDroido> getGameHistoryDroidoList() {
        return gameHistoryDroidoList;
    }

    public void setGameHistoryDroidoList(List<GameHistoryDroido> gameHistoryDroidoList) {
        this.gameHistoryDroidoList = gameHistoryDroidoList;
    }
}