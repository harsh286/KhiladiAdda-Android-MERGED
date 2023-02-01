package com.khiladiadda.network.model.request.ludoTournament;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LudoTournamentFetchRequest {
    @SerializedName("startDate")
    @Expose
    private boolean startDate;

    public LudoTournamentFetchRequest(boolean startDate) {
        this.startDate = startDate;
    }

    public boolean isStartDate() {
        return startDate;
    }

    public void setStartDate(boolean startDate) {
        this.startDate = startDate;
    }
}
