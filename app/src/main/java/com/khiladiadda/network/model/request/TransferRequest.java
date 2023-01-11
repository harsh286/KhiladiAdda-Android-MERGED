package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransferRequest {

    @SerializedName("user_id") @Expose private String userId;
    @SerializedName("mobile") @Expose private String mobile;
    @SerializedName("coins") @Expose private String coins;
    @SerializedName("wallet") @Expose private String wallet;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public TransferRequest(String userId, String mobile, String coins, String wallet) {
        this.userId = userId;
        this.mobile = mobile;
        this.coins = coins;
        this.wallet = wallet;
    }

}