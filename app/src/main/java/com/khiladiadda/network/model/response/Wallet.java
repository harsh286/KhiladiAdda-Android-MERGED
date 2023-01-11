package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wallet {

    @SerializedName("winning") @Expose private double winning;
    @SerializedName("bonus") @Expose private double bonus;
    @SerializedName("deposit") @Expose private double deposit;

    public double getWinning() {
        return winning;
    }

    public void setWinning(double winning) {
        this.winning = winning;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }
}
