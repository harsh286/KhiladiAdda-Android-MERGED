package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class BeneficiaryResponse extends BaseResponse {

    @SerializedName("response") @Expose private List<BeneficiaryDetails> response = null;

    @SerializedName("manual_withdraw") @Expose private boolean isManualWithdraw;

    @SerializedName("payout_enable") @Expose private int payoutEnable;

    @SerializedName("withdraw_commission") @Expose private List<WithdrawComissionDetails> withdrawCommission = null;

    @SerializedName("is_withdraw_verified") @Expose private boolean isWithdrawVerified;

    @SerializedName("userProfileData") @Expose private ProfileDetails profileDetails;
    @SerializedName("parallel_payout") @Expose private int parallelPayout;

    public int getParallelPayout() {
        return parallelPayout;
    }

    public void setParallelPayout(int parallelPayout) {
        this.parallelPayout = parallelPayout;
    }

    public List<BeneficiaryDetails> getResponse() {
        return response;
    }

    public void setResponse(List<BeneficiaryDetails> response) {
        this.response = response;
    }

    public boolean isManualWithdraw() {
        return isManualWithdraw;
    }

    public void setManualWithdraw(boolean manualWithdraw) {
        isManualWithdraw = manualWithdraw;
    }

    public int getPayoutEnable() {
        return payoutEnable;
    }

    public void setPayoutEnable(int payoutEnable) {
        this.payoutEnable = payoutEnable;
    }

    public List<WithdrawComissionDetails> getWithdrawCommission() {
        return withdrawCommission;
    }

    public void setWithdrawCommission(List<WithdrawComissionDetails> withdrawCommission) {
        this.withdrawCommission = withdrawCommission;
    }

    public boolean isWithdrawVerified() {
        return isWithdrawVerified;
    }

    public void setWithdrawVerified(boolean withdrawVerified) {
        isWithdrawVerified = withdrawVerified;
    }

    public ProfileDetails getProfileDetails() {
        return profileDetails;
    }

    public void setProfileDetails(ProfileDetails profileDetails) {
        this.profileDetails = profileDetails;
    }
}