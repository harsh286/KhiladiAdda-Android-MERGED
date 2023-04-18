package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class BajajPayDeLinkWalletDecryptResponse extends BaseResponse {
    @SerializedName("mobileNumber")
    @Expose
    String mobileNumber;
    @SerializedName("userDelinkStatus")
    @Expose
    String userDelinkStatus;
    @SerializedName("requestNumber")
    @Expose
    String requestNumber;
    @SerializedName("merchantId")
    @Expose
    String merchantId;

    public BajajPayDeLinkWalletDecryptResponse(String mobileNumber, String userDelinkStatus, String requestNumber, String merchantId) {
        this.mobileNumber = mobileNumber;
        this.userDelinkStatus = userDelinkStatus;
        this.requestNumber = requestNumber;
        this.merchantId = merchantId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserDelinkStatus() {
        return userDelinkStatus;
    }

    public void setUserDelinkStatus(String userDelinkStatus) {
        this.userDelinkStatus = userDelinkStatus;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
}
