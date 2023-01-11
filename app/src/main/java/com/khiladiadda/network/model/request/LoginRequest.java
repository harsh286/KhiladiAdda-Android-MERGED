package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("mobile") @Expose private String mobile;
    @SerializedName("password") @Expose private String password;
    @SerializedName("platform") @Expose private String platform;
    @SerializedName("device_id") @Expose private String deviceId;
    @SerializedName("token") @Expose private String token;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginRequest(String mobile, String platform, String deviceId, String token) {
        this.mobile = mobile;
        this.password = password;
        this.platform = platform;
        this.deviceId = deviceId;
        this.token = token;
    }
}