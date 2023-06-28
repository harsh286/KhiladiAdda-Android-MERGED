package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RummyDetails {
    public Integer getNumPlayers() {
        return numPlayers;
    }

    @SerializedName("arenaType")
    @Expose
    private String arenaType;
    @SerializedName("stake")
    @Expose
    private Integer stake;
    @SerializedName("entryFee")
    @Expose
    private Integer entryFee;
    @SerializedName("gameMode")
    @Expose
    private Integer gameMode;
    @SerializedName("online")
    @Expose
    private Integer online;
    @SerializedName("rake")
    @Expose
    private Integer rake;
    @SerializedName("numPlayers")
    @Expose
    private Integer numPlayers;
    @SerializedName("bonus")
    @Expose
    private Integer bonus;
    @SerializedName("players")
    @Expose
    private List<PlayersDetails> mPlayersDetails;

    public void setNumPlayers(Integer numPlayers) {
        this.numPlayers = numPlayers;
    }

    public String getArenaType() {
        return arenaType;
    }

    public void setArenaType(String arenaType) {
        this.arenaType = arenaType;
    }

    public Integer getStake() {
        return stake;
    }

    public void setStake(Integer stake) {
        this.stake = stake;
    }

    public Integer getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(Integer entryFee) {
        this.entryFee = entryFee;
    }

    public Integer getGameMode() {
        return gameMode;
    }

    public void setGameMode(Integer gameMode) {
        this.gameMode = gameMode;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Integer getRake() {
        return rake;
    }

    public void setRake(Integer rake) {
        this.rake = rake;
    }

    public List<PlayersDetails> getmPlayersDetails() {
        return mPlayersDetails;
    }

    public void setmPlayersDetails(List<PlayersDetails> mPlayersDetails) {
        this.mPlayersDetails = mPlayersDetails;
    }
    public Integer getBonus(){
        return bonus;
    }
    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }
}
