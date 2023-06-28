package com.khiladiadda.network.model.response;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class LeagueListDetails implements Parcelable {
    @SerializedName("title") @Expose public String title;
    @SerializedName("game_category_id") @Expose public String gameCategoryId;
    @SerializedName("match_id") @Expose public String matchId;
    @SerializedName("played_participants") @Expose public long playedParticipants;
    @SerializedName("rules") @Expose public String rules;
    @SerializedName("plus_point") @Expose public long plusPoint;
    @SerializedName("minus_point") @Expose public long minusPoint;
    @SerializedName("_id") @Expose public String id;
    @SerializedName("game_id") @Expose public String gameId;
    @SerializedName("entry_fees") @Expose public double entryFees;
    @SerializedName("total_participants") @Expose public long totalParticipants;
    @SerializedName("bonus_code") @Expose public long bonusCode;
    @SerializedName("kill_point") @Expose public double killPoint;
    @SerializedName("start") @Expose public String start;
    @SerializedName("prize_pool_breakup") @Expose public List<PubgPrizePool> prizePoolBreakup = null;
    @SerializedName("prize_money") @Expose public double prizeMoney;
    @SerializedName("end") @Expose public String end;
    @SerializedName("created_at") @Expose public String createdAt;
    @SerializedName("updated_at") @Expose public String updatedAt;
    @SerializedName("room_id") @Expose public String roomId;
    @SerializedName("room_password") @Expose public String roomPassword;
    @SerializedName("map") @Expose public String map;
    @SerializedName("is_cancelled") @Expose public boolean cancelled;
    @SerializedName("length") @Expose private long length;
    @SerializedName("level") @Expose private long level;
    @SerializedName("maxloss") @Expose private long maxloss;

    public String getGameCategoryId() {
        return gameCategoryId;
    }

    public void setGameCategoryId(String gameCategoryId) {
        this.gameCategoryId = gameCategoryId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public long getPlayedParticipants() {
        return playedParticipants;
    }

    public void setPlayedParticipants(long playedParticipants) {
        this.playedParticipants = playedParticipants;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public long getPlusPoint() {
        return plusPoint;
    }

    public void setPlusPoint(long plusPoint) {
        this.plusPoint = plusPoint;
    }

    public long getMinusPoint() {
        return minusPoint;
    }

    public void setMinusPoint(long minusPoint) {
        this.minusPoint = minusPoint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public double getEntryFees() {
        return entryFees;
    }

    public void setEntryFees(double entryFees) {
        this.entryFees = entryFees;
    }

    public long getTotalParticipants() {
        return totalParticipants;
    }

    public void setTotalParticipants(long totalParticipants) {
        this.totalParticipants = totalParticipants;
    }

    public long getBonusCode() {
        return bonusCode;
    }

    public void setBonusCode(long bonusCode) {
        this.bonusCode = bonusCode;
    }

    public double getKillPoint() {
        return killPoint;
    }

    public void setKillPoint(double killPoint) {
        this.killPoint = killPoint;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public List<PubgPrizePool> getPrizePoolBreakup() {
        return prizePoolBreakup;
    }

    public void setPrizePoolBreakup(List<PubgPrizePool> prizePoolBreakup) {
        this.prizePoolBreakup = prizePoolBreakup;
    }

    public double getPrizeMoney() {
        return prizeMoney;
    }

    public void setPrizeMoney(double prizeMoney) {
        this.prizeMoney = prizeMoney;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomPassword() {
        return roomPassword;
    }

    public void setRoomPassword(String roomPassword) {
        this.roomPassword = roomPassword;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public long getMaxloss() {
        return maxloss;
    }

    public void setMaxloss(long maxloss) {
        this.maxloss = maxloss;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.gameCategoryId);
        dest.writeString(this.matchId);
        dest.writeLong(this.playedParticipants);
        dest.writeString(this.rules);
        dest.writeLong(this.plusPoint);
        dest.writeLong(this.minusPoint);
        dest.writeString(this.id);
        dest.writeString(this.gameId);
        dest.writeDouble(this.entryFees);
        dest.writeLong(this.totalParticipants);
        dest.writeLong(this.bonusCode);
        dest.writeDouble(this.killPoint);
        dest.writeString(this.start);
        dest.writeTypedList(this.prizePoolBreakup);
        dest.writeDouble(this.prizeMoney);
        dest.writeString(this.end);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.roomId);
        dest.writeString(this.roomPassword);
        dest.writeString(this.map);
        dest.writeByte(this.cancelled ? (byte) 1 : (byte) 0);
        dest.writeLong(this.length);
        dest.writeLong(this.level);
        dest.writeLong(this.maxloss);
    }

    public LeagueListDetails() {
    }

    protected LeagueListDetails(Parcel in) {
        this.title = in.readString();
        this.gameCategoryId = in.readString();
        this.matchId = in.readString();
        this.playedParticipants = in.readLong();
        this.rules = in.readString();
        this.plusPoint = in.readLong();
        this.minusPoint = in.readLong();
        this.id = in.readString();
        this.gameId = in.readString();
        this.entryFees = in.readDouble();
        this.totalParticipants = in.readLong();
        this.bonusCode = in.readLong();
        this.killPoint = in.readDouble();
        this.start = in.readString();
        this.prizePoolBreakup = in.createTypedArrayList(PubgPrizePool.CREATOR);
        this.prizeMoney = in.readDouble();
        this.end = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.roomId = in.readString();
        this.roomPassword = in.readString();
        this.map = in.readString();
        this.cancelled = in.readByte() != 0;
        this.length = in.readLong();
        this.level = in.readLong();
        this.maxloss = in.readLong();
    }

    public static final Creator<LeagueListDetails> CREATOR = new Creator<LeagueListDetails>() {
        @Override public LeagueListDetails createFromParcel(Parcel source) {
            return new LeagueListDetails(source);
        }

        @Override public LeagueListDetails[] newArray(int size) {
            return new LeagueListDetails[size];
        }
    };

}