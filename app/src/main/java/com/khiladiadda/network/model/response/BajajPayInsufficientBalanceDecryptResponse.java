package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class BajajPayInsufficientBalanceDecryptResponse extends BaseResponse {
    @SerializedName("merchantId")
    @Expose
    String merchantId;
    @SerializedName("mobileNumber")
    @Expose
    String mobileNumber;
    @SerializedName("userToken")
    @Expose
    String userToken;
    @SerializedName("redirectUrl")
    @Expose
    String redirectUrl;
    @SerializedName("paymentAmount")
    @Expose
    String paymentAmount;
    @SerializedName("merchantTxnId")
    @Expose
    String merchantTxnId;

    public BajajPayInsufficientBalanceDecryptResponse(String merchantId, String mobileNumber, String userToken, String redirectUrl, String paymentAmount, String merchantTxnId) {
        this.merchantId = merchantId;
        this.mobileNumber = mobileNumber;
        this.userToken = userToken;
        this.redirectUrl = redirectUrl;
        this.paymentAmount = paymentAmount;
        this.merchantTxnId = merchantTxnId;
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

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getMerchantTxnId() {
        return merchantTxnId;
    }

    public void setMerchantTxnId(String merchantTxnId) {
        this.merchantTxnId = merchantTxnId;
    }
}
