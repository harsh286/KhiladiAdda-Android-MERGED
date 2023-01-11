package com.khiladiadda.network.model.response.gamer_cash;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SwitchGamerCashRequest {
    @SerializedName("amount")
    @Expose
    private int amount;
    @SerializedName("coupon")
    @Expose
    private String coupon;

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
