package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallBreakJoinResponse {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("randomName")
    @Expose
    private String randomName;
    @SerializedName("dp")
    @Expose
    private String dp;
    @SerializedName("randomDp")
    @Expose
    private String randomDp;
    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("points")
    @Expose
    private Integer points;
    @SerializedName("winningAmount")
    @Expose
    private Integer winningAmount;
    @SerializedName("joinedAt")
    @Expose
    private String joinedAt;

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

    public String getRandomName() {
        return randomName;
    }

    public void setRandomName(String randomName) {
        this.randomName = randomName;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getRandomDp() {
        return randomDp;
    }

    public void setRandomDp(String randomDp) {
        this.randomDp = randomDp;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(Integer winningAmount) {
        this.winningAmount = winningAmount;
    }

    public String getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(String joinedAt) {
        this.joinedAt = joinedAt;
    }
}
