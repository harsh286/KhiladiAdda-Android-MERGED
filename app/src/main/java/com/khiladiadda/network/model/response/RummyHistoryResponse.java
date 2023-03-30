package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RummyHistoryResponse {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("wallet")
    @Expose
    private Wallet wallet;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("cardId")
    @Expose
    private String cardId;
    @SerializedName("cardUniqId")
    @Expose
    private String cardUniqId;
    @SerializedName("arenaID")
    @Expose
    private String arenaID;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("winningAmount")
    @Expose
    private Double winningAmount;
    @SerializedName("refID")
    @Expose
    private String refID;
    @SerializedName("txnID")
    @Expose
    private String txnID;
    @SerializedName("expTime")
    @Expose
    private Integer expTime;
    @SerializedName("gameMode")
    @Expose
    private Integer gameMode;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("rummyCode")
    @Expose
    private String rummyCode;

    public String getRummyCode() {
        return rummyCode;
    }

    public void setRummyCode(String rummyCode) {
        this.rummyCode = rummyCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardUniqId() {
        return cardUniqId;
    }

    public void setCardUniqId(String cardUniqId) {
        this.cardUniqId = cardUniqId;
    }

    public String getArenaID() {
        return arenaID;
    }

    public void setArenaID(String arenaID) {
        this.arenaID = arenaID;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(Double winningAmount) {
        this.winningAmount = winningAmount;
    }

    public String getRefID() {
        return refID;
    }

    public void setRefID(String refID) {
        this.refID = refID;
    }

    public String getTxnID() {
        return txnID;
    }

    public void setTxnID(String txnID) {
        this.txnID = txnID;
    }

    public Integer getExpTime() {
        return expTime;
    }

    public void setExpTime(Integer expTime) {
        this.expTime = expTime;
    }

    public Integer getGameMode() {
        return gameMode;
    }

    public void setGameMode(Integer gameMode) {
        this.gameMode = gameMode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
