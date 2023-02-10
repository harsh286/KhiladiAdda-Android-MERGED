package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhonepeRequest {

    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("targetApp")
    @Expose
    private Integer targetApp;

    @SerializedName("coupon") @Expose private String coupon;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getTargetApp() {
        return targetApp;
    }

    public void setTargetApp(Integer targetApp) {
        this.targetApp = targetApp;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }
}
