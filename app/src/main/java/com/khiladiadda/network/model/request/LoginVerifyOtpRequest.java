package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginVerifyOtpRequest {

    @SerializedName("mobile") @Expose private String mobileNumber;
    @SerializedName("otp") @Expose private String otp;
    @SerializedName("password") @Expose private String password;

    public LoginVerifyOtpRequest(String mobileNumber, String password, String otp) {
        this.mobileNumber = mobileNumber;
        this.otp = otp;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}