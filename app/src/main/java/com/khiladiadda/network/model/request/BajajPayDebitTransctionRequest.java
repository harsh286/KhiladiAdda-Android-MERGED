package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BajajPayDebitTransctionRequest {
    @SerializedName("merchantId")
    @Expose
    String merchantId;
    @SerializedName("mobileNumber")
    @Expose
    String mobileNumber;
    @SerializedName("userToken")
    @Expose
    String userToken;
    @SerializedName("paymentAmount")
    @Expose
    String paymentAmount;
    @SerializedName("merchantTxnId")
    @Expose
    long merchantTxnId;
    @SerializedName("checkSum")
    @Expose
    String checkSum;
    @SerializedName("otpFlag")
    @Expose
    String otpFlag;
    @SerializedName("otp")
    @Expose
    String otp;
    @SerializedName("accessToken")
    @Expose
    String accessToken;
    @SerializedName("subMerchantId")
    @Expose
    String subMerchantId;
    @SerializedName("subMerchantName")
    @Expose
    String subMerchantName;

    public BajajPayDebitTransctionRequest(String merchantId, String mobileNumber, String userToken, String paymentAmount, long merchantTxnId, String checkSum, String otpFlag, String otp, String accessToken, String subMerchantId, String subMerchantName) {
        this.merchantId = merchantId;
        this.mobileNumber = mobileNumber;
        this.userToken = userToken;
        this.paymentAmount = paymentAmount;
        this.merchantTxnId = merchantTxnId;
        this.checkSum = checkSum;
        this.otpFlag = otpFlag;
        this.otp = otp;
        this.accessToken = accessToken;
        this.subMerchantId = subMerchantId;
        this.subMerchantName = subMerchantName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public long getMerchantTxnId() {
        return merchantTxnId;
    }

    public void setMerchantTxnId(long merchantTxnId) {
        this.merchantTxnId = merchantTxnId;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    public String getOtpFlag() {
        return otpFlag;
    }

    public void setOtpFlag(String otpFlag) {
        this.otpFlag = otpFlag;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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
