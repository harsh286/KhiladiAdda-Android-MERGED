package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordOtpRequest {

    @SerializedName("mobile") @Expose private String mobileNumber;
    @SerializedName("otp") @Expose private String otp;

    public ForgotPasswordOtpRequest(String mobileNumber, String otp) {
        this.mobileNumber = mobileNumber;
        this.otp = otp;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}