package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyTeamDetails {

    @SerializedName("users") @Expose private List<TeamDetails> users = null;
    @SerializedName("team_code") @Expose private String teamCode;
    @SerializedName("team_name") @Expose private String teamName;
    @SerializedName("team_slot") @Expose private String teamSlot;

    public List<TeamDetails> getUsers() {
        return users;
    }

    public void setUsers(List<TeamDetails> users) {
        this.users = users;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamSlot() {
        return teamSlot;
    }

    public void setTeamSlot(String teamSlot) {
        this.teamSlot = teamSlot;
    }
}