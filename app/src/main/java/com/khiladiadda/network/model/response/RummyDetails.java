package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RummyDetails {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("arenaType")
    @Expose
    private String arenaType;
    @SerializedName("numPlayers")
    @Expose
    private Integer numPlayers;
    @SerializedName("maxWin")
    @Expose
    private Integer maxWin;
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
    @SerializedName("cardId")
    @Expose
    private String cardId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArenaType() {
        return arenaType;
    }

    public void setArenaType(String arenaType) {
        this.arenaType = arenaType;
    }

    public Integer getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(Integer numPlayers) {
        this.numPlayers = numPlayers;
    }

    public Integer getMaxWin() {
        return maxWin;
    }

    public void setMaxWin(Integer maxWin) {
        this.maxWin = maxWin;
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

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
