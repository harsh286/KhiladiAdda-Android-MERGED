package com.khiladiadda.login;

import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class TrueCallerResponse extends BaseResponse {
    @SerializedName("otp_verified")
    String otp_verified;
    @SerializedName("jwt")
    String jwt;
    @SerializedName("isExists")
    boolean isExists;

    public TrueCallerResponse(String otp_verified, String jwt, boolean isExists) {
        this.otp_verified = otp_verified;
        this.jwt = jwt;
        this.isExists = isExists;
    }

    public String getOtp_verified() {
        return otp_verified;
    }

    public void setOtp_verified(String otp_verified) {
        this.otp_verified = otp_verified;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public boolean isExists() {
        return isExists;
    }

    public void setExists(boolean exists) {
        isExists = exists;
    }
}
