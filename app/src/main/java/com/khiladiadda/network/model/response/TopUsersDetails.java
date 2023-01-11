package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopUsersDetails {

    @SerializedName("type")
    @Expose
    private long leaderboardType;
    @SerializedName("amount")
    @Expose
    private String winningAmount;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dp")
    @Expose
    private String dp;

    public long getLeaderboardType() {
        return leaderboardType;
    }

    public void setLeaderboardType(long leaderboardType) {
        this.leaderboardType = leaderboardType;
    }

    public String getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(String winningAmount) {
        this.winningAmount = winningAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }
}