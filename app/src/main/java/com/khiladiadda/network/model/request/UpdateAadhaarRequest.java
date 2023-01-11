package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateAadhaarRequest {

    @SerializedName("name") @Expose private String name;
    @SerializedName("email") @Expose private String email;
    @SerializedName("dob") @Expose private String dob;
    @SerializedName("gender") @Expose private String gender;
    @SerializedName("address") @Expose private String address;
    @SerializedName("father_name") @Expose private String fatherName;
    @SerializedName("mother_name") @Expose private String motherName;
    @SerializedName("number") @Expose private String aadharNo;
    @SerializedName("url") @Expose private String url;
    @SerializedName("urlAadhar") @Expose private String urlAadhar;

    public UpdateAadhaarRequest(String name, String url, String urlAadhar, String email, String dob, String gender, String address, String fatherName, String motherName, String aadharNo) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.aadharNo = aadharNo;
        this.url = url;
        this.urlAadhar = urlAadhar;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlAadhar() {
        return urlAadhar;
    }

    public void setUrlAadhar(String urlAadhar) {
        this.urlAadhar = urlAadhar;
    }
}