package com.khiladiadda.network.model.response.ludoTournament;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LudoTmtUserFirstInfoResponse {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("randomName")
    @Expose
    private String randomName;
    @SerializedName("randomDp")
    @Expose
    private String randomDp;
    @SerializedName("dp")
    @Expose
    private String dp;
    @SerializedName("joinedAt")
    @Expose
    private String joinedAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRandomName() {
        return randomName;
    }

    public void setRandomName(String randomName) {
        this.randomName = randomName;
    }

    public String getRandomDp() {
        return randomDp;
    }

    public void setRandomDp(String randomDp) {
        this.randomDp = randomDp;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(String joinedAt) {
        this.joinedAt = joinedAt;
    }
}
