package com.khiladiadda.network.model.response.droid_doresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GameHistoryDroido {
    @SerializedName("n_attempts")
    @Expose
    private Integer nAttempts;
    @SerializedName("tournament_status")
    @Expose
    private Integer tournamentStatus;
    @SerializedName("winning_amount")
    @Expose
    private Integer winningAmount;
    @SerializedName("entry_fees")
    @Expose
    private Integer entryFees;
    @SerializedName("tournament_id")
    @Expose
    private String tournamentId;
    @SerializedName("tournaments_name")
    @Expose
    private String tournamentsName;
    @SerializedName("tournaments_id")
    @Expose
    private String tournamentsId;
    @SerializedName("tournaments_image")
    @Expose
    private String img;

    public Integer getnAttempts() {
        return nAttempts;
    }

    public void setnAttempts(Integer nAttempts) {
        this.nAttempts = nAttempts;
    }

    public Integer getTournamentStatus() {
        return tournamentStatus;
    }

    public void setTournamentStatus(Integer tournamentStatus) {
        this.tournamentStatus = tournamentStatus;
    }

    public Integer getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(Integer winningAmount) {
        this.winningAmount = winningAmount;
    }

    public Integer getEntryFees() {
        return entryFees;
    }

    public void setEntryFees(Integer entryFees) {
        this.entryFees = entryFees;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getTournamentsName() {
        return tournamentsName;
    }

    public void setTournamentsName(String tournamentsName) {
        this.tournamentsName = tournamentsName;
    }

    public String getTournamentsId() {
        return tournamentsId;
    }

    public void setTournamentsId(String tournamentsId) {
        this.tournamentsId = tournamentsId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}