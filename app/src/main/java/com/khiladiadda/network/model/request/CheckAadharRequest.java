package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckAadharRequest {

    @SerializedName("aadharNumber")
    @Expose
    private String aadharNumber;

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public CheckAadharRequest(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }
}
