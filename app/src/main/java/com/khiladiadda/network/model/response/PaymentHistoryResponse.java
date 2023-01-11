package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class PaymentHistoryResponse extends BaseResponse {

    @SerializedName("response") @Expose private List<PaymentHistoryDetails> response = null;

    public List<PaymentHistoryDetails> getResponse() {
        return response;
    }

    public void setResponse(List<PaymentHistoryDetails> response) {
        this.response = response;
    }
}
