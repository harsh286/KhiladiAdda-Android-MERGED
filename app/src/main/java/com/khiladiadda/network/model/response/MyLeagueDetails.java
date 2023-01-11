package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyLeagueDetails {

    @SerializedName("past") @Expose private List<LeagueListDetails> past = null;
    @SerializedName("live") @Expose private List<LeagueListDetails> live = null;
    @SerializedName("upcoming") @Expose private List<LeagueListDetails> upcoming = null;

    public List<LeagueListDetails> getPast() {
        return past;
    }

    public void setPast(List<LeagueListDetails> past) {
        this.past = past;
    }

    public List<LeagueListDetails> getLive() {
        return live;
    }

    public void setLive(List<LeagueListDetails> live) {
        this.live = live;
    }

    public List<LeagueListDetails> getUpcoming() {
        return upcoming;
    }

    public void setUpcoming(List<LeagueListDetails> upcoming) {
        this.upcoming = upcoming;
    }

}