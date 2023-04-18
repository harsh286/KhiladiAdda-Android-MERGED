package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BajajPayDeLinkWalletRequest {
    @SerializedName("merchantId")
    @Expose
    String merchantId;
    @SerializedName("mobileNumber")
    @Expose
    String mobileNumber;

    public BajajPayDeLinkWalletRequest(String merchantId, String mobileNumber) {
        this.merchantId = merchantId;
        this.mobileNumber = mobileNumber;
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
}
