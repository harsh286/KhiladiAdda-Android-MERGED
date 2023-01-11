package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendOTPRequest {

    @SerializedName("mobile") @Expose private String mobileNumber;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public SendOTPRequest(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}