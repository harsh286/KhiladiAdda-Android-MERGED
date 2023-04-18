package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class BajajPayResponse extends BaseResponse {
    @SerializedName("encResponse")
    @Expose
    public String encResponse;
    @SerializedName("statusCode")
    @Expose
    public String statusCode;
    @SerializedName("statusMsg")
    @Expose
    public String statusMsg;
    @SerializedName("merchantId")
    @Expose
    public String merchantId;

    public BajajPayResponse(String encResponse, String statusCode, String statusMsg, String merchantId) {
        this.encResponse = encResponse;
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
        this.merchantId = merchantId;
    }

    public String getEncResponse() {
        return encResponse;
    }

    public void setEncResponse(String encResponse) {
        this.encResponse = encResponse;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
}
