package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BajajPayGetOtpRequest {
    @SerializedName("mobileNumber")
    @Expose
    String mobileNumber;
    @SerializedName("merchantId")
    @Expose
    String merchantId;
    @SerializedName("subMerchantId")
    @Expose
    String subMerchantId;
    @SerializedName("subMerchantName")
    @Expose
    String subMerchantName;

    public BajajPayGetOtpRequest(String mobileNumber, String merchantId, String subMerchantId, String subMerchantName) {
        this.mobileNumber = mobileNumber;
        this.merchantId = merchantId;
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
