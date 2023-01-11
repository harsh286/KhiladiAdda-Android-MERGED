package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentStatusRequest {

    @SerializedName("orderId") @Expose private String orderId;
    @SerializedName("coupon") @Expose private String coupon;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }
}
