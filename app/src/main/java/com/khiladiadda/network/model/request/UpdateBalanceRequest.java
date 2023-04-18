package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class UpdateBalanceRequest {
    @SerializedName("amount")
    @Expose
    int amount;
    @SerializedName("app_version")
    @Expose
    String app_version;
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

}