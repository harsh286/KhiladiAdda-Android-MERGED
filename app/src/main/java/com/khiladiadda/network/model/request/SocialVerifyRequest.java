package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocialVerifyRequest {

    @SerializedName("mobile") @Expose private String mobileNumber;

    @SerializedName("invite_code") @Expose private String inviteCode;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public SocialVerifyRequest(String mobileNumber, String inviteCode) {
        this.mobileNumber = mobileNumber;
        this.inviteCode = inviteCode;
    }
}
