package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequest {

    @SerializedName("mobile") @Expose private String mobile;
    @SerializedName("newpassword") @Expose private String newPassword;
    @SerializedName("oldpassword") @Expose private String oldPassword;

    public ChangePasswordRequest(String mobile, String newPassword, String oldPassword) {
        this.mobile = mobile;
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}