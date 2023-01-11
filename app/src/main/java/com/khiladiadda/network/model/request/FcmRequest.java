package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FcmRequest {

    @SerializedName("leagueDisabled")
    @Expose
    private boolean leagueDisabled;

    public boolean isLeagueDisabled() {
        return leagueDisabled;
    }

    public void setLeagueDisabled(boolean leagueDisabled) {
        this.leagueDisabled = leagueDisabled;
    }
}
