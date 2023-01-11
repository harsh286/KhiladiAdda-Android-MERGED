package com.khiladiadda.network.model.response.droid_doresponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseData {

    @SerializedName("trending")
    private List<TournamentTrendingList> tournamentTrendingList;
    @SerializedName("allTournament")
    private List<TournamentTrendingList> tournamentList;


    public List<TournamentTrendingList> getTrendingList() {
        return tournamentTrendingList;
    }

    public void setTrendingList(List<TournamentTrendingList> tournamentTrendingList) {
        this.tournamentTrendingList = tournamentTrendingList;
    }

    public List<TournamentTrendingList> getTournamentList() {
        return tournamentList;
    }

    public void setTournamentList(List<TournamentTrendingList> tournamentList) {
        this.tournamentList = tournamentList;
    }
}
