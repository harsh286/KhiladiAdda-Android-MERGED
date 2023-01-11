package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BeneficiaryDetails {

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
    @SerializedName("city") @Expose private String city;
    @SerializedName("pincode") @Expose private String pincode;
    @SerializedName("vpa") @Expose private String VPA;
    @SerializedName("fund_account_id") @Expose private String fundAccountId;
    @SerializedName("address_verified") @Expose private boolean addressVerified;
    @SerializedName("transferType") @Expose private int transferType;
    @SerializedName("is_exists") @Expose private boolean isExist;

    private boolean selected;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getVPA() {
        return VPA;
    }

    public void setVPA(String VPA) {
        this.VPA = VPA;
    }

    public String getFundAccountId() {
        return fundAccountId;
    }

    public void setFundAccountId(String fundAccountId) {
        this.fundAccountId = fundAccountId;
    }

    public boolean isAddressVerified() {
        return addressVerified;
    }

    public void setAddressVerified(boolean addressVerified) {
        this.addressVerified = addressVerified;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getTransferType() {
        return transferType;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }
}
