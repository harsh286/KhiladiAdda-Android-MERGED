package com.khiladiadda.network.model.request.hth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.request.PlayerId;

import java.util.List;

public class CreateBattleRequest {
    @SerializedName("match_id")
    @Expose
    private String matchId;
    @SerializedName("battle_id")
    @Expose
    private String battleId;
    @SerializedName("amount")
    @Expose private double amount;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("players") @Expose private List<PlayerId> players = null;

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getBattleId() {
        return battleId;
    }

    public void setBattleId(String battleId) {
        this.battleId = battleId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<PlayerId> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerId> players) {
        this.players = players;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

}