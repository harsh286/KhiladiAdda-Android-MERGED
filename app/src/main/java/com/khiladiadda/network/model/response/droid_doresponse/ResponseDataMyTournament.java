package com.khiladiadda.network.model.response.droid_doresponse;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseDataMyTournament {
    @SerializedName("n_attempts")
    private int n_attempts;
    @SerializedName("winning_amount")
    public Integer winning_amount;
    @SerializedName("entry_fees")
    private Integer entryFees;
    @SerializedName("tournament_id")
    public String tournament_id;
    @SerializedName("tournament_status")
    public int tournament_status;
    @SerializedName("tournaments_name")
    public String tournaments_name;
    @SerializedName("tournaments_id")
    private String tournaments_id;
    @SerializedName("tournaments_image")
    public String tournaments_image;
    @SerializedName("end")
    public String endIn;
    @SerializedName("totalparticipants")
    public Integer totalparticipants;
    @SerializedName("playedparticipants")
    public Integer playedparticipants;

    @SerializedName("prizemoney")
    private Integer winPrize;

    @SerializedName("is_won")
    public boolean is_won;

    @SerializedName("prize_pool_breakthrough")
    private ArrayList<PrizePool> prizePools;

    public Integer getWinPrize() {
        return winPrize;
    }

    public boolean isIs_won() {
        return is_won;
    }

    public void setIs_won(boolean is_won) {
        this.is_won = is_won;
    }

    public void setWinPrize(Integer winPrize) {
        this.winPrize = winPrize;
    }

    public int getN_attempts() {
        return n_attempts;
    }

    public void setN_attempts(int n_attempts) {
        this.n_attempts = n_attempts;
    }

    public Integer getWinning_amount() {
        return winning_amount;
    }

    public void setWinning_amount(Integer winning_amount) {
        this.winning_amount = winning_amount;
    }

    public Integer getEntryFees() {
        return entryFees;
    }

    public void setEntryFees(Integer entryFees) {
        this.entryFees = entryFees;
    }

    public String getTournament_id() {
        return tournament_id;
    }

    public void setTournament_id(String tournament_id) {
        this.tournament_id = tournament_id;
    }

    public int getTournament_status() {
        return tournament_status;
    }

    public void setTournament_status(int tournament_status) {
        this.tournament_status = tournament_status;
    }

    public String getTournaments_name() {
        return tournaments_name;
    }

    public void setTournaments_name(String tournaments_name) {
        this.tournaments_name = tournaments_name;
    }

    public String getTournaments_id() {
        return tournaments_id;
    }

    public void setTournaments_id(String tournaments_id) {
        this.tournaments_id = tournaments_id;
    }

    public String getTournaments_image() {
        return tournaments_image;
    }

    public void setTournaments_image(String tournaments_image) {
        this.tournaments_image = tournaments_image;
    }

    public String getEndIn() {
        return endIn;
    }

    public void setEndIn(String endIn) {
        this.endIn = endIn;
    }

    public Integer getTotalparticipants() {
        return totalparticipants;
    }

    public void setTotalparticipants(Integer totalparticipants) {
        this.totalparticipants = totalparticipants;
    }

    public Integer getPlayedparticipants() {
        return playedparticipants;
    }

    public void setPlayedparticipants(Integer playedparticipants) {
        this.playedparticipants = playedparticipants;
    }

    public ArrayList<PrizePool> getPrizePools() {
        return prizePools;
    }

    public void setPrizePools(ArrayList<PrizePool> prizePools) {
        this.prizePools = prizePools;
    }
}
