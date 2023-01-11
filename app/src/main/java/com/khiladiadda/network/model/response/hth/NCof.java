package com.khiladiadda.network.model.response.hth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NCof {
    @SerializedName("lost")
    @Expose
    private Integer lost;
    @SerializedName("won")
    @Expose
    private Integer won;

    public Integer getLost() {
        return lost;
    }

    public void setLost(Integer lost) {
        this.lost = lost;
    }

    public Integer getWon() {
        return won;
    }

    public void setWon(Integer won) {
        this.won = won;
    }

}
