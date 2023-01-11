package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CashfreeSavePayment {
    @SerializedName("referenceId") @Expose private long referenceId;
    @SerializedName("orderId") @Expose private String orderId;
    @SerializedName("orderAmount") @Expose private long orderAmount;
    @SerializedName("txStatus") @Expose private String txStatus;
    @SerializedName("paymentMode") @Expose private String paymentMode;
    @SerializedName("txMsg") @Expose private String txMsg;
    @SerializedName("txTime") @Expose private String txTime;
    @SerializedName("signature") @Expose private String signature;
    @SerializedName("coupon") @Expose private String coupon;

    public long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(long referenceId) {
        this.referenceId = referenceId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getTxStatus() {
        return txStatus;
    }

    public void setTxStatus(String txStatus) {
        this.txStatus = txStatus;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getTxMsg() {
        return txMsg;
    }

    public void setTxMsg(String txMsg) {
        this.txMsg = txMsg;
    }

    public String getTxTime() {
        return txTime;
    }

    public void setTxTime(String txTime) {
        this.txTime = txTime;
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
