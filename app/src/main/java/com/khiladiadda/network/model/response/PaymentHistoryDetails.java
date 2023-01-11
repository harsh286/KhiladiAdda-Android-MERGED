package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentHistoryDetails {
    @SerializedName("_id") @Expose private String id;
    @SerializedName("user_id") @Expose private String userId;
    @SerializedName("created_at") @Expose private String createdAt;
    @SerializedName("order_id") @Expose private String orderId;
    @SerializedName("type") @Expose private String type;
    @SerializedName("payment_via") @Expose private String paymentVia;
    @SerializedName("amount") @Expose private String amount;
    @SerializedName("status") @Expose private String status;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPaymentVia() {
        return paymentVia;
    }

    public void setPaymentVia(String paymentVia) {
        this.paymentVia = paymentVia;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
