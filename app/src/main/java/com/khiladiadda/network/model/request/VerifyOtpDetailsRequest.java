package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyOtpDetailsRequest {

// 	"uuid": "474079bb-4f0a-4286-bd09-d221ae96dcec",
//		"otp": "12345123451",
//		"share_code": "D212"

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("share_code")
    @Expose
    private String shareCode;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public VerifyOtpDetailsRequest(String uuid, String otp, String shareCode) {
        this.uuid = uuid;
        this.otp = otp;
        this.shareCode = shareCode;
    }

}