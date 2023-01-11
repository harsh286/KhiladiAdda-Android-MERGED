package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wallets {
    @SerializedName("bonus")
    @Expose
    private double bonus;
    @SerializedName("deposit")
    @Expose
    private double deposit;
    @SerializedName("winning")
    @Expose
    private double winning;

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

    public double getWinning() {
        return winning;
    }

    public void setWinning(double winning) {
        this.winning = winning;
    }
}
