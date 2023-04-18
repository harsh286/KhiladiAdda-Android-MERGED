package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BajajAuthOtPPaymentRequest {
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
    @SerializedName("subMerchantId")
    @Expose
    String subMerchantId;
    @SerializedName("subMerchantName")
    @Expose
    String subMerchantName;

    public BajajAuthOtPPaymentRequest(String merchantId, String mobileNumber, String userToken, String paymentAmount, String subMerchantId, String subMerchantName) {
        this.merchantId = merchantId;
        this.mobileNumber = mobileNumber;
        this.userToken = userToken;
        this.paymentAmount = paymentAmount;
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
