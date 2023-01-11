package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LudoContest implements Parcelable {

    @SerializedName("is_captain_declared") @Expose private boolean isCaptainDeclared;
    @SerializedName("is_opponent_declared") @Expose private boolean isOpponentDeclared;
    @SerializedName("opponent_id") @Expose private String opponentId;
    @SerializedName("is_played") @Expose private boolean isPlayed;
    @SerializedName("is_cancelled") @Expose private boolean isCancelled;
    @SerializedName("is_accepted") @Expose private boolean isAccepted;
    @SerializedName("game_id") @Expose private String gameId;
    @SerializedName("contest_name") @Expose private String contestName;
    @SerializedName("entry_fees") @Expose private double entryFees;
    @SerializedName("start") @Expose private String start;
    @SerializedName("end") @Expose private String end;
    @SerializedName("winning_amount") @Expose private double winningAmount;
    @SerializedName("captain_id") @Expose private String captainId;
    @SerializedName("contest_code") @Expose private String contestCode;
    @SerializedName("created_at") @Expose private String createdAt;
    @SerializedName("_id") @Expose private String id;
    @SerializedName("room_id") @Expose private String roomId;
    @SerializedName("is_result_declared") @Expose private boolean isResultDeclared;
    @SerializedName("admin_status") @Expose private long adminStatus;
    @SerializedName("opponent_error") @Expose private OpponentError opponentError;
    @SerializedName("captain_error") @Expose private CaptainError captainError;
    @SerializedName("room_id_updated_at") @Expose private String room_id_updated_at;
    @SerializedName("opponent_result") @Expose private OpponentResult opponentResult;
    @SerializedName("captain_result") @Expose private CaptainResult captainResult;
    @SerializedName("captain") @Expose private Captain captain;
    @SerializedName("opponent") @Expose private Opponent opponent;
    @SerializedName("mode") @Expose private int mode;
    @SerializedName("contest_status") @Expose private int contestStatus;


    protected LudoContest(Parcel in) {
        isCaptainDeclared = in.readByte() != 0;
        isOpponentDeclared = in.readByte() != 0;
        opponentId = in.readString();
        isPlayed = in.readByte() != 0;
        isCancelled = in.readByte() != 0;
        isAccepted = in.readByte() != 0;
        gameId = in.readString();
        contestName = in.readString();
        entryFees = in.readDouble();
        start = in.readString();
        end = in.readString();
        winningAmount = in.readDouble();
        captainId = in.readString();
        contestCode = in.readString();
        createdAt = in.readString();
        id = in.readString();
        roomId = in.readString();
        isResultDeclared = in.readByte() != 0;
        adminStatus = in.readLong();
        opponentError = in.readParcelable(OpponentError.class.getClassLoader());
        captainError = in.readParcelable(CaptainError.class.getClassLoader());
        room_id_updated_at = in.readString();
        opponentResult = in.readParcelable(OpponentResult.class.getClassLoader());
        captainResult = in.readParcelable(CaptainResult.class.getClassLoader());
        captain = in.readParcelable(Captain.class.getClassLoader());
        opponent = in.readParcelable(Opponent.class.getClassLoader());
        mode = in.readInt();
        contestStatus = in.readInt();
    }

    public static final Creator<LudoContest> CREATOR = new Creator<LudoContest>() {
        @Override public LudoContest createFromParcel(Parcel in) {
            return new LudoContest(in);
        }

        @Override public LudoContest[] newArray(int size) {
            return new LudoContest[size];
        }
    };

    public boolean isCaptainDeclared() {
        return isCaptainDeclared;
    }

    public void setCaptainDeclared(boolean captainDeclared) {
        isCaptainDeclared = captainDeclared;
    }

    public boolean isOpponentDeclared() {
        return isOpponentDeclared;
    }

    public void setOpponentDeclared(boolean opponentDeclared) {
        isOpponentDeclared = opponentDeclared;
    }

    public String getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(String opponentId) {
        this.opponentId = opponentId;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean played) {
        isPlayed = played;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public double getEntryFees() {
        return entryFees;
    }

    public void setEntryFees(double entryFees) {
        this.entryFees = entryFees;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public double getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(double winningAmount) {
        this.winningAmount = winningAmount;
    }

    public String getCaptainId() {
        return captainId;
    }

    public void setCaptainId(String captainId) {
        this.captainId = captainId;
    }

    public String getContestCode() {
        return contestCode;
    }

    public void setContestCode(String contestCode) {
        this.contestCode = contestCode;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public boolean isResultDeclared() {
        return isResultDeclared;
    }

    public void setResultDeclared(boolean resultDeclared) {
        isResultDeclared = resultDeclared;
    }

    public long getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(long adminStatus) {
        this.adminStatus = adminStatus;
    }

    public OpponentError getOpponentError() {
        return opponentError;
    }

    public void setOpponentError(OpponentError opponentError) {
        this.opponentError = opponentError;
    }

    public CaptainError getCaptainError() {
        return captainError;
    }

    public void setCaptainError(CaptainError captainError) {
        this.captainError = captainError;
    }

    public String getRoom_id_updated_at() {
        return room_id_updated_at;
    }

    public void setRoom_id_updated_at(String room_id_updated_at) {
        this.room_id_updated_at = room_id_updated_at;
    }

    public OpponentResult getOpponentResult() {
        return opponentResult;
    }

    public void setOpponentResult(OpponentResult opponentResult) {
        this.opponentResult = opponentResult;
    }

    public CaptainResult getCaptainResult() {
        return captainResult;
    }

    public void setCaptainResult(CaptainResult captainResult) {
        this.captainResult = captainResult;
    }

    public Captain getCaptain() {
        return captain;
    }

    public void setCaptain(Captain captain) {
        this.captain = captain;
    }

    public Opponent getOpponent() {
        return opponent;
    }

    public void setOpponent(Opponent opponent) {
        this.opponent = opponent;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getContestStatus() {
        return contestStatus;
    }

    public void setContestStatus(int contestStatus) {
        this.contestStatus = contestStatus;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isCaptainDeclared ? 1 : 0));
        dest.writeByte((byte) (isOpponentDeclared ? 1 : 0));
        dest.writeString(opponentId);
        dest.writeByte((byte) (isPlayed ? 1 : 0));
        dest.writeByte((byte) (isCancelled ? 1 : 0));
        dest.writeByte((byte) (isAccepted ? 1 : 0));
        dest.writeString(gameId);
        dest.writeString(contestName);
        dest.writeDouble(entryFees);
        dest.writeString(start);
        dest.writeString(end);
        dest.writeDouble(winningAmount);
        dest.writeString(captainId);
        dest.writeString(contestCode);
        dest.writeString(createdAt);
        dest.writeString(id);
        dest.writeString(roomId);
        dest.writeByte((byte) (isResultDeclared ? 1 : 0));
        dest.writeLong(adminStatus);
        dest.writeParcelable(opponentError, flags);
        dest.writeParcelable(captainError, flags);
        dest.writeString(room_id_updated_at);
        dest.writeParcelable(opponentResult, flags);
        dest.writeParcelable(captainResult, flags);
        dest.writeParcelable(captain, flags);
        dest.writeParcelable(opponent, flags);
        dest.writeInt(mode);
        dest.writeInt(contestStatus);
    }
}