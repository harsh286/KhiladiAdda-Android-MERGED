package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddBeneficieryRazorpay {

    @SerializedName("account_type") @Expose private String accountType;
    @SerializedName("name") @Expose private String name;
    @SerializedName("ifsc") @Expose private String ifsc;
    @SerializedName("account_number") @Expose private String accountNumber;
    @SerializedName("address") @Expose private String address;
    @SerializedName("transferType") @Expose private int transferType;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTransferType() {
        return transferType;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }
}