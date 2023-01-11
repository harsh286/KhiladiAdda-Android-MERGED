package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionDetails {

    @SerializedName("is_deducted") @Expose private boolean isDeducted;
    @SerializedName("_id") @Expose private String id;
    @SerializedName("user_id") @Expose private String userId;
    @SerializedName("amount") @Expose private double amount;
    @SerializedName("message") @Expose private String message = null;
    @SerializedName("created_at") @Expose private String createdAt;
    @SerializedName("__v") @Expose private long v;
    @SerializedName("wallet") @Expose private Wallet wallet;
    @SerializedName("is_gst") @Expose private boolean isGst;

    public boolean isIsDeducted() {
        return isDeducted;
    }

    public void setIsDeducted(boolean isDeducted) {
        this.isDeducted = isDeducted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public long getV() {
        return v;
    }

    public void setV(long v) {
        this.v = v;
    }

    public boolean isDeducted() {
        return isDeducted;
    }

    public void setDeducted(boolean deducted) {
        isDeducted = deducted;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public boolean isGst() {
        return isGst;
    }

    public void setGst(boolean gst) {
        isGst = gst;
    }
}
