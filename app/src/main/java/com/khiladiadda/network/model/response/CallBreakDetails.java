package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CallBreakDetails {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("entryFees")
    @Expose
    private Long entryFees;
    @SerializedName("winningAmount")
    @Expose
    private Long winningAmount;
    @SerializedName("liveEnabled")
    @Expose
    private Boolean liveEnabled;
    @SerializedName("participants")
    @Expose
    private Long participants;
    @SerializedName("cardStatus")
    @Expose
    private Long cardStatus;
    @SerializedName("__v")
    @Expose
    private Long v;
    @SerializedName("isMatchPlayable")
    @Expose
    private boolean isMatchPlayable;
    @SerializedName("players")
    @Expose
    private List<CallBreakHistoryPlayerResponse> players;

    public List<CallBreakHistoryPlayerResponse> getPlayers() {
        return players;
    }

    public void setPlayers(List<CallBreakHistoryPlayerResponse> players) {
        this.players = players;
    }

    public boolean isMatchPlayable() {
        return isMatchPlayable;
    }

    public void setMatchPlayable(boolean matchPlayable) {
        isMatchPlayable = matchPlayable;
    }

    @SerializedName("prizePoolBreakthrough")
    @Expose
    public List<PrizePoolBreakthrough> prizePoolBreakup = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getEntryFees() {
        return entryFees;
    }

    public void setEntryFees(Long entryFees) {
        this.entryFees = entryFees;
    }

    public Long getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(Long winningAmount) {
        this.winningAmount = winningAmount;
    }

    public Boolean getLiveEnabled() {
        return liveEnabled;
    }

    public void setLiveEnabled(Boolean liveEnabled) {
        this.liveEnabled = liveEnabled;
    }

    public Long getParticipants() {
        return participants;
    }

    public void setParticipants(Long participants) {
        this.participants = participants;
    }

    public Long getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(Long cardStatus) {
        this.cardStatus = cardStatus;
    }

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

    public List<PrizePoolBreakthrough> getPrizePoolBreakup() {
        return prizePoolBreakup;
    }

    public void setPrizePoolBreakup(List<PrizePoolBreakthrough> prizePoolBreakup) {
        this.prizePoolBreakup = prizePoolBreakup;
    }
}
