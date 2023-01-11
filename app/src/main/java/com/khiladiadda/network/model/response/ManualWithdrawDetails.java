package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ManualWithdrawDetails {
    @SerializedName("request_no") @Expose private long requestNo;
    @SerializedName("status") @Expose private long status;
    @SerializedName("reject_msg") @Expose private String rejectMsg;
    @SerializedName("action_at") @Expose private Object actionAt;
    @SerializedName("_id") @Expose private String id;
    @SerializedName("user_id") @Expose private String userId;
    @SerializedName("withdraw_amount") @Expose private long withdrawAmount;
    @SerializedName("withdraw_coins") @Expose private long withdrawCoins;
    @SerializedName("payment_mode") @Expose private String paymentMode;
    @SerializedName("payment_address") @Expose private String paymentAddress;
    @SerializedName("created_at") @Expose private String createdAt;
    @SerializedName("__v") @Expose private long v;

    public long getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(long requestNo) {
        this.requestNo = requestNo;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getRejectMsg() {
        return rejectMsg;
    }

    public void setRejectMsg(String rejectMsg) {
        this.rejectMsg = rejectMsg;
    }

    public Object getActionAt() {
        return actionAt;
    }

    public void setActionAt(Object actionAt) {
        this.actionAt = actionAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(long withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public long getV() {
        return v;
    }

    public void setV(long v) {
        this.v = v;
    }

}