package com.khiladiadda.network.model.request.hth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.request.PlayerId;

import java.util.List;

public class UpdateOpponentPlayers {
    @SerializedName("battle_id")
    @Expose
    private String battleId;
    @SerializedName("players")
    @Expose
    private List<PlayerId> players = null;

    public String getBattleId() {
        return battleId;
    }

    public void setBattleId(String battleId) {
        this.battleId = battleId;
    }

    public List<PlayerId> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerId> players) {
        this.players = players;
    }

}