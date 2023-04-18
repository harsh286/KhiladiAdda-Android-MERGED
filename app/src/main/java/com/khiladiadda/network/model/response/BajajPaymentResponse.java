package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class BajajPaymentResponse extends BaseResponse {
    @SerializedName("mobileNumber")
    @Expose
    String mobileNumber;
    @SerializedName("otpFlag")
    @Expose
    String otpFlag;
    @SerializedName("otpMessage")
    @Expose
    String otpMessage;
    @SerializedName("accessToken")
    @Expose
    String accessToken;
    @SerializedName("merchantId")
    @Expose
    String merchantId;
    @SerializedName("userToken")
    @Expose
    String userToken;
    @SerializedName("requestNumber")
    @Expose
    String requestNumber;

    public BajajPaymentResponse(String mobileNumber, String otpFlag, String otpMessage, String accessToken, String merchantId, String userToken, String requestNumber) {
        this.mobileNumber = mobileNumber;
        this.otpFlag = otpFlag;
        this.otpMessage = otpMessage;
        this.accessToken = accessToken;
        this.merchantId = merchantId;
        this.userToken = userToken;
        this.requestNumber = requestNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOtpFlag() {
        return otpFlag;
    }

    public void setOtpFlag(String otpFlag) {
        this.otpFlag = otpFlag;
    }

    public String getOtpMessage() {
        return otpMessage;
    }

    public void setOtpMessage(String otpMessage) {
        this.otpMessage = otpMessage;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }
}
