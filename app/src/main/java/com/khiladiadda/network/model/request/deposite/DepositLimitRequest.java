package com.khiladiadda.network.model.request.deposite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DepositLimitRequest {
    @SerializedName("dailyLimit")
    @Expose
    private Integer dailyLimit;

    public DepositLimitRequest(Integer dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public Integer getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(Integer dailyLimit) {
        this.dailyLimit = dailyLimit;
    }
}
