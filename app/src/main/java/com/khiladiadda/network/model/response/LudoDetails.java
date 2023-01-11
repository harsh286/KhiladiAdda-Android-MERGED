package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LudoDetails {

    @SerializedName("won") @Expose private long won;
    @SerializedName("lost") @Expose private long lost;

    public long getWon() {
        return won;
    }

    public void setWon(long won) {
        this.won = won;
    }

    public long getLost() {
        return lost;
    }

    public void setLost(long lost) {
        this.lost = lost;
    }

}
