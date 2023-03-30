package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CbHistoryRankResponse {
    @SerializedName("players")
    @Expose
    private List<CallBreakHistoryPlayerResponse> players;

    public List<CallBreakHistoryPlayerResponse> getPlayers() {
        return players;
    }

    public void setPlayers(List<CallBreakHistoryPlayerResponse> players) {
        this.players = players;
    }
}
