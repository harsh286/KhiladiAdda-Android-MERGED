package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScratchCardResponseDettails {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("amount")
    @Expose
    private double amount;
    @SerializedName("commissionAmount")
    @Expose
    private double commissionAmount;
    @SerializedName("wallet")
    @Expose
    private String wallet;
    @SerializedName("is_deleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("is_used")
    @Expose
    private Integer isUsed;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("scratch_type")
    @Expose
    private Integer scratchType;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(double commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getScratchType() {
        return scratchType;
    }

    public void setScratchType(Integer scratchType) {
        this.scratchType = scratchType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}