package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GmailVerifyRequest {

    @SerializedName("gmail_id") @Expose public String gmailId;
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

    public String getGmailId() {
        return gmailId;
    }

    public void setGmailId(String gmailId) {
        this.gmailId = gmailId;
    }

    public GmailVerifyRequest(String gmailId, String platform, String deviceId, String token) {
        this.gmailId = gmailId;
        this.platform = platform;
        this.deviceId = deviceId;
        this.token = token;
    }
}