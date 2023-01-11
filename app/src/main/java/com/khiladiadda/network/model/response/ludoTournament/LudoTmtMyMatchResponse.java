package com.khiladiadda.network.model.response.ludoTournament;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LudoTmtMyMatchResponse {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("nParticipants")
    @Expose
    private Integer nParticipants;
    @SerializedName("nParticipated")
    @Expose
    private Integer nParticipated;
    @SerializedName("tMode")
    @Expose
    private Integer tMode;
    @SerializedName("ttLevel")
    @Expose
    private Integer ttLevel;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("entryFees")
    @Expose
    private Integer entryFees;
    @SerializedName("prize")
    @Expose
    private Integer prize;
    @SerializedName("startDate")
    @Expose
    private String startDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getnParticipants() {
        return nParticipants;
    }

    public void setnParticipants(Integer nParticipants) {
        this.nParticipants = nParticipants;
    }

    public Integer getnParticipated() {
        return nParticipated;
    }

    public void setnParticipated(Integer nParticipated) {
        this.nParticipated = nParticipated;
    }

    public Integer gettMode() {
        return tMode;
    }

    public void settMode(Integer tMode) {
        this.tMode = tMode;
    }

    public Integer getTtLevel() {
        return ttLevel;
    }

    public void setTtLevel(Integer ttLevel) {
        this.ttLevel = ttLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEntryFees() {
        return entryFees;
    }

    public void setEntryFees(Integer entryFees) {
        this.entryFees = entryFees;
    }

    public Integer getPrize() {
        return prize;
    }

    public void setPrize(Integer prize) {
        this.prize = prize;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
