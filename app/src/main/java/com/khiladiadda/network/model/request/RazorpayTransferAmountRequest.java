package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RazorpayTransferAmountRequest {

    @SerializedName("fund_account_id") @Expose private String fundAccountId;
    @SerializedName("mode") @Expose private String mode;
    @SerializedName("amount") @Expose private String amount;
    @SerializedName("otp") @Expose private String otp;
    @SerializedName("beneId") @Expose private String beneId;

    public String getFundAccountId() {
        return fundAccountId;
    }

    public void setFundAccountId(String fundAccountId) {
        this.fundAccountId = fundAccountId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getBeneId() {
        return beneId;
    }

    public void setBeneId(String beneId) {
        this.beneId = beneId;
    }
}