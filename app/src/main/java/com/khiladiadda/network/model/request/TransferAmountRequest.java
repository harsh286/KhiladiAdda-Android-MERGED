package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransferAmountRequest {

    @SerializedName("amount") @Expose private String amount;

    @SerializedName("otp") @Expose private String otp;

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
}