package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CashfreeChecksumRequest {

    @SerializedName("orderAmount") @Expose private long orderAmount;

    public long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(long orderAmount) {
        this.orderAmount = orderAmount;
    }

    @SerializedName("coupon") @Expose private String coupon;

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

}