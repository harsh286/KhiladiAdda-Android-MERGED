package com.khiladiadda.network.model.response.droid_doresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponseData {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("totalparticipants")
    @Expose
    private int totalparticipants;
    @SerializedName("playedparticipants")
    @Expose
    private int playedparticipants;
    @SerializedName("bonus_code")
    @Expose
    private int bonusCode;
    @SerializedName("tournament_status")
    @Expose
    private int tournamentStatus;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("entry_fees")
    @Expose
    private int entryFees;
    @SerializedName("prize_pool_breakthrough")
    private ArrayList<PrizePool> prizePools;
    @SerializedName("prizemoney")
    @Expose
    private int winPrize;

    @SerializedName("end")
    private String end;
    @SerializedName("n_attempts")
    @Expose
    private int nAttempts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTotalparticipants() {
        return totalparticipants;
    }

    public void setTotalparticipants(int totalparticipants) {
        this.totalparticipants = totalparticipants;
    }

    public int getPlayedparticipants() {
        return playedparticipants;
    }

    public void setPlayedparticipants(int playedparticipants) {
        this.playedparticipants = playedparticipants;
    }

    public int getBonusCode() {
        return bonusCode;
    }

    public void setBonusCode(int bonusCode) {
        this.bonusCode = bonusCode;
    }

    public int getTournamentStatus() {
        return tournamentStatus;
    }

    public void setTournamentStatus(int tournamentStatus) {
        this.tournamentStatus = tournamentStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEntryFees() {
        return entryFees;
    }

    public void setEntryFees(int entryFees) {
        this.entryFees = entryFees;
    }

    public ArrayList<PrizePool> getPrizePools() {
        return prizePools;
    }

    public void setPrizePools(ArrayList<PrizePool> prizePools) {
        this.prizePools = prizePools;
    }

    public int getWinPrize() {
        return winPrize;
    }

    public void setWinPrize(int winPrize) {
        this.winPrize = winPrize;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getnAttempts() {
        return nAttempts;
    }

    public void setnAttempts(int nAttempts) {
        this.nAttempts = nAttempts;
    }
}
