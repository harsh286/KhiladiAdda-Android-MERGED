package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransferSilverCoinsRequest {

    @SerializedName("wallet") @Expose private String wallet;
    @SerializedName("amount") @Expose private double amount;

    public TransferSilverCoinsRequest(String wallet, double amount) {
        this.wallet = wallet;
        this.amount = amount;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
