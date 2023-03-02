package com.khiladiadda.network.model.response.deposite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchDepositLimitResponse {
    @SerializedName("dailyLimit")
    @Expose
    private Integer dailyLimit;
    @SerializedName("monthlyLimit")
    @Expose
    private Integer monthlyLimit;
    @SerializedName("limitUpdatedAt")
    @Expose
    private String limitUpdatedAt;
    @SerializedName("remainingLimit")
    @Expose
    private Integer remainingLimit;

    public Integer getRemainingLimit() {
        return remainingLimit;
    }

    public void setRemainingLimit(Integer remainingLimit) {
        this.remainingLimit = remainingLimit;
    }

    public Integer getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(Integer dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public Integer getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(Integer monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public String getLimitUpdatedAt() {
        return limitUpdatedAt;
    }

    public void setLimitUpdatedAt(String limitUpdatedAt) {
        this.limitUpdatedAt = limitUpdatedAt;
    }
}
