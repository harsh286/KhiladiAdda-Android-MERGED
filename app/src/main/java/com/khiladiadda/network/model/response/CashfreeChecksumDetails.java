package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CashfreeChecksumDetails {
    @SerializedName("appId") @Expose private String appId;
    @SerializedName("orderId") @Expose private String orderId;
    @SerializedName("orderAmount") @Expose private long orderAmount;
    @SerializedName("orderCurrency") @Expose private String orderCurrency;
    @SerializedName("orderNote") @Expose private String orderNote;
    @SerializedName("customerName") @Expose private String customerName;
    @SerializedName("customerEmail") @Expose private String customerEmail;
    @SerializedName("customerPhone") @Expose private long customerPhone;
    @SerializedName("returnUrl") @Expose private String returnUrl;
    @SerializedName("notifyUrl") @Expose private String notifyUrl;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderCurrency() {
        return orderCurrency;
    }

    public void setOrderCurrency(String orderCurrency) {
        this.orderCurrency = orderCurrency;
    }

    public String getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public long getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(long customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

}
