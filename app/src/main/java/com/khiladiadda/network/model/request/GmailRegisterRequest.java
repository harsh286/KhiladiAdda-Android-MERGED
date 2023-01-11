package com.khiladiadda.network.model.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GmailRegisterRequest implements Parcelable {

    @SerializedName("gmail_id") @Expose public String gmailId;
    @SerializedName("name") @Expose public String name;
    @SerializedName("username") @Expose public String username;
    @SerializedName("email") @Expose public String email;
    @SerializedName("dp") @Expose public String dp;
    @SerializedName("mobile") @Expose public String mobile;
    @SerializedName("invitecode") @Expose public String inviteCode;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public GmailRegisterRequest(String gmailId, String name, String username, String email, String dp, String mobile, String inviteCode, String platform, String deviceId, String token) {
        this.gmailId = gmailId;
        this.name = name;
        this.username = username;
        this.email = email;
        this.dp = dp;
        this.mobile = mobile;
        this.inviteCode = inviteCode;
        this.platform = platform;
        this.deviceId = deviceId;
        this.token = token;
    }


    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.gmailId);
        dest.writeString(this.name);
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeString(this.dp);
        dest.writeString(this.mobile);
        dest.writeString(this.inviteCode);
        dest.writeString(this.platform);
        dest.writeString(this.deviceId);
        dest.writeString(this.token);
    }

    protected GmailRegisterRequest(Parcel in) {
        this.gmailId = in.readString();
        this.name = in.readString();
        this.username = in.readString();
        this.email = in.readString();
        this.dp = in.readString();
        this.mobile = in.readString();
        this.inviteCode = in.readString();
        this.platform = in.readString();
        this.deviceId = in.readString();
        this.token = in.readString();
    }

    public static final Parcelable.Creator<GmailRegisterRequest> CREATOR = new Parcelable.Creator<GmailRegisterRequest>() {
        @Override public GmailRegisterRequest createFromParcel(Parcel source) {
            return new GmailRegisterRequest(source);
        }

        @Override public GmailRegisterRequest[] newArray(int size) {
            return new GmailRegisterRequest[size];
        }
    };
}