package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SquadLeaderboard {

    @SerializedName("_id") @Expose private String id;
    @SerializedName("user") @Expose private List<LeaderboardTeamMate> user = null;
    @SerializedName("team_name") @Expose private String teamName;
    @SerializedName("team_slot") @Expose private long teamSlot;
    @SerializedName("rank") @Expose private long rank;
    @SerializedName("team_winning_amount") @Expose private long winningAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<LeaderboardTeamMate> getUser() {
        return user;
    }

    public void setUser(List<LeaderboardTeamMate> user) {
        this.user = user;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public long getTeamSlot() {
        return teamSlot;
    }

    public void setTeamSlot(long teamSlot) {
        this.teamSlot = teamSlot;
    }

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public long getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(long winningAmount) {
        this.winningAmount = winningAmount;
    }
}
