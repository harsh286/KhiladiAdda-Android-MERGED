package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChecksumRequest {

    @SerializedName("CUST_ID") @Expose private String customerID;
    @SerializedName("TXN_AMOUNT") @Expose private String amount;
    @SerializedName("EMAIL") @Expose private String email;
    @SerializedName("MOBILE_NO") @Expose private String mobile;
    @SerializedName("ORDER_ID") @Expose private String orderId;
    @SerializedName("CALLBACK_URL") @Expose private String callbackURL;
    @SerializedName("coupon") @Expose private String coupon;

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCallbackURL() {
        return callbackURL;
    }

    public void setCallbackURL(String callbackURL) {
        this.callbackURL = callbackURL;
    }

    public ChecksumRequest(String customerID, String amount, String email, String mobile, String orderId, String callbackURL, String coupon) {
        this.customerID = customerID;
        this.amount = amount;
        this.email = email;
        this.mobile = mobile;
        this.orderId = orderId;
        this.callbackURL = callbackURL;
        this.coupon = coupon;
    }
}
