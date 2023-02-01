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
    @SerializedName("tStatus")
    @Expose
    private int tStatus;
    @SerializedName("is_out")
    @Expose
    private boolean isOut;

    public boolean isOut() {
        return isOut;
    }

    public void setOut(boolean out) {
        isOut = out;
    }

    public int gettStatus() {
        return tStatus;
    }

    public void settStatus(int tStatus) {
        this.tStatus = tStatus;
    }

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
