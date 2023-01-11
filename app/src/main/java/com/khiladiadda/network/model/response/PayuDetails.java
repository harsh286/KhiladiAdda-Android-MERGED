package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayuDetails {

    @SerializedName("amount") @Expose private long amount;
    @SerializedName("email") @Expose private String email;
    @SerializedName("firstname") @Expose private String firstname;
    @SerializedName("productinfo") @Expose private String productinfo;
    @SerializedName("txnid") @Expose private String txnid;
    @SerializedName("mobile") @Expose private String mobile;

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
