package com.khiladiadda.battle.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyBattleList {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("winning_amount")
    private double winning_amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getWinning_amount() {
        return winning_amount;
    }

    public void setWinning_amount(double winning_amount) {
        this.winning_amount = winning_amount;
    }
}
