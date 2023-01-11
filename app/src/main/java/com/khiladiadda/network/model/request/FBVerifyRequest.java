package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FBVerifyRequest {

    @SerializedName("access_token") @Expose private String fbToken;
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

    public String getFbToken() {
        return fbToken;
    }

    public void setFbToken(String fbToken) {
        this.fbToken = fbToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public FBVerifyRequest(String fbToken, String platform, String deviceId, String token) {
        this.fbToken = fbToken;
        this.platform = platform;
        this.deviceId = deviceId;
        this.token = token;
    }
}