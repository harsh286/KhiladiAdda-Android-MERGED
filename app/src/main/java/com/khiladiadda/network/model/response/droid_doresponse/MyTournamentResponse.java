package com.khiladiadda.network.model.response.droid_doresponse;

import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class MyTournamentResponse extends BaseResponse {
    @SerializedName("response")
    private List<ResponseDataMyTournament> tournamentList;

    public List<ResponseDataMyTournament> getTournamentList() {
        return tournamentList;
    }

    public void setTournamentList(List<ResponseDataMyTournament> tournamentList) {
        this.tournamentList = tournamentList;
    }
}
