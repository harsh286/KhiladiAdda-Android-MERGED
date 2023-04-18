package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class BajajPayBalanceDecryptResponse extends BaseResponse {
    @SerializedName("walletBalance")
    @Expose
    String walletBalance;
    @SerializedName("mobileNumber")
    @Expose
    String mobileNumber;
    @SerializedName("walletStatus")
    @Expose
    String walletStatus;
    @SerializedName("requestNumber")
    @Expose
    String requestNumber;
    @SerializedName("subMerchantId")
    @Expose
    String subMerchantId;
    @SerializedName("subMerchantName")
    @Expose
    String subMerchantName;
    @SerializedName("merchantId")
    @Expose
    String merchantId;
    @SerializedName("userToken")
    @Expose
    String userToken;

    public BajajPayBalanceDecryptResponse(String walletBalance, String mobileNumber, String walletStatus, String requestNumber, String subMerchantId, String subMerchantName, String merchantId, String userToken) {
        this.walletBalance = walletBalance;
        this.mobileNumber = mobileNumber;
        this.walletStatus = walletStatus;
        this.requestNumber = requestNumber;
        this.subMerchantId = subMerchantId;
        this.subMerchantName = subMerchantName;
        this.merchantId = merchantId;
        this.userToken = userToken;
    }

    public String getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(String walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getWalletStatus() {
        return walletStatus;
    }

    public void setWalletStatus(String walletStatus) {
        this.walletStatus = walletStatus;
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

    public String getSubMerchantName() {
        return subMerchantName;
    }

    public void setSubMerchantName(String subMerchantName) {
        this.subMerchantName = subMerchantName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
