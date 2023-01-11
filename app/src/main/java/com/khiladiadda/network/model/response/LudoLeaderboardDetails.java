package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LudoLeaderboardDetails {

    @SerializedName("_id") @Expose private String id;
    @SerializedName("total") @Expose private double total;
    @SerializedName("name") @Expose private String name;
    @SerializedName("dp") @Expose private String dp;
    @SerializedName("n_ludo") @Expose private LudoDetails nLudo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public LudoDetails getnLudo() {
        return nLudo;
    }

    public void setnLudo(LudoDetails nLudo) {
        this.nLudo = nLudo;
    }
}
