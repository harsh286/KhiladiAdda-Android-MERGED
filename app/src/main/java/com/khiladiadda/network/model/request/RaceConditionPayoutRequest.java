package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RaceConditionPayoutRequest {
    @SerializedName("bankAccount")
    @Expose
    private String bankAccount;
    @SerializedName("ifsc")
    @Expose
    private String ifsc;
    @SerializedName("vpa")
    @Expose
    private String vpa;
    @SerializedName("address1")
    @Expose
    private String address;
    @SerializedName("transferMode")
    @Expose
    private String transferMode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("transferType")
    @Expose
    private int transferType;
    @SerializedName("bene_type")
    @Expose
    private String benType;

    public RaceConditionPayoutRequest(String bankAccount, String ifsc, String vpa, String address, String transferMode, String name, int transferType) {
        this.bankAccount = bankAccount;
        this.ifsc = ifsc;
        this.vpa = vpa;
        this.address = address;
        this.transferMode = transferMode;
        this.name = name;
        this.transferType = transferType;
        this.benType = benType;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getVpa() {
        return vpa;
    }

    public void setVpa(String vpa) {
        this.vpa = vpa;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTransferMode() {
        return transferMode;
    }

    public void setTransferMode(String transferMode) {
        this.transferMode = transferMode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTransferType() {
        return transferType;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    public String getBenType() {
        return benType;
    }

    public void setBenType(String benType) {
        this.benType = benType;
    }
}
