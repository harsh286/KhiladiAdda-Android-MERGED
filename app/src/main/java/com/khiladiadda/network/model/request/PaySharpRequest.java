package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaySharpRequest {

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
