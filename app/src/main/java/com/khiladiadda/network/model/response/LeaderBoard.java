package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class LeaderBoard {
    @SerializedName("user_id") @Expose private String userId;
    @SerializedName("name") @Expose private String name;
    @SerializedName("dp") @Expose private String dp;
    @SerializedName("winning_amount") @Expose private double winAmount;
    @SerializedName("time_taken") @Expose private long timeTaken;
    @SerializedName("rank") @Expose private long rank;
    @SerializedName("right_answers") @Expose private long point;

    public LeaderBoard(String userId) {
        this.userId = userId;
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

    public double getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(double winAmount) {
        this.winAmount = winAmount;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public long getPoint() {
        return point;
    }

    public void setPoint(long point) {
        this.point = point;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaderBoard that = (LeaderBoard) o;
        return userId.equals(that.userId);
    }

    @Override public int hashCode() {
        return Objects.hash(userId);
    }
}
