package com.khiladiadda.battle.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BattleCatDetails {
    @SerializedName("_id") @Expose
    private String id;
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
    @SerializedName("myBattle")@Expose private List<MyBattleList>myBattleLists =null;
    @SerializedName("category_id")@Expose private String category_id;

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

    public long getnParticipants() {
        return nParticipants;
    }

    public void setnParticipants(long nParticipants) {
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

    public long getnGroups() {
        return nGroups;
    }

    public void setnGroups(long nGroups) {
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

    public List<MyBattleList> getMyBattleLists() {
        return myBattleLists;
    }

    public void setMyBattleLists(List<MyBattleList> myBattleLists) {
        this.myBattleLists = myBattleLists;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return "BattleCatDetails{" +
                "id='" + id + '\'' +
                ", livePrizePool=" + livePrizePool +
                ", nParticipants=" + nParticipants +
                ", isResultDeclared=" + isResultDeclared +
                ", investedAmount=" + investedAmount +
                ", isActive=" + isActive +
                ", isDeleted=" + isDeleted +
                ", matchId='" + matchId + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", nGroups=" + nGroups +
                ", img='" + img + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", v=" + v +
                ", question='" + question + '\'' +
                ", isPlayed=" + isPlayed +
                ", nGroupJoined=" + nGroupJoined +
                ", nGroupsPlayed=" + nGroupsPlayed +
                ", myBattleLists=" + myBattleLists +
                ", category_id='" + category_id + '\'' +
                '}';
    }
}
