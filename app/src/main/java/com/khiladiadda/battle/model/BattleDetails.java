package com.khiladiadda.battle.model;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BattleDetails implements Parcelable {

    public BattleDetails() {
    }

    @SerializedName("_id") @Expose private String id;
    @SerializedName("live_prize_pool") @Expose private double livePrizePool;
    @SerializedName("n_participants") @Expose private long nParticipants;
    @SerializedName("is_result_declared") @Expose private boolean isResultDeclared;
    @SerializedName("invested_amount") @Expose private double investedAmount;
    @SerializedName("is_active") @Expose private boolean isActive;
    @SerializedName("is_deleted") @Expose private boolean isDeleted;
    @SerializedName("match_id") @Expose private String matchId;
    @SerializedName("title") @Expose private String title;
    @SerializedName("desc") @Expose private String desc;
    @SerializedName("n_groups") @Expose private long nGroups;
    @SerializedName("img") @Expose private String img;
    @SerializedName("created_at") @Expose private String createdAt;
    @SerializedName("__v") @Expose private long v;
    @SerializedName("question") @Expose private String question;
    @SerializedName("is_played") @Expose private boolean isPlayed;
    @SerializedName("n_groups_joined") @Expose private long nGroupJoined;
    @SerializedName("n_groups_played") @Expose private List<String> nGroupsPlayed = null;
    @SerializedName("category_id") @Expose private String categoryId;
    @SerializedName("is_free") @Expose private boolean isFree;
    @SerializedName("total_participants") @Expose private long totalParticipants;
    @SerializedName("prize") @Expose private double prize;
    @SerializedName("bonus_code") @Expose private long bonusCode;
    @SerializedName("captain_id") @Expose private String captainId;
    @SerializedName("is_contest") @Expose private boolean isContest;
    @SerializedName("is_reverse") @Expose private boolean isReverse;


    protected BattleDetails(Parcel in) {
        id = in.readString();
        livePrizePool = in.readDouble();
        nParticipants = in.readLong();
        isResultDeclared = in.readByte() != 0;
        investedAmount = in.readDouble();
        isActive = in.readByte() != 0;
        isDeleted = in.readByte() != 0;
        matchId = in.readString();
        title = in.readString();
        desc = in.readString();
        nGroups = in.readLong();
        img = in.readString();
        createdAt = in.readString();
        v = in.readLong();
        question = in.readString();
        isPlayed = in.readByte() != 0;
        nGroupJoined = in.readLong();
        nGroupsPlayed = in.createStringArrayList();
        categoryId = in.readString();
        isFree = in.readByte() != 0;
        totalParticipants = in.readLong();
        prize = in.readDouble();
        bonusCode = in.readLong();
        captainId = in.readString();
        isContest = in.readByte() != 0;
        isReverse = in.readByte() != 0;
    }

    public static final Creator<BattleDetails> CREATOR = new Creator<BattleDetails>() {
        @Override
        public BattleDetails createFromParcel(Parcel in) {
            return new BattleDetails(in);
        }

        @Override
        public BattleDetails[] newArray(int size) {
            return new BattleDetails[size];
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

    public long getNParticipants() {
        return nParticipants;
    }

    public void setNParticipants(long nParticipants) {
        this.nParticipants = nParticipants;
    }

    public boolean isResultDeclared() {
        return isResultDeclared;
    }

    public void setResultDeclared(boolean resultDeclared) {
        isResultDeclared = resultDeclared;
    }

    public double getInvestedAmount() {
        return investedAmount;
    }

    public void setInvestedAmount(double investedAmount) {
        this.investedAmount = investedAmount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
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

    public long getNGroups() {
        return nGroups;
    }

    public void setNGroups(long nGroups) {
        this.nGroups = nGroups;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public long getV() {
        return v;
    }

    public void setV(long v) {
        this.v = v;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean played) {
        isPlayed = played;
    }

    public long getnGroupJoined() {
        return nGroupJoined;
    }

    public void setnGroupJoined(long nGroupJoined) {
        this.nGroupJoined = nGroupJoined;
    }

    public List<String> getnGroupsPlayed() {
        return nGroupsPlayed;
    }

    public void setnGroupsPlayed(List<String> nGroupsPlayed) {
        this.nGroupsPlayed = nGroupsPlayed;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
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

    public void setPrize(double prize) {
        this.prize = prize;
    }

    public long getBonusCode() {
        return bonusCode;
    }

    public void setBonusCode(long bonusCode) {
        this.bonusCode = bonusCode;
    }

    public String getCaptainId() {
        return captainId;
    }

    public void setCaptainId(String captainId) {
        this.captainId = captainId;
    }

    public boolean isContest() {
        return isContest;
    }

    public void setContest(boolean contest) {
        isContest = contest;
    }

    public boolean isReverse() {
        return isReverse;
    }

    public void setReverse(boolean reverse) {
        isReverse = reverse;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeDouble(livePrizePool);
        dest.writeLong(nParticipants);
        dest.writeByte((byte) (isResultDeclared ? 1 : 0));
        dest.writeDouble(investedAmount);
        dest.writeByte((byte) (isActive ? 1 : 0));
        dest.writeByte((byte) (isDeleted ? 1 : 0));
        dest.writeString(matchId);
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeLong(nGroups);
        dest.writeString(img);
        dest.writeString(createdAt);
        dest.writeLong(v);
        dest.writeString(question);
        dest.writeByte((byte) (isPlayed ? 1 : 0));
        dest.writeLong(nGroupJoined);
        dest.writeStringList(nGroupsPlayed);
        dest.writeString(categoryId);
        dest.writeByte((byte) (isFree ? 1 : 0));
        dest.writeLong(totalParticipants);
        dest.writeDouble(prize);
        dest.writeLong(bonusCode);
        dest.writeString(captainId);
        dest.writeByte((byte) (isContest ? 1 : 0));
        dest.writeByte((byte) (isReverse ? 1 : 0));
    }


}