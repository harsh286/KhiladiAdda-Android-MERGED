package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RazorpayRequest {

    @SerializedName("razorpay_order_id") @Expose private String orderId;
    @SerializedName("razorpay_payment_id") @Expose private String paymentId;
    @SerializedName("razorpay_signature") @Expose private String signature;
    @SerializedName("coupon") @Expose private String coupon;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }
}