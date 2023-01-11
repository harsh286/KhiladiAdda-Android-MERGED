package com.khiladiadda.network.model.response.hth;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BattlesDeatilsHTH implements Parcelable {
    @SerializedName("_id") @Expose private String id;
    @SerializedName("audio") @Expose private String audio;
    @SerializedName("live_prize_pool") @Expose private double livePrizePool;
    @SerializedName("n_participants") @Expose private long nParticipants;
    @SerializedName("is_result_declared") @Expose private Boolean isResultDeclared;
    @SerializedName("invested_amount") @Expose private double investedAmount;
    @SerializedName("is_active") @Expose private Boolean isActive;
    @SerializedName("is_deleted") @Expose private Boolean isDeleted;
    @SerializedName("n_groups_joined") @Expose private long nGroupsJoined;
    @SerializedName("n_groups_played") @Expose private List<String> nGroupsPlayed = null;
    @SerializedName("bonus_code") @Expose private long bonusCode;
    @SerializedName("is_free") @Expose private Boolean isFree;
    @SerializedName("is_reverse") @Expose private Boolean isReverse;
    @SerializedName("is_contest") @Expose private Boolean isContest;
    @SerializedName("total_participants") @Expose private long totalParticipants;
    @SerializedName("prize") @Expose private double prize;
    @SerializedName("category_id") @Expose private String categoryId;
    @SerializedName("match_id") @Expose private String matchId;
    @SerializedName("captain_id") @Expose private String captainId;
    @SerializedName("opponent_id") @Expose private String opponentId;
    @SerializedName("contestId") @Expose private String contestId;
    @SerializedName("n_groups") @Expose private long nGroups;
    @SerializedName("created_at") @Expose private String createdAt;
    @SerializedName("title") @Expose private String title;
    @SerializedName("desc") @Expose private String desc;
    @SerializedName("img") @Expose private String img;
    @SerializedName("captain_team") @Expose private List<CaptainTeamHTH> captainTeam = null;
    @SerializedName("opponent_team") @Expose private List<CaptainTeamHTH> opponentTeam = null;
    @SerializedName("__v") @Expose private Integer v;
    @SerializedName("match") @Expose private MatchHTH match;
    @SerializedName("captain") @Expose private CaptainHTH captain;
    @SerializedName("groups") @Expose private List<GroupHTH> groups = null;
    @SerializedName("opponent") @Expose private CaptainHTH opponent;
    @SerializedName("battle_status") @Expose private Integer battle_status;
    @SerializedName("is_cancelled")@Expose private boolean is_cancelled;
    @SerializedName("opponent_points")@Expose private double opponent_points;
    @SerializedName("captain_points")@Expose private double captain_points;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public double getLivePrizePool() {
        return livePrizePool;
    }

    public void setLivePrizePool(double livePrizePool) {
        this.livePrizePool = livePrizePool;
    }

    public long getnParticipants() {
        return nParticipants;
    }

    public void setnParticipants(long nParticipants) {
        this.nParticipants = nParticipants;
    }

    public Boolean getIsResultDeclared() {
        return isResultDeclared;
    }

    public void setIsResultDeclared(Boolean isResultDeclared) {
        this.isResultDeclared = isResultDeclared;
    }

    public double getInvestedAmount() {
        return investedAmount;
    }

    public void setInvestedAmount(double investedAmount) {
        this.investedAmount = investedAmount;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public long getnGroupsJoined() {
        return nGroupsJoined;
    }

    public void setnGroupsJoined(long nGroupsJoined) {
        this.nGroupsJoined = nGroupsJoined;
    }

    public List<String> getnGroupsPlayed() {
        return nGroupsPlayed;
    }

    public void setnGroupsPlayed(List<String> nGroupsPlayed) {
        this.nGroupsPlayed = nGroupsPlayed;
    }

    public long getBonusCode() {
        return bonusCode;
    }

    public void setBonusCode(long bonusCode) {
        this.bonusCode = bonusCode;
    }

    public Boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }

    public Boolean getIsReverse() {
        return isReverse;
    }

    public void setIsReverse(Boolean isReverse) {
        this.isReverse = isReverse;
    }

    public Boolean getIsContest() {
        return isContest;
    }

    public void setIsContest(Boolean isContest) {
        this.isContest = isContest;
    }

    public long getTotalParticipants() {
        return totalParticipants;
    }

    public void setTotalParticipants(long totalParticipants) {
        this.totalParticipants = totalParticipants;
    }

    public double getPrize() {
        return prize;
    }

    public void setPrize(Integer prize) {
        this.prize = prize;
    }

    public Object getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getCaptainId() {
        return captainId;
    }

    public void setCaptainId(String captainId) {
        this.captainId = captainId;
    }

    public String getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(String opponentId) {
        this.opponentId = opponentId;
    }

    public String getContestId() {
        return contestId;
    }

    public void setContestId(String contestId) {
        this.contestId = contestId;
    }

    public long getnGroups() {
        return nGroups;
    }

    public void setnGroups(long nGroups) {
        this.nGroups = nGroups;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<CaptainTeamHTH> getCaptainTeam() {
        return captainTeam;
    }

    public void setCaptainTeam(List<CaptainTeamHTH> captainTeam) {
        this.captainTeam = captainTeam;
    }

    public List<CaptainTeamHTH> getOpponentTeam() {
        return opponentTeam;
    }

    public void setOpponentTeam(List<CaptainTeamHTH> opponentTeam) {
        this.opponentTeam = opponentTeam;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public MatchHTH getMatch() {
        return match;
    }

    public void setMatch(MatchHTH match) {
        this.match = match;
    }

    public CaptainHTH getCaptain() {
        return captain;
    }

    public void setCaptain(CaptainHTH captain) {
        this.captain = captain;
    }

    public List<GroupHTH> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupHTH> groups) {
        this.groups = groups;
    }

    public CaptainHTH getOpponent() {
        return opponent;
    }

    public void setOpponent(CaptainHTH opponent) {
        this.opponent = opponent;
    }

    public double getOpponent_points() {
        return opponent_points;
    }

    public void setOpponent_points(double opponent_points) {
        this.opponent_points = opponent_points;
    }

    public double getCaptain_points() {
        return captain_points;
    }

    public void setCaptain_points(double captain_points) {
        this.captain_points = captain_points;
    }

    public boolean isIs_cancelled() {
        return is_cancelled;
    }

    public void setIs_cancelled(boolean is_cancelled) {
        this.is_cancelled = is_cancelled;
    }


    public Boolean getResultDeclared() {
        return isResultDeclared;
    }

    public void setResultDeclared(Boolean resultDeclared) {
        isResultDeclared = resultDeclared;
    }

    public Integer getBattle_status() {
        return battle_status;
    }

    public void setBattle_status(Integer battle_status) {
        this.battle_status = battle_status;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.audio);
        dest.writeDouble(this.livePrizePool);
        dest.writeLong(this.nParticipants);
        dest.writeValue(this.isResultDeclared);
        dest.writeDouble(this.investedAmount);
        dest.writeValue(this.isActive);
        dest.writeValue(this.isDeleted);
        dest.writeLong(this.nGroupsJoined);
        dest.writeStringList(this.nGroupsPlayed);
        dest.writeLong(this.bonusCode);
        dest.writeValue(this.isFree);
        dest.writeValue(this.isReverse);
        dest.writeValue(this.isContest);
        dest.writeLong(this.totalParticipants);
        dest.writeDouble(this.prize);
        dest.writeString(this.categoryId);
        dest.writeString(this.matchId);
        dest.writeString(this.captainId);
        dest.writeString(this.opponentId);
        dest.writeString(this.contestId);
        dest.writeLong(this.nGroups);
        dest.writeString(this.createdAt);
        dest.writeString(this.title);
        dest.writeString(this.desc);
        dest.writeString(this.img);
        dest.writeTypedList(this.captainTeam);
        dest.writeTypedList(this.opponentTeam);
        dest.writeValue(this.v);
        dest.writeParcelable(this.match, flags);
        dest.writeParcelable(this.captain, flags);
        dest.writeTypedList(this.groups);
        dest.writeParcelable(this.opponent, flags);
        dest.writeValue(this.battle_status);
        dest.writeByte(this.is_cancelled ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.opponent_points);
        dest.writeDouble(this.captain_points);

    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.audio = source.readString();
        this.livePrizePool = source.readDouble();
        this.nParticipants = source.readLong();
        this.isResultDeclared = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.investedAmount = source.readDouble();
        this.isActive = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.isDeleted = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.nGroupsJoined = source.readLong();
        this.nGroupsPlayed = source.createStringArrayList();
        this.bonusCode = source.readLong();
        this.isFree = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.isReverse = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.isContest = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.totalParticipants = source.readLong();
        this.prize = source.readDouble();
        this.categoryId = source.readString();
        this.matchId = source.readString();
        this.captainId = source.readString();
        this.opponentId = source.readString();
        this.contestId = source.readString();
        this.nGroups = source.readLong();
        this.createdAt = source.readString();
        this.title = source.readString();
        this.desc = source.readString();
        this.img = source.readString();
        this.captainTeam = source.createTypedArrayList(CaptainTeamHTH.CREATOR);
        this.opponentTeam = source.createTypedArrayList(CaptainTeamHTH.CREATOR);
        this.v = (Integer) source.readValue(Integer.class.getClassLoader());
        this.match = source.readParcelable(MatchHTH.class.getClassLoader());
        this.captain = source.readParcelable(CaptainHTH.class.getClassLoader());
        this.groups = source.createTypedArrayList(GroupHTH.CREATOR);
        this.opponent = source.readParcelable(CaptainHTH.class.getClassLoader());
        this.battle_status = (Integer) source.readValue(Integer.class.getClassLoader());
        this.is_cancelled = source.readByte() != 0;
        this.opponent_points = source.readDouble();
        this.captain_points = source.readDouble();

    }

    public BattlesDeatilsHTH() {
    }

    protected BattlesDeatilsHTH(Parcel in) {
        this.id = in.readString();
        this.audio = in.readString();
        this.livePrizePool = in.readDouble();
        this.nParticipants = in.readLong();
        this.isResultDeclared = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.investedAmount = in.readDouble();
        this.isActive = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.isDeleted = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.nGroupsJoined = in.readLong();
        this.nGroupsPlayed = in.createStringArrayList();
        this.bonusCode = in.readLong();
        this.isFree = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.isReverse = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.isContest = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.totalParticipants = in.readLong();
        this.prize = in.readDouble();
        this.categoryId = in.readString();
        this.matchId = in.readString();
        this.captainId = in.readString();
        this.opponentId = in.readString();
        this.contestId = in.readString();
        this.nGroups = in.readLong();
        this.createdAt = in.readString();
        this.title = in.readString();
        this.desc = in.readString();
        this.img = in.readString();
        this.captainTeam = in.createTypedArrayList(CaptainTeamHTH.CREATOR);
        this.opponentTeam = in.createTypedArrayList(CaptainTeamHTH.CREATOR);
        this.v = (Integer) in.readValue(Integer.class.getClassLoader());
        this.match = in.readParcelable(MatchHTH.class.getClassLoader());
        this.captain = in.readParcelable(CaptainHTH.class.getClassLoader());
        this.groups = in.createTypedArrayList(GroupHTH.CREATOR);
        this.opponent = in.readParcelable(CaptainHTH.class.getClassLoader());
        this.battle_status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.is_cancelled = in.readByte() != 0;
        this.opponent_points = in.readDouble();
        this.captain_points = in.readDouble();

    }

    public static final Parcelable.Creator<BattlesDeatilsHTH> CREATOR = new Parcelable.Creator<BattlesDeatilsHTH>() {
        @Override
        public BattlesDeatilsHTH createFromParcel(Parcel source) {
            return new BattlesDeatilsHTH(source);
        }

        @Override
        public BattlesDeatilsHTH[] newArray(int size) {
            return new BattlesDeatilsHTH[size];
        }
    };
}
