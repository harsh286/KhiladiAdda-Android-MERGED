package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayuSavePayment {
    @SerializedName("status") @Expose private String status;
    @SerializedName("amount") @Expose private String amount;
    @SerializedName("email") @Expose private String email;
    @SerializedName("txnid") @Expose private String txnid;
    @SerializedName("firstname") @Expose private String firstname;
    @SerializedName("productinfo") @Expose private String productinfo;
    @SerializedName("hash") @Expose private String hash;
    @SerializedName("coupon") @Expose private String coupon;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getProductinfo() {
        return productinfo;
    }

    public void setProductinfo(String productinfo) {
        this.productinfo = productinfo;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }
}