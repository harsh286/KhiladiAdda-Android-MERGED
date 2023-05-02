package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class UpdateBalanceRequest {
    @SerializedName("amount")
    @Expose
    private int amount;
    @SerializedName("app_version")
    @Expose
    private String app_version;
    @SerializedName("txnId")
    @Expose
    private String txnId;

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }
}