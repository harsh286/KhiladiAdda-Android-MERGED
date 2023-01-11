package com.khiladiadda.network.model.response.hth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.response.Coins;

public class UserCoins {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("coins")
    @Expose
    private Coins coins;
    @SerializedName("username")
    @Expose
    private String username;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coins getCoins() {
        return coins;
    }

    public void setCoins(Coins coins) {
        this.coins = coins;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
