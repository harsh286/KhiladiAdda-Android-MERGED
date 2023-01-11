package com.khiladiadda.network.model.response.droid_doresponse;

import com.google.gson.annotations.SerializedName;

public class TournamentDetailRequest {
    @SerializedName("tournamentId")
    public String tournamentId;


    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }
}
