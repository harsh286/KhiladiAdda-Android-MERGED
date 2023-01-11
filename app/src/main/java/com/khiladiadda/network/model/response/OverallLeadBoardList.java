package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OverallLeadBoardList  {
    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("winning_amount")
    @Expose
    private double winningAmount;
    @SerializedName("amount")
    @Expose
    private long amount;

    @SerializedName("user_id")
    @Expose
    private String userId;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dp")
    private String dp;


    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(double winningAmount) {
        this.winningAmount = winningAmount;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
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
}
