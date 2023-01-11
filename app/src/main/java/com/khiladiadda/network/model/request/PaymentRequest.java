package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentRequest {

    @SerializedName("CUST_ID") @Expose private String customerID;
    @SerializedName("TYPE") @Expose private String type;
    @SerializedName("STATUS") @Expose private String status;
    @SerializedName("ORDER_ID") @Expose private String orderId;
    @SerializedName("BANKNAME") @Expose private String bankName;
    @SerializedName("TXN_AMOUNT") @Expose private String amount;
    @SerializedName("TXN_DATE") @Expose private String date;
    @SerializedName("TXN_ID") @Expose private String txnId;
    @SerializedName("PAYMENTMODE") @Expose private String paymentMode;
    @SerializedName("BANKTXNID") @Expose private String bankTxnId;
    @SerializedName("CURRENCY") @Expose private String currency;
    @SerializedName("GATEWAYNAME") @Expose private String gatewayName;
    @SerializedName("coupon") @Expose private String coupon;

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getBankTxnId() {
        return bankTxnId;
    }

    public void setBankTxnId(String bankTxnId) {
        this.bankTxnId = bankTxnId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getGatewayName() {
        return gatewayName;
    }

    public void setGatewayName(String gatewayName) {
        this.gatewayName = gatewayName;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

}