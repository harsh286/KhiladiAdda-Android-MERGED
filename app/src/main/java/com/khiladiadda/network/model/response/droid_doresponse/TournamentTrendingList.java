package com.khiladiadda.network.model.response.droid_doresponse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class TournamentTrendingList implements Serializable {
    @SerializedName("_id")
    private String id;
    @SerializedName("image")
    public String image;
    @SerializedName("logo")
    public String logo;
    @SerializedName("totalparticipants")
    public int totalParticipants;
    @SerializedName("startplayedparticipants")
    public int startPlayedParticipants;
    @SerializedName("playedparticipants")
    public int playedparticipants;
    @SerializedName("endplayedparticipants")
    public int endplayedparticipants;
    @SerializedName("tournament_status")
    public int tournamentStatus;
    @SerializedName("name")
    public String name;
    @SerializedName("game_id")
    public String gameId;
    @SerializedName("entry_fees")
    private Integer entryFees;
    @SerializedName("prizemoney")
    private Integer winPrize;
    @SerializedName("n_attempts")
    private Integer nAttempts;

    @SerializedName("end")
    private String end;

    @SerializedName("prize_pool_breakthrough")
    private ArrayList<PrizePool> prizePools;

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public ArrayList<PrizePool> getPrizePools() {
        return prizePools;
    }

    public void setPrizePools(ArrayList<PrizePool> prizePools) {
        this.prizePools = prizePools;
    }

    public TournamentTrendingList(ArrayList<PrizePool> prizePools) {
        this.prizePools = prizePools;
    }


    public TournamentTrendingList(String id, String image, String logo, int totalParticipants, int startPlayedParticipants, int playedparticipants, int endplayedparticipants, int tournamentStatus, String name, String gameId, Integer entryFees, Integer prizeMoney, Integer nAttempts) {
        this.id = id;
        this.image = image;
        this.logo = logo;
        this.totalParticipants = totalParticipants;
        this.startPlayedParticipants = startPlayedParticipants;
        this.playedparticipants = playedparticipants;
        this.endplayedparticipants = endplayedparticipants;
        this.tournamentStatus = tournamentStatus;
        this.name = name;
        this.gameId = gameId;
        this.entryFees = entryFees;
        this.winPrize = prizeMoney;
        this.nAttempts = prizeMoney;
    }


    public Integer getnAttempts() {
        return nAttempts;
    }

    public void setnAttempts(Integer nAttempts) {
        this.nAttempts = nAttempts;
    }

    public Integer getWinPrize() {
        return winPrize;
    }

    public void setWinPrize(Integer winPrize) {
        this.winPrize = winPrize;
    }

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getTotalParticipants() {
        return totalParticipants;
    }

    public void setTotalParticipants(int totalParticipants) {
        this.totalParticipants = totalParticipants;
    }

    public int getStartPlayedParticipants() {
        return startPlayedParticipants;
    }

    public void setStartPlayedParticipants(int startPlayedParticipants) {
        this.startPlayedParticipants = startPlayedParticipants;
    }

    public int getPlayedparticipants() {
        return playedparticipants;
    }

    public void setPlayedparticipants(int playedparticipants) {
        this.playedparticipants = playedparticipants;
    }

    public int getEndplayedparticipants() {
        return endplayedparticipants;
    }

    public void setEndplayedparticipants(int endplayedparticipants) {
        this.endplayedparticipants = endplayedparticipants;
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

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Integer getEntryFees() {
        return entryFees;
    }

    public void setEntryFees(Integer entryFees) {
        this.entryFees = entryFees;
    }
}
