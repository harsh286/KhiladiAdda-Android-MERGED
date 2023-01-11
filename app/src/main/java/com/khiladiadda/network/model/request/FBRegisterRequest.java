package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FBRegisterRequest {

    @SerializedName("access_token") @Expose private String fbToken;
    @SerializedName("mobile") @Expose public String mobile;
    @SerializedName("invitecode") @Expose public String inviteCode;

    public String getFBToken() {
        return fbToken;
    }

    public void setFBToken(String fbToken) {
        this.fbToken = fbToken;
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

    public FBRegisterRequest(String fbToken, String mobile, String inviteCode) {
        this.fbToken = fbToken;
        this.mobile = mobile;
        this.inviteCode = inviteCode;
    }
}