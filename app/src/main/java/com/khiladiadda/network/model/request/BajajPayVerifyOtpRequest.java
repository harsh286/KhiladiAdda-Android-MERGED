package com.khiladiadda.network.model.request;

import com.google.gson.annotations.SerializedName;

public class BajajPayVerifyOtpRequest {
    @SerializedName("mobileNumber")
    public String mobileNumber;
    @SerializedName("merchantId")
    public String merchantId;
    @SerializedName("accessToken")
    public String accessToken;
    @SerializedName("otp")
    public String otp;
    @SerializedName("subMerchantId")
    public String subMerchantId;
    @SerializedName("subMerchantName")
    public String subMerchantName;

    public BajajPayVerifyOtpRequest(String mobileNumber, String merchantId, String subMerchantId, String accessToken, String otp, String subMerchantName) {
        this.mobileNumber = mobileNumber;
        this.merchantId = merchantId;
        this.accessToken = accessToken;
        this.otp = otp;
        this.subMerchantId = subMerchantId;
        this.subMerchantName = subMerchantName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getSubMerchantId() {
        return subMerchantId;
    }

    public void setSubMerchantId(String subMerchantId) {
        this.subMerchantId = subMerchantId;
    }

    public String getSubMerchantName() {
        return subMerchantName;
    }

    public void setSubMerchantName(String subMerchantName) {
        this.subMerchantName = subMerchantName;
    }
}
