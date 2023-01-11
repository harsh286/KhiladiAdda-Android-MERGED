package com.khiladiadda.network.model.response.ludoTournament;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LudoTmtTournamentDetailsResponse {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("currentLevelInfo")
    @Expose
    private LudoTmtCurrentLevelInfo currentLevelInfo;
    @SerializedName("ttMatch")
    @Expose
    private Integer ttMatch;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LudoTmtCurrentLevelInfo getCurrentLevelInfo() {
        return currentLevelInfo;
    }

    public void setCurrentLevelInfo(LudoTmtCurrentLevelInfo currentLevelInfo) {
        this.currentLevelInfo = currentLevelInfo;
    }

    public Integer getTtMatch() {
        return ttMatch;
    }

    public void setTtMatch(Integer ttMatch) {
        this.ttMatch = ttMatch;
    }
}
