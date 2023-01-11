package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOtpDetailsRequest {

//    "uuid": "07d4296c-26d1-4e41-81ee-f73d87016aa1",
//            "aadhaar":"424582822484",
//            "captcha": "DCLTQ",
//            "verification_type": "OTP",
//            "consent":"YES"

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("aadhaar")
    @Expose
    private String aadhaar;
    @SerializedName("captcha")
    @Expose
    private String captcha;
    @SerializedName("verification_type")
    @Expose
    private String verificationnType;
    @SerializedName("consent")
    @Expose
    private String consent;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAadhaar() {
        return aadhaar;
    }

    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getVerificationnType() {
        return verificationnType;
    }

    public void setVerificationnType(String verificationnType) {
        this.verificationnType = verificationnType;
    }

    public String getConsent() {
        return consent;
    }

    public void setConsent(String consent) {
        this.consent = consent;
    }

    public GetOtpDetailsRequest(String uuid, String aadhaar, String captcha, String verificationnType, String consent) {
        this.uuid = uuid;
        this.aadhaar = aadhaar;
        this.captcha = captcha;
        this.verificationnType = verificationnType;
        this.consent = consent;
    }
}
