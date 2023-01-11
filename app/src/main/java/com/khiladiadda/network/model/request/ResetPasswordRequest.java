package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPasswordRequest {

    @SerializedName("mobile") @Expose private String mobile;
    @SerializedName("otp") @Expose private String otp;
    @SerializedName("password") @Expose private String password;

    public ResetPasswordRequest(String mobile, String otp, String password) {
        this.mobile = mobile;
        this.otp = otp;
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOTP() {
        return otp;
    }

    public void setOTP(String otp) {
        this.otp = otp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
