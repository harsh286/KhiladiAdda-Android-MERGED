package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FBParticipantList {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("is_won")
    @Expose
    private boolean isWon;
    @SerializedName("winning_amount")
    @Expose
    private double winningAmount;
    @SerializedName("amount")
    @Expose
    private long amount;

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    @SerializedName("battle_group_id")
    @Expose
    private String battleGroupId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    // @SerializedName("user") @Expose private FBParticipantData user;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dp")
    private String dp;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsWon() {
        return isWon;
    }

    public void setIsWon(boolean isWon) {
        this.isWon = isWon;
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

    public String getBattleGroupId() {
        return battleGroupId;
    }

    public void setBattleGroupId(String battleGroupId) {
        this.battleGroupId = battleGroupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

//    public FBParticipantData getUser() {
//        return user;
//    }
//
//    public void setUser(FBParticipantData user) {
//        this.user = user;
//    }
}
