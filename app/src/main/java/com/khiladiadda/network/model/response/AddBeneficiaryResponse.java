package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class AddBeneficiaryResponse extends BaseResponse {

    @SerializedName("response") @Expose private AddBeneficiaryDetails response;

    @SerializedName("manual_withdraw") @Expose private boolean manual_withdraw;

    public AddBeneficiaryDetails getResponse() {
        return response;
    }

    public void setResponse(AddBeneficiaryDetails response) {
        this.response = response;
    }

    public boolean isManual_withdraw() {
        return manual_withdraw;
    }

    public void setManual_withdraw(boolean manual_withdraw) {
        this.manual_withdraw = manual_withdraw;
    }

}