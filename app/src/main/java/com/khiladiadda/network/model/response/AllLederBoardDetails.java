package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllLederBoardDetails {

    @SerializedName("_id") @Expose private String id;
    @SerializedName("winning_amount") @Expose private double winningAmount;
    @SerializedName("rank") @Expose private long rank;
    @SerializedName("killed") @Expose private long killed;
    @SerializedName("game_character_id") @Expose private String gameCharacterId;
    @SerializedName("game_username") @Expose private String gameUsername;
    @SerializedName("user_id") @Expose private String userId;
    @SerializedName("name") @Expose private String name;
    @SerializedName("dp") @Expose private String dp;
    @SerializedName("username") @Expose private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public long getKilled() {
        return killed;
    }

    public void setKilled(long killed) {
        this.killed = killed;
    }

    public String getGameCharacterId() {
        return gameCharacterId;
    }

    public void setGameCharacterId(String gameCharacterId) {
        this.gameCharacterId = gameCharacterId;
    }

    public String getGameUsername() {
        return gameUsername;
    }

    public void setGameUsername(String gameUsername) {
        this.gameUsername = gameUsername;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(double winningAmount) {
        this.winningAmount = winningAmount;
    }
}