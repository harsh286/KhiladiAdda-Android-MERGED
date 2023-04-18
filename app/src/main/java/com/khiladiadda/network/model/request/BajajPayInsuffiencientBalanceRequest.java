package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BajajPayInsuffiencientBalanceRequest {
    @SerializedName("merchantId")
    @Expose
    String merchantId;
    @SerializedName("mobileNumber")
    @Expose
    String mobileNumber;
    @SerializedName("merchantTxnId")
    @Expose
    long merchantTxnId;
    @SerializedName("userToken")
    @Expose
    String userToken;
    @SerializedName("paymentAmount")
    @Expose
    String paymentAmount;
    @SerializedName("callBackUrl")
    @Expose
    String callBackUrl;
    @SerializedName("requestNumber")
    @Expose
    String requestNumber;
    @SerializedName("subMerchantId")
    @Expose
    String subMerchantId;

    public BajajPayInsuffiencientBalanceRequest(String merchantId, String mobileNumber, long merchantTxnId, String userToken, String paymentAmount, String callBackUrl, String requestNumber, String subMerchantId) {
        this.merchantId = merchantId;
        this.mobileNumber = mobileNumber;
        this.merchantTxnId = merchantTxnId;
        this.userToken = userToken;
        this.paymentAmount = paymentAmount;
        this.callBackUrl = callBackUrl;
        this.requestNumber = requestNumber;
        this.subMerchantId = subMerchantId;
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

    public long getMerchantTxnId() {
        return merchantTxnId;
    }

    public void setMerchantTxnId(long merchantTxnId) {
        this.merchantTxnId = merchantTxnId;
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

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public String getSubMerchantId() {
        return subMerchantId;
    }

    public void setSubMerchantId(String subMerchantId) {
        this.subMerchantId = subMerchantId;
    }
}
