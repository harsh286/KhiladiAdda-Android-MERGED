package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ManualWithdrawRequest {
    @SerializedName("withdraw_coins") @Expose private long withdrawCoins;
    @SerializedName("payment_mode") @Expose private String paymentMode;
    @SerializedName("payment_address") @Expose private String paymentAddress;

    public long getWithdrawCoins() {
        return withdrawCoins;
    }

    public void setWithdrawCoins(long withdrawCoins) {
        this.withdrawCoins = withdrawCoins;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentAddress() {
        return paymentAddress;
    }

    public void setPaymentAddress(String paymentAddress) {
        this.paymentAddress = paymentAddress;
    }
}
