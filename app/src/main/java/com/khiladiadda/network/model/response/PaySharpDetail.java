package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaySharpDetail {

    @SerializedName("paysharpReferenceNo")
    @Expose
    private String paysharpReferenceNo;
    @SerializedName("intentUrl")
    @Expose
    private String intentUrl;
    @SerializedName("phonepeUrl")
    @Expose
    private String phonepeUrl;
    @SerializedName("gpayUrl")
    @Expose
    private String gpayUrl;
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("amount")
    @Expose
    private long amount;
    @SerializedName("customerId")
    @Expose
    private String customerId;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("customerMobileNo")
    @Expose
    private String customerMobileNo;
    @SerializedName("customerEmail")
    @Expose
    private String customerEmail;
    @SerializedName("remarks")
    @Expose
    private String remarks;

    public String getPaysharpReferenceNo() {
        return paysharpReferenceNo;
    }

    public void setPaysharpReferenceNo(String paysharpReferenceNo) {
        this.paysharpReferenceNo = paysharpReferenceNo;
    }

    public String getIntentUrl() {
        return intentUrl;
    }

    public void setIntentUrl(String intentUrl) {
        this.intentUrl = intentUrl;
    }

    public String getPhonepeUrl() {
        return phonepeUrl;
    }

    public void setPhonepeUrl(String phonepeUrl) {
        this.phonepeUrl = phonepeUrl;
    }

    public String getGpayUrl() {
        return gpayUrl;
    }

    public void setGpayUrl(String gpayUrl) {
        this.gpayUrl = gpayUrl;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobileNo() {
        return customerMobileNo;
    }

    public void setCustomerMobileNo(String customerMobileNo) {
        this.customerMobileNo = customerMobileNo;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
