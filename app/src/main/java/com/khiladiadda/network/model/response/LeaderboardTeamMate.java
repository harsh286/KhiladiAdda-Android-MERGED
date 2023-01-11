package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaderboardTeamMate {

    @SerializedName("user_id") @Expose private String userId;
    @SerializedName("name") @Expose private String name;
    @SerializedName("dp") @Expose private String dp;
    @SerializedName("won") @Expose private long won;
    @SerializedName("killed") @Expose private long killed;
    @SerializedName("game_character_id") @Expose private String gameCharacterId;
    @SerializedName("game_username") @Expose private String gameUsername;

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

    public long getWon() {
        return won;
    }

    public void setWon(long won) {
        this.won = won;
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
}
