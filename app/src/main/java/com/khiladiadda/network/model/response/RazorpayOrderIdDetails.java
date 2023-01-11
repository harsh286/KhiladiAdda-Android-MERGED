package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class RazorpayOrderIdDetails extends BaseResponse {

    @SerializedName("id") @Expose private String id;
    @SerializedName("entity") @Expose private String entity;
    @SerializedName("amount") @Expose private long amount;
    @SerializedName("amount_paid") @Expose private long amountPaid;
    @SerializedName("amount_due") @Expose private long amountDue;
    @SerializedName("currency") @Expose private String currency;
    @SerializedName("receipt") @Expose private String receipt;
    @SerializedName("offer_id") @Expose private Object offerId;
//    @SerializedName("status") @Expose private String status;
    @SerializedName("attempts") @Expose private long attempts;
    @SerializedName("created_at") @Expose private long createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(long amountPaid) {
        this.amountPaid = amountPaid;
    }

    public long getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(long amountDue) {
        this.amountDue = amountDue;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public Object getOfferId() {
        return offerId;
    }

    public void setOfferId(Object offerId) {
        this.offerId = offerId;
    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }

    public long getAttempts() {
        return attempts;
    }

    public void setAttempts(long attempts) {
        this.attempts = attempts;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

}