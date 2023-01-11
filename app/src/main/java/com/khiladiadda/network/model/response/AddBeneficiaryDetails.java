package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddBeneficiaryDetails {

    @SerializedName("_id") @Expose private String id;
    @SerializedName("name") @Expose private String name;
    @SerializedName("email") @Expose private String email;
    @SerializedName("phone") @Expose private String phone;
    @SerializedName("bankAccount") @Expose private String bankAccount;
    @SerializedName("ifsc") @Expose private String ifsc;
    @SerializedName("address1") @Expose private String address1;
    @SerializedName("transferMode") @Expose private String transferMode;
    @SerializedName("created_at") @Expose private String createdAt;
    @SerializedName("user_id") @Expose private String userId;
    @SerializedName("beneId") @Expose private String beneId;
    @SerializedName("is_verified") @Expose private boolean isVerified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getTransferMode() {
        return transferMode;
    }

    public void setTransferMode(String transferMode) {
        this.transferMode = transferMode;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBeneId() {
        return beneId;
    }

    public void setBeneId(String beneId) {
        this.beneId = beneId;
    }

    public boolean isIsVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

}
