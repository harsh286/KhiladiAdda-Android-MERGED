package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FirebaseRequest {

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

    public FirebaseRequest(String platform, String deviceId, String token) {
        this.platform = platform;
        this.deviceId = deviceId;
        this.token = token;
    }

}