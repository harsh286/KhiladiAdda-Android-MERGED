package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class WalletTransactionResponse extends BaseResponse {
    @SerializedName("response") @Expose private List<TransactionDetails> response = null;

    public List<TransactionDetails> getResponse() {
        return response;
    }

    public void setResponse(List<TransactionDetails> response) {
        this.response = response;
    }

}