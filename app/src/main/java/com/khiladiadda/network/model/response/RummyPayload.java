package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RummyPayload {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("numPlayers")
    @Expose
    private Long numPlayers;
    @SerializedName("maxWin")
    @Expose
    private Long maxWin;
    @SerializedName("stake")
    @Expose
    private Long stake;
    @SerializedName("entryFee")
    @Expose
    private Long entryFee;
    @SerializedName("gameMode")
    @Expose
    private Long gameMode;
    @SerializedName("online")
    @Expose
    private Long online;
    @SerializedName("rake")
    @Expose
    private Long rake;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(Long numPlayers) {
        this.numPlayers = numPlayers;
    }

    public Long getMaxWin() {
        return maxWin;
    }

    public void setMaxWin(Long maxWin) {
        this.maxWin = maxWin;
    }

    public Long getStake() {
        return stake;
    }

    public void setStake(Long stake) {
        this.stake = stake;
    }

    public Long getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(Long entryFee) {
        this.entryFee = entryFee;
    }

    public Long getGameMode() {
        return gameMode;
    }

    public void setGameMode(Long gameMode) {
        this.gameMode = gameMode;
    }

    public Long getOnline() {
        return online;
    }

    public void setOnline(Long online) {
        this.online = online;
    }

    public Long getRake() {
        return rake;
    }

    public void setRake(Long rake) {
        this.rake = rake;
    }

}
