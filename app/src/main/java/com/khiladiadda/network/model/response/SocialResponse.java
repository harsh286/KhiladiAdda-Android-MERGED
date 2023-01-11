package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class SocialResponse extends BaseResponse {

    @SerializedName("otp_verified") @Expose private Boolean otpVerified;
    @SerializedName("jwt") @Expose private String jwt;

    public Boolean getOtpVerified() {
        return otpVerified;
    }

    public void setOtpVerified(Boolean otpVerified) {
        this.otpVerified = otpVerified;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

}