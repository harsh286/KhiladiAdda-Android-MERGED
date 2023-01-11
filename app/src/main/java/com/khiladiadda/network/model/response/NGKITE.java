package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NGKITE extends NLudo {
    @SerializedName("played")
    @Expose
    private int played;

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }
}
