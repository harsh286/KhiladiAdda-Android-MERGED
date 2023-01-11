package com.khiladiadda.battle.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayedUsersList {
    @SerializedName("_id") @Expose private String id;
    @SerializedName("is_won") @Expose private boolean isWon;
    @SerializedName("is_result_declared") @Expose private boolean isResultDeclared;
    @SerializedName("is_cancelled") @Expose private boolean isCancelled;
    @SerializedName("winning_amount") @Expose private double winningAmount;
    @SerializedName("amount") @Expose private double amount;
    @SerializedName("battle_group_id") @Expose private String battleGroupId;
    @SerializedName("created_at") @Expose private String createdAt;
    @SerializedName("user_id") @Expose private String userId;
    @SerializedName("match_id") @Expose private String matchId;
    @SerializedName("battle_id") @Expose private String battleId;
    @SerializedName("__v") @Expose private long v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //    public Wallets getWallets() {
    //        return wallets;
    //    }
    //
    //    public void setWallets(Wallets wallets) {
    //        this.wallets = wallets;
    //    }

    public boolean isIsWon() {
        return isWon;
    }

    public void setIsWon(boolean isWon) {
        this.isWon = isWon;
    }

    public boolean isIsResultDeclared() {
        return isResultDeclared;
    }

    public void setIsResultDeclared(boolean isResultDeclared) {
        this.isResultDeclared = isResultDeclared;
    }

    public boolean isIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public double getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(double winningAmount) {
        this.winningAmount = winningAmount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBattleGroupId() {
        return battleGroupId;
    }

    public void setBattleGroupId(String battleGroupId) {
        this.battleGroupId = battleGroupId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getBattleId() {
        return battleId;
    }

    public void setBattleId(String battleId) {
        this.battleId = battleId;
    }

    public long getV() {
        return v;
    }

    public void setV(long v) {
        this.v = v;
    }
}
