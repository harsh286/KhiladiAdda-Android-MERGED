package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AadharUpdateRequest {

    @SerializedName("aadhar_info") @Expose
    private UpdateAadhaarRequest aadharInfo;

    public UpdateAadhaarRequest getAadharInfo() {
        return aadharInfo;
    }

    public void setAadharInfo(UpdateAadhaarRequest aadharInfo) {
        this.aadharInfo = aadharInfo;
    }

    public AadharUpdateRequest(UpdateAadhaarRequest aadharInfo) {
        this.aadharInfo = aadharInfo;
    }

}