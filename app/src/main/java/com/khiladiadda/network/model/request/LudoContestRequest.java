package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LudoContestRequest {

    @SerializedName("end") @Expose private String end;
    @SerializedName("entry_fees") private long entryFees;
    @SerializedName("game_id") private String gameId;
    @SerializedName("captain_ludo_id") private String ludoId;
    @SerializedName("contest_type") private long contestType;
    @SerializedName("start") @Expose private String start;
    @SerializedName("mode") @Expose private int mode;

    public LudoContestRequest(String end, long entryFees, String gameId, String ludoId, long contestType, String start, int mode) {
        this.end = end;
        this.entryFees = entryFees;
        this.gameId = gameId;
        this.ludoId = ludoId;
        this.contestType = contestType;
        this.start = start;
        this.mode = mode;
    }

    public LudoContestRequest(long entryFees) {
        this.entryFees = entryFees;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public long getEntryFees() {
        return entryFees;
    }

    public void setEntryFees(long entryFees) {
        this.entryFees = entryFees;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getLudoId() {
        return ludoId;
    }

    public void setLudoId(String ludoId) {
        this.ludoId = ludoId;
    }

    public long getContestType() {
        return contestType;
    }

    public void setContestType(long contestType) {
        this.contestType = contestType;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}