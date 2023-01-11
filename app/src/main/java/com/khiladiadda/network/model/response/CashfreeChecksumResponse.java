package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class CashfreeChecksumResponse extends BaseResponse {

    @SerializedName("response") @Expose private String checksum;
    @SerializedName("details") @Expose private CashfreeChecksumDetails details;

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public CashfreeChecksumDetails getDetails() {
        return details;
    }

    public void setDetails(CashfreeChecksumDetails details) {
        this.details = details;
    }

}