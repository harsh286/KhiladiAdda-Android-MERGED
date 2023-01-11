package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class ProfileTransactionResponse extends BaseResponse {

    @SerializedName("response") @Expose private ProfileDetails response;
    @SerializedName("recent_transaction") @Expose private List<TransactionDetails> transactionDetails = null;
    @SerializedName("new_payment")@Expose private boolean Wallet;
    @SerializedName("paytm_enabled")@Expose private boolean PaytmEnabled;

    public ProfileDetails getResponse() {
        return response;
    }

    public void setResponse(ProfileDetails response) {
        this.response = response;
    }

    public List<TransactionDetails> getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(List<TransactionDetails> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public boolean getWallet() {
        return Wallet;
    }

    public void setWallet(boolean wallet) {
        Wallet = wallet;
    }

    public boolean getPaytmEnabled() {
        return PaytmEnabled;
    }

    public void setPaytmEnabled(boolean paytmEnabled) {
        PaytmEnabled = paytmEnabled;
    }
}