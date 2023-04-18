package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BajajPayVerifyResponseDecrypt {
    @SerializedName("statusCode")
    @Expose
    private String statusCode;
    @SerializedName("statusMsg")
    @Expose
    private String statusMsg;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("userToken")
    @Expose
    private String userToken;
    @SerializedName("requestNumber")
    @Expose
    private String requestNumber;
    @SerializedName("merchantId")
    @Expose
    private String merchantId;
    @SerializedName("subMerchantId")
    @Expose
    private String subMerchantId;
    @SerializedName("subMerchantName")
    @Expose
    private String subMerchantName;
    @SerializedName("udf1")
    @Expose
    private String udf1;
    @SerializedName("udf2")
    @Expose
    private String udf2;
    @SerializedName("udf3")
    @Expose
    private String udf3;
    @SerializedName("udf4")
    @Expose
    private String udf4;
    @SerializedName("udf5")
    @Expose
    private String udf5;
    @SerializedName("udf6")
    @Expose
    private String udf6;

    public BajajPayVerifyResponseDecrypt(String statusCode, String statusMsg, String mobileNumber, String userToken, String requestNumber, String merchantId, String subMerchantId, String subMerchantName, String udf1, String udf2, String udf3, String udf4, String udf5, String udf6) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
        this.mobileNumber = mobileNumber;
        this.userToken = userToken;
        this.requestNumber = requestNumber;
        this.merchantId = merchantId;
        this.subMerchantId = subMerchantId;
        this.subMerchantName = subMerchantName;
        this.udf1 = udf1;
        this.udf2 = udf2;
        this.udf3 = udf3;
        this.udf4 = udf4;
        this.udf5 = udf5;
        this.udf6 = udf6;
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

    public String getUdf1() {
        return udf1;
    }

    public void setUdf1(String udf1) {
        this.udf1 = udf1;
    }

    public String getUdf2() {
        return udf2;
    }

    public void setUdf2(String udf2) {
        this.udf2 = udf2;
    }

    public String getUdf3() {
        return udf3;
    }

    public void setUdf3(String udf3) {
        this.udf3 = udf3;
    }

    public String getUdf4() {
        return udf4;
    }

    public void setUdf4(String udf4) {
        this.udf4 = udf4;
    }

    public String getUdf5() {
        return udf5;
    }

    public void setUdf5(String udf5) {
        this.udf5 = udf5;
    }

    public String getUdf6() {
        return udf6;
    }

    public void setUdf6(String udf6) {
        this.udf6 = udf6;
    }
}
