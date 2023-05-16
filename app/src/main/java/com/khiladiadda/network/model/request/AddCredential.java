package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCredential {
    @SerializedName("game_username")
    @Expose
    public String userName;
    @SerializedName("game_character_id")
    @Expose
    public String characterId;
    @SerializedName("team_code")
    @Expose
    public String teamCode;
    @SerializedName("team_name")
    @Expose
    public String teamName;
    @SerializedName("leagueLevels")
    @Expose
    public String leagueLevels;
    @SerializedName("mapDownloaded")
    @Expose
    public boolean mapDownloaded;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
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

    public String getLeagueLevels() {
        return leagueLevels;
    }

    public void setLeagueLevels(String leagueLevels) {
        this.leagueLevels = leagueLevels;
    }

    public boolean getMapDownloaded() {
        return mapDownloaded;
    }

    public void setMapDownloaded(boolean mapDownloaded) {
        this.mapDownloaded = mapDownloaded;
    }

    public AddCredential(String userName, String characterId, String teamCode, String teamName, String gameLevel, boolean mapDownloaded) {
        this.userName = userName;
        this.characterId = characterId;
        this.teamCode = teamCode;
        this.teamName = teamName;
        this.leagueLevels = gameLevel;
        this.mapDownloaded = mapDownloaded;
    }
}
