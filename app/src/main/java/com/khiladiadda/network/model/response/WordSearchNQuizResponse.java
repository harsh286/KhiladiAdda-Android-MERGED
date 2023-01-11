package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WordSearchNQuizResponse {
    @SerializedName("played")
    @Expose
    private Integer played;
    @SerializedName("won")
    @Expose
    private Integer won;
    @SerializedName("lost")
    @Expose
    private Integer lost;
    @SerializedName("pending")
    @Expose
    private Integer pending;

    public Integer getPlayed() {
        return played;
    }

    public void setPlayed(Integer played) {
        this.played = played;
    }

    public Integer getWon() {
        return won;
    }

    public void setWon(Integer won) {
        this.won = won;
    }

    public Integer getLost() {
        return lost;
    }

    public void setLost(Integer lost) {
        this.lost = lost;
    }

    public Integer getPending() {
        return pending;
    }

    public void setPending(Integer pending) {
        this.pending = pending;
    }
}
