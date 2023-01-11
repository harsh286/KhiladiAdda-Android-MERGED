package com.khiladiadda.network.model.response.hth;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GroupHTH implements Parcelable {
    @SerializedName("_id") @Expose private String id;
    @SerializedName("live_prize_pool") @Expose private double livePrizePool;
    @SerializedName("invested_amount") @Expose private double investedAmount;
    @SerializedName("n_participants") @Expose private long nParticipants;
    @SerializedName("g_points") @Expose private long points;
    @SerializedName("is_substitute") @Expose private Boolean isSubstitute;
    @SerializedName("is_contest") @Expose private Boolean isContest;
    @SerializedName("is_captain") @Expose private Boolean isCaptain;
    @SerializedName("bonus_code") @Expose private Integer bonusCode;
    @SerializedName("is_deleted") @Expose private Boolean isDeleted;
    @SerializedName("is_won") @Expose private Boolean isWon;
    @SerializedName("is_result_declared") @Expose private Boolean isResultDeclared;
    @SerializedName("battle_id") @Expose private String battleId;
    @SerializedName("match_id") @Expose private String matchId;
    @SerializedName("players") @Expose private List<PlayersHTH> players = null;
    @SerializedName("user_id") @Expose private String userId;
    @SerializedName("created_at") @Expose private String createdAt;
    @SerializedName("__v") @Expose private Integer v;

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

    public long getnParticipants() {
        return nParticipants;
    }

    public void setnParticipants(long nParticipants) {
        this.nParticipants = nParticipants;
    }

    public long getgPoints() {
        return points;
    }

    public void setgPoints(long gPoints) {
        this.points = gPoints;
    }

    public Boolean getIsSubstitute() {
        return isSubstitute;
    }

    public void setIsSubstitute(Boolean isSubstitute) {
        this.isSubstitute = isSubstitute;
    }

    public Boolean getIsContest() {
        return isContest;
    }

    public void setIsContest(Boolean isContest) {
        this.isContest = isContest;
    }

    public Boolean getIsCaptain() {
        return isCaptain;
    }

    public void setIsCaptain(Boolean isCaptain) {
        this.isCaptain = isCaptain;
    }

    public Integer getBonusCode() {
        return bonusCode;
    }

    public void setBonusCode(Integer bonusCode) {
        this.bonusCode = bonusCode;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsWon() {
        return isWon;
    }

    public void setIsWon(Boolean isWon) {
        this.isWon = isWon;
    }

    public Boolean getIsResultDeclared() {
        return isResultDeclared;
    }

    public void setIsResultDeclared(Boolean isResultDeclared) {
        this.isResultDeclared = isResultDeclared;
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

    public List<PlayersHTH> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayersHTH> players) {
        this.players = players;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public Boolean getSubstitute() {
        return isSubstitute;
    }

    public void setSubstitute(Boolean substitute) {
        isSubstitute = substitute;
    }

    public Boolean getContest() {
        return isContest;
    }

    public void setContest(Boolean contest) {
        isContest = contest;
    }

    public Boolean getCaptain() {
        return isCaptain;
    }

    public void setCaptain(Boolean captain) {
        isCaptain = captain;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getWon() {
        return isWon;
    }

    public void setWon(Boolean won) {
        isWon = won;
    }

    public Boolean getResultDeclared() {
        return isResultDeclared;
    }

    public void setResultDeclared(Boolean resultDeclared) {
        isResultDeclared = resultDeclared;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeDouble(this.livePrizePool);
        dest.writeDouble(this.investedAmount);
        dest.writeLong(this.nParticipants);
        dest.writeLong(this.points);
        dest.writeValue(this.isSubstitute);
        dest.writeValue(this.isContest);
        dest.writeValue(this.isCaptain);
        dest.writeValue(this.bonusCode);
        dest.writeValue(this.isDeleted);
        dest.writeValue(this.isWon);
        dest.writeValue(this.isResultDeclared);
        dest.writeString(this.battleId);
        dest.writeString(this.matchId);
        dest.writeTypedList(this.players);
        dest.writeString(this.userId);
        dest.writeString(this.createdAt);
        dest.writeValue(this.v);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.livePrizePool = source.readDouble();
        this.investedAmount = source.readDouble();
        this.nParticipants = source.readLong();
        this.points = source.readLong();
        this.isSubstitute = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.isContest = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.isCaptain = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.bonusCode = (Integer) source.readValue(Integer.class.getClassLoader());
        this.isDeleted = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.isWon = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.isResultDeclared = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.battleId = source.readString();
        this.matchId = source.readString();
        this.players = source.createTypedArrayList(PlayersHTH.CREATOR);
        this.userId = source.readString();
        this.createdAt = source.readString();
        this.v = (Integer) source.readValue(Integer.class.getClassLoader());
    }

    public GroupHTH() {
    }

    protected GroupHTH(Parcel in) {
        this.id = in.readString();
        this.livePrizePool = in.readDouble();
        this.investedAmount = in.readDouble();
        this.nParticipants = in.readLong();
        this.points = in.readLong();
        this.isSubstitute = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.isContest = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.isCaptain = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.bonusCode = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isDeleted = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.isWon = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.isResultDeclared = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.battleId = in.readString();
        this.matchId = in.readString();
        this.players = in.createTypedArrayList(PlayersHTH.CREATOR);
        this.userId = in.readString();
        this.createdAt = in.readString();
        this.v = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<GroupHTH> CREATOR = new Parcelable.Creator<GroupHTH>() {
        @Override
        public GroupHTH createFromParcel(Parcel source) {
            return new GroupHTH(source);
        }

        @Override
        public GroupHTH[] newArray(int size) {
            return new GroupHTH[size];
        }
    };
}
