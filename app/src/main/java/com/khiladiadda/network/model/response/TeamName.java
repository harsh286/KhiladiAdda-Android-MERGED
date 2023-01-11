package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamName {

    @SerializedName("_id") @Expose private String id;
    @SerializedName("team_name") @Expose private String teamName;
    @SerializedName("team_slot") @Expose private long teamSlot;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public long getTeamSlot() {
        return teamSlot;
    }

    public void setTeamSlot(long teamSlot) {
        this.teamSlot = teamSlot;
    }
}
