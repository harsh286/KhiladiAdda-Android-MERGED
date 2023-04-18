package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class BajajPayBalanceFinalDecryptResponse extends BaseResponse {
    @SerializedName("merchantId")
    @Expose
    String merchantId;
    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("txnDateTime")
    @Expose
    String txnDateTime;
    @SerializedName("paymentAmount")
    @Expose
    String paymentAmount;
    @SerializedName("statusCode")
    @Expose
    String statusCode;
    @SerializedName("statusMsg")
    @Expose
    String statusMsg;
    @SerializedName("merchantTxnId")
    @Expose
    String merchantTxnId;
    @SerializedName("requestNumber")
    @Expose
    String requestNumber;
    @SerializedName("udf1")
    @Expose
    String udf1;
    @SerializedName("bfltransactionId")
    @Expose
    String bflTransactionId;

    public BajajPayBalanceFinalDecryptResponse(String merchantId, String status, String txnDateTime, String paymentAmount, String statusCode, String statusMsg, String merchantTxnId, String requestNumber, String udf1, String bflTransactionId) {
        this.merchantId = merchantId;
        this.status = status;
        this.txnDateTime = txnDateTime;
        this.paymentAmount = paymentAmount;
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
        this.merchantTxnId = merchantTxnId;
        this.requestNumber = requestNumber;
        this.udf1 = udf1;
        this.bflTransactionId = bflTransactionId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTxnDateTime() {
        return txnDateTime;
    }

    public void setTxnDateTime(String txnDateTime) {
        this.txnDateTime = txnDateTime;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
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

    public String getMerchantTxnId() {
        return merchantTxnId;
    }

    public void setMerchantTxnId(String merchantTxnId) {
        this.merchantTxnId = merchantTxnId;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public String getUdf1() {
        return udf1;
    }

    public void setUdf1(String udf1) {
        this.udf1 = udf1;
    }

    public String getBflTransactionId() {
        return bflTransactionId;
    }

    public void setBflTransactionId(String bflTransactionId) {
        this.bflTransactionId = bflTransactionId;
    }
}
