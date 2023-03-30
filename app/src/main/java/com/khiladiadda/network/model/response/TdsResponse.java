package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class TdsResponse extends BaseResponse {

    @SerializedName("response")
    @Expose
    private double tdsAmount;

    public double getTdsAmount() {
        return tdsAmount;
    }

    public void setTdsAmount(double tdsAmount) {
        this.tdsAmount = tdsAmount;
    }
}