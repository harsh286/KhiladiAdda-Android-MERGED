package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayuChecksumRequest {

    @SerializedName("amount") @Expose private String amount;

    public String getOrderAmount() {
        return amount;
    }

    public void setOrderAmount(String orderAmount) {
        this.amount = orderAmount;
    }

    @SerializedName("coupon") @Expose private String coupon;

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

}