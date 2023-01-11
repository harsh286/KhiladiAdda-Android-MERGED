package com.khiladiadda.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.response.Wallets;

public class BaseResponse {

    @SerializedName("status") @Expose private boolean status;
    @SerializedName("message") @Expose private String message;
    @SerializedName("systemInfo") @Expose private int systemInfo;
    @SerializedName("type") @Expose private int type;
    @SerializedName("payment_via")@Expose private String payment_via;
    @SerializedName("wallet_obj") @Expose private Wallets walletObj;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSystemInfo() {
        return systemInfo;
    }

    public void setSystemInfo(int systemInfo) {
        this.systemInfo = systemInfo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPayment_via() {
        return payment_via;
    }

    public void setPayment_via(String payment_via) {
        this.payment_via = payment_via;
    }

    public Wallets getWalletObj() {
        return walletObj;
    }

    public void setWalletObj(Wallets walletObj) {
        this.walletObj = walletObj;
    }
}