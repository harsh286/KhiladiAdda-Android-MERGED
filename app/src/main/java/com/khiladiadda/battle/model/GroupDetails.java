package com.khiladiadda.battle.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GroupDetails implements Parcelable {

    @SerializedName("_id") @Expose private String id;
    @SerializedName("live_prize_pool") @Expose private double livePrizePool;
    @SerializedName("invested_amount") @Expose private double investedAmount;
    @SerializedName("n_participants") @Expose private long nParticipants;
    @SerializedName("g_points") @Expose private long points;
    @SerializedName("is_won") @Expose private boolean isWon;
    @SerializedName("battle_id") @Expose private String battleId;
    @SerializedName("match_id") @Expose private String matchId;
    @SerializedName("players") @Expose private ArrayList<Player> players = null;
    @SerializedName("created_at") @Expose private String createdAt;
    @SerializedName("is_played") @Expose private boolean isPlayed;
    @SerializedName("played") @Expose private PlayedUsersList played = null;
    @SerializedName("winner") @Expose private boolean isWinner;
    @SerializedName("is_substitute") @Expose private boolean isSubstitute;
    @SerializedName("matchId")@Expose private String LivematchId;
    @SerializedName("user_id")@Expose private String captainId;
    @SerializedName("is_captain") @Expose private boolean isCaptain;
   // @SerializedName("is_result_declared")@Expose private boolean is_result_declared ;

    protected GroupDetails(Parcel in) {
        id = in.readString();
        livePrizePool = in.readDouble();
        investedAmount = in.readDouble();
        nParticipants = in.readLong();
        points = in.readLong();
        isWon = in.readByte() != 0;
        battleId = in.readString();
        matchId = in.readString();
        players = in.createTypedArrayList(Player.CREATOR);
        createdAt = in.readString();
        isPlayed = in.readByte() != 0;
        isWinner = in.readByte() != 0;
        isSubstitute = in.readByte() != 0;
        LivematchId = in.readString();
        captainId = in.readString();
        isCaptain = in.readByte() != 0;
    }

    public static final Creator<GroupDetails> CREATOR = new Creator<GroupDetails>() {
        @Override
        public GroupDetails createFromParcel(Parcel in) {
            return new GroupDetails(in);
        }

        @Override
        public GroupDetails[] newArray(int size) {
            return new GroupDetails[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLivePrizePool() {
        return livePrizePool;
    }

    public void setLivePrizePool(double livePrizePool) {
        this.livePrizePool = livePrizePool;
    }

    public double getInvestedAmount() {
        return investedAmount;
    }

    public void setInvestedAmount(double investedAmount) {
        this.investedAmount = investedAmount;
    }

    public long getNParticipants() {
        return nParticipants;
    }

    public void setNParticipants(long nParticipants) {
        this.nParticipants = nParticipants;
    }

    public boolean isIsWon() {
        return isWon;
    }

    public void setIsWon(boolean isWon) {
        this.isWon = isWon;
    }

    public String getBattleId() {
        return battleId;
    }

    public void setBattleId(String battleId) {
        this.battleId = battleId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean isPlayed) {
        this.isPlayed = isPlayed;
    }

    public PlayedUsersList getPlayed() {
        return played;
    }

    public void setPlayed(PlayedUsersList played) {
        this.played = played;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    public boolean isSubstitute() {
        return isSubstitute;
    }

    public void setSubstitute(boolean substitute) {
        isSubstitute = substitute;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeDouble(livePrizePool);
        dest.writeDouble(investedAmount);
        dest.writeLong(nParticipants);
        dest.writeLong(points);
        dest.writeByte((byte) (isWon ? 1 : 0));
        dest.writeString(battleId);
        dest.writeString(matchId);
        dest.writeTypedList(players);
        dest.writeString(createdAt);
        dest.writeByte((byte) (isPlayed ? 1 : 0));
        dest.writeByte((byte) (isWinner ? 1 : 0));
        dest.writeByte((byte) (isSubstitute ? 1 : 0));
        dest.writeString(LivematchId);
        dest.writeString(captainId);
        dest.writeByte((byte) (isCaptain ? 1 : 0));
    }

    public String getLivematchId() {
        return LivematchId;
    }

    public void setLivematchId(String livematchId) {
        LivematchId = livematchId;
    }

    public String getCaptainId() {
        return captainId;
    }

    public void setCaptainId(String captainId) {
        this.captainId = captainId;
    }

    public boolean isCaptain() {
        return isCaptain;
    }

    public void setCaptain(boolean captain) {
        isCaptain = captain;
    }
}