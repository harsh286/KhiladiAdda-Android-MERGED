package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallBreakHistoryPlayerResponse implements Parcelable {
    @SerializedName("wallet")
    @Expose
    private Wallet wallet;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("randomName")
    @Expose
    private String randomName;
    @SerializedName("dp")
    @Expose
    private String dp;
    @SerializedName("randomDp")
    @Expose
    private String randomDp;
    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("score")
    @Expose
    private Double score;
    @SerializedName("winningAmount")
    @Expose
    private Double winningAmount;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("joinedAt")
    @Expose
    private String joinedAt;

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRandomName() {
        return randomName;
    }

    public void setRandomName(String randomName) {
        this.randomName = randomName;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getRandomDp() {
        return randomDp;
    }

    public void setRandomDp(String randomDp) {
        this.randomDp = randomDp;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(Double winningAmount) {
        this.winningAmount = winningAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(String joinedAt) {
        this.joinedAt = joinedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.wallet, flags);
        dest.writeString(this.name);
        dest.writeString(this.randomName);
        dest.writeString(this.dp);
        dest.writeString(this.randomDp);
        dest.writeValue(this.rank);
        dest.writeValue(this.score);
        dest.writeValue(this.winningAmount);
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.joinedAt);
    }

    public void readFromParcel(Parcel source) {
        this.wallet = source.readParcelable(Wallet.class.getClassLoader());
        this.name = source.readString();
        this.randomName = source.readString();
        this.dp = source.readString();
        this.randomDp = source.readString();
        this.rank = (Integer) source.readValue(Integer.class.getClassLoader());
        this.score = (Double) source.readValue(Double.class.getClassLoader());
        this.winningAmount = (Double) source.readValue(Double.class.getClassLoader());
        this.id = source.readString();
        this.userId = source.readString();
        this.joinedAt = source.readString();
    }

    public CallBreakHistoryPlayerResponse() {
    }

    protected CallBreakHistoryPlayerResponse(Parcel in) {
        this.wallet = in.readParcelable(Wallet.class.getClassLoader());
        this.name = in.readString();
        this.randomName = in.readString();
        this.dp = in.readString();
        this.randomDp = in.readString();
        this.rank = (Integer) in.readValue(Integer.class.getClassLoader());
        this.score = (Double) in.readValue(Double.class.getClassLoader());
        this.winningAmount = (Double) in.readValue(Double.class.getClassLoader());
        this.id = in.readString();
        this.userId = in.readString();
        this.joinedAt = in.readString();
    }

    public static final Creator<CallBreakHistoryPlayerResponse> CREATOR = new Creator<CallBreakHistoryPlayerResponse>() {
        @Override
        public CallBreakHistoryPlayerResponse createFromParcel(Parcel source) {
            return new CallBreakHistoryPlayerResponse(source);
        }

        @Override
        public CallBreakHistoryPlayerResponse[] newArray(int size) {
            return new CallBreakHistoryPlayerResponse[size];
        }
    };
}
