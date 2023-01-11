package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuizListDetails implements Parcelable {

    @SerializedName("totalparticipants") @Expose private long totalparticipants;
    @SerializedName("isTrending") @Expose private boolean isTrending;
    @SerializedName("playedparticipants") @Expose private long playedparticipants;
    @SerializedName("result_declared") @Expose private boolean resultDeclared;
    @SerializedName("_id") @Expose private String id;
    @SerializedName("name") @Expose private String name;
    @SerializedName("category_id") @Expose private String categoryId;
    @SerializedName("start") @Expose private String start;
    @SerializedName("end") @Expose private String end;
    @SerializedName("entry_fees") @Expose private long entryFees;
    @SerializedName("prize_pool_breakthrough") @Expose private List<PrizePoolBreakthrough> prizePoolBreakthrough = null;
    @SerializedName("bonus_code") @Expose private long bonusCode;
    @SerializedName("prizemoney") @Expose private long prizemoney;
    @SerializedName("created_at") @Expose private String createdAt;
    @SerializedName("updated_at") @Expose private String updatedAt;
    @SerializedName("image") @Expose private String image;
    @SerializedName("is_played") @Expose private String isPlayed;
    @SerializedName("is_cancelled") @Expose private boolean isCancelled;

    public long getTotalparticipants() {
        return totalparticipants;
    }

    public void setTotalparticipants(long totalparticipants) {
        this.totalparticipants = totalparticipants;
    }

    public boolean isIsTrending() {
        return isTrending;
    }

    public void setIsTrending(boolean isTrending) {
        this.isTrending = isTrending;
    }

    public long getPlayedparticipants() {
        return playedparticipants;
    }

    public void setPlayedparticipants(long playedparticipants) {
        this.playedparticipants = playedparticipants;
    }

    public boolean isResultDeclared() {
        return resultDeclared;
    }

    public void setResultDeclared(boolean resultDeclared) {
        this.resultDeclared = resultDeclared;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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

    public long getEntryFees() {
        return entryFees;
    }

    public void setEntryFees(long entryFees) {
        this.entryFees = entryFees;
    }

    public List<PrizePoolBreakthrough> getPrizePoolBreakthrough() {
        return prizePoolBreakthrough;
    }

    public void setPrizePoolBreakthrough(List<PrizePoolBreakthrough> prizePoolBreakthrough) {
        this.prizePoolBreakthrough = prizePoolBreakthrough;
    }

    public long getBonusCode() {
        return bonusCode;
    }

    public void setBonusCode(long bonusCode) {
        this.bonusCode = bonusCode;
    }

    public long getPrizemoney() {
        return prizemoney;
    }

    public void setPrizemoney(long prizemoney) {
        this.prizemoney = prizemoney;
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

    public boolean isTrending() {
        return isTrending;
    }

    public void setTrending(boolean trending) {
        isTrending = trending;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsPlayed() {
        return isPlayed;
    }

    public void setIsPlayed(String isPlayed) {
        this.isPlayed = isPlayed;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.totalparticipants);
        dest.writeByte(this.isTrending ? (byte) 1 : (byte) 0);
        dest.writeLong(this.playedparticipants);
        dest.writeByte(this.resultDeclared ? (byte) 1 : (byte) 0);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.categoryId);
        dest.writeString(this.start);
        dest.writeString(this.end);
        dest.writeLong(this.entryFees);
        dest.writeTypedList(this.prizePoolBreakthrough);
        dest.writeLong(this.bonusCode);
        dest.writeLong(this.prizemoney);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.image);
        dest.writeString(this.isPlayed);
        dest.writeByte(this.isCancelled ? (byte) 1 : (byte) 0);
    }

    public QuizListDetails() {
    }

    protected QuizListDetails(Parcel in) {
        this.totalparticipants = in.readLong();
        this.isTrending = in.readByte() != 0;
        this.playedparticipants = in.readLong();
        this.resultDeclared = in.readByte() != 0;
        this.id = in.readString();
        this.name = in.readString();
        this.categoryId = in.readString();
        this.start = in.readString();
        this.end = in.readString();
        this.entryFees = in.readLong();
        this.prizePoolBreakthrough = in.createTypedArrayList(PrizePoolBreakthrough.CREATOR);
        this.bonusCode = in.readLong();
        this.prizemoney = in.readLong();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.image = in.readString();
        this.isPlayed = in.readString();
        this.isCancelled = in.readByte() != 0;
    }

    public static final Parcelable.Creator<QuizListDetails> CREATOR = new Parcelable.Creator<QuizListDetails>() {
        @Override public QuizListDetails createFromParcel(Parcel source) {
            return new QuizListDetails(source);
        }

        @Override public QuizListDetails[] newArray(int size) {
            return new QuizListDetails[size];
        }
    };
}