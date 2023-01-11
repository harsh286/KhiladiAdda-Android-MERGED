package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateDOB {
    @SerializedName("dob") @Expose private String dob;

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public UpdateDOB(String dob) {
        this.dob = dob;
    }
}