package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coins {

    @SerializedName("winning") @Expose private double winning;
    @SerializedName("deposit") @Expose private double deposit;
    @SerializedName("bonus") @Expose private double bonus;
    @SerializedName("silver") @Expose private double silver;

    public double getWinning() {
        return winning;
    }

    public void setWinning(double winning) {
        this.winning = winning;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getSilver() {
        return silver;
    }

    public void setSilver(double silver) {
        this.silver = silver;
    }
}