package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParticipantDetails {

    @SerializedName("game_username") @Expose private String gameUsername;
    @SerializedName("username") @Expose private String username;
    @SerializedName("name") @Expose private String name;
    @SerializedName("dp") @Expose private String dp;

    public String getGameUsername() {
        return gameUsername;
    }

    public void setGameUsername(String gameUsername) {
        this.gameUsername = gameUsername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
