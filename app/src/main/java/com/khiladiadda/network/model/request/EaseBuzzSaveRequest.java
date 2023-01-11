package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EaseBuzzSaveRequest {

    @SerializedName("amount") @Expose
    private double amount;
    @SerializedName("orderId") @Expose
    private String orderId;
    @SerializedName("coupon") @Expose
    private String coupon;
    @SerializedName("status") @Expose
    private String status;

    public EaseBuzzSaveRequest(double amount, String orderId, String coupon, String status) {
        this.amount = amount;
        this.orderId = orderId;
        this.coupon = coupon;
        this.status = status;
    }

    public EaseBuzzSaveRequest(double amount, String coupon) {
        this.amount = amount;
        this.coupon = coupon;
    }
}
