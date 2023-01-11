package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpRequest {

    @SerializedName("mobile") @Expose private String mobileNumber;
    @SerializedName("otp") @Expose private String otp;
    @SerializedName("platform") @Expose private String platform;
    @SerializedName("device_id") @Expose private String deviceId;
    @SerializedName("token") @Expose private String token;

    public OtpRequest(String mobileNumber, String otp, String platform, String deviceId, String token) {
        this.mobileNumber = mobileNumber;
        this.otp = otp;
        this.platform = platform;
        this.deviceId = deviceId;
        this.token = token;
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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}