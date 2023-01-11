package com.khiladiadda.network.model.response.gamer_cash;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseFetchDetail {
    @SerializedName("coins")
    @Expose
    private int coins = 0;


    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

}
