package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class PayoutResponse extends BaseResponse {

    @SerializedName("withdraw_enabled") @Expose private boolean withdraw_enabled;
    @SerializedName("manual_withdraw") @Expose private boolean manual_withdraw;

    public boolean isWithdraw_enabled() {
        return withdraw_enabled;
    }

    public void setWithdraw_enabled(boolean withdraw_enabled) {
        this.withdraw_enabled = withdraw_enabled;
    }

    public boolean isManual_withdraw() {
        return manual_withdraw;
    }

    public void setManual_withdraw(boolean manual_withdraw) {
        this.manual_withdraw = manual_withdraw;
    }
}