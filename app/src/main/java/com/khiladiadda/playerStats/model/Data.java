package com.khiladiadda.playerStats.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("bowling")
    @Expose
    private Bowling bowling;
    @SerializedName("batting")
    @Expose
    private Batting batting;

    public Bowling getBowling() {
        return bowling;
    }

    public void setBowling(Bowling bowling) {
        this.bowling = bowling;
    }

    public Batting getBatting() {
        return batting;
    }

    public void setBatting(Batting batting) {
        this.batting = batting;
    }

}
