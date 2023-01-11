package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WithdrawLimitDetails {

    @SerializedName("totalWithdrawal")
    @Expose
    private long totalWithdrawal;
    @SerializedName("isLimitExceeded")
    @Expose
    private boolean isLimitExceeded;
    @SerializedName("limitAmount")
    @Expose
    private long limitAmount;
    @SerializedName("is_kyc_enabled")
    @Expose
    private boolean isKycEnabled;
    @SerializedName("aadhar_verified_via")
    @Expose
    private int aadharVerifiedVia;
    @SerializedName("aadharVerified")
    @Expose
    private boolean isAadharVerified;

    public long getTotalWithdrawal() {
        return totalWithdrawal;
    }

    public void setTotalWithdrawal(long totalWithdrawal) {
        this.totalWithdrawal = totalWithdrawal;
    }

    public boolean isIsLimitExceeded() {
        return isLimitExceeded;
    }

    public void setIsLimitExceeded(boolean isLimitExceeded) {
        this.isLimitExceeded = isLimitExceeded;
    }

    public long getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(long limitAmount) {
        this.limitAmount = limitAmount;
    }

    public boolean isLimitExceeded() {
        return isLimitExceeded;
    }

    public void setLimitExceeded(boolean limitExceeded) {
        isLimitExceeded = limitExceeded;
    }

    public boolean isKycEnabled() {
        return isKycEnabled;
    }

    public void setKycEnabled(boolean kycEnabled) {
        isKycEnabled = kycEnabled;
    }

    public int getAadharVerifiedVia() {
        return aadharVerifiedVia;
    }

    public void setAadharVerifiedVia(int aadharVerifiedVia) {
        this.aadharVerifiedVia = aadharVerifiedVia;
    }

    public boolean isAadharVerified() {
        return isAadharVerified;
    }

    public void setAadharVerified(boolean aadharVerified) {
        isAadharVerified = aadharVerified;
    }

}