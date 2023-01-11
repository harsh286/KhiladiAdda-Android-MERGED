package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationRequest {

    @SerializedName("name") @Expose private String userName;
    @SerializedName("mobile") @Expose private String mobileNumber;
    @SerializedName("email") @Expose private String email;
    @SerializedName("password") @Expose private String password;
    @SerializedName("invitecode") @Expose private String inviteCode;

    public RegistrationRequest(String userName,String mobileNumber, String inviteCode) {
        this.userName = userName;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.password = password;
        this.inviteCode = inviteCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
