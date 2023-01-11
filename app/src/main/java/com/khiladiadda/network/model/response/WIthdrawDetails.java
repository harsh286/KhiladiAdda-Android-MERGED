package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WIthdrawDetails {
    @SerializedName("transferId")
    @Expose
    private String requestNo;
    @SerializedName("transferMode")
    @Expose
    private String paymentMode;
    @SerializedName("amount")
    @Expose
    private long withdrawAmount;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("transferAddress")
    @Expose
    private String transferAddress;

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public long getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(long withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTransferAddress() {
        return transferAddress;
    }

    public void setTransferAddress(String transferAddress) {
        this.transferAddress = transferAddress;
    }

}