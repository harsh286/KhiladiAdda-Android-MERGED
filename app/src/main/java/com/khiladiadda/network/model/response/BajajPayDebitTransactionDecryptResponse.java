package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class BajajPayDebitTransactionDecryptResponse extends BaseResponse {
    @SerializedName("statusCode")
    @Expose
    String statusCode;
    @SerializedName("statusMsg")
    @Expose
    String statusMsg;
    @SerializedName("txnDateTime")
    @Expose
    String txnDateTime;
    @SerializedName("paymentAmount")
    @Expose
    String paymentAmount;
    @SerializedName("merchantTxnId")
    @Expose
    String merchantTxnId;
    @SerializedName("merchantId")
    @Expose
    String merchantId;
    @SerializedName("bfltransactionId")
    @Expose
    String bflTransactionId;

    public BajajPayDebitTransactionDecryptResponse(String statusCode, String statusMsg, String txnDateTime, String paymentAmount, String merchantTxnId, String merchantId, String bflTransactionId) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
        this.txnDateTime = txnDateTime;
        this.paymentAmount = paymentAmount;
        this.merchantTxnId = merchantTxnId;
        this.merchantId = merchantId;
        this.bflTransactionId = bflTransactionId;
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

    public String getMerchantTxnId() {
        return merchantTxnId;
    }

    public void setMerchantTxnId(String merchantTxnId) {
        this.merchantTxnId = merchantTxnId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getBflTransactionId() {
        return bflTransactionId;
    }

    public void setBflTransactionId(String bflTransactionId) {
        this.bflTransactionId = bflTransactionId;
    }
}
