package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpponentResult implements Parcelable {
    @SerializedName("rank") @Expose private long rank = 0;
    @SerializedName("image") @Expose private String image;
    @SerializedName("is_won") @Expose private boolean isWon;

    protected OpponentResult(Parcel in) {
        rank = in.readLong();
        image = in.readString();
        isWon = in.readByte() != 0;
    }

    public static final Creator<OpponentResult> CREATOR = new Creator<OpponentResult>() {
        @Override public OpponentResult createFromParcel(Parcel in) {
            return new OpponentResult(in);
        }

        @Override public OpponentResult[] newArray(int size) {
            return new OpponentResult[size];
        }
    };

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isIsWon() {
        return isWon;
    }

    public void setIsWon(boolean isWon) {
        this.isWon = isWon;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(rank);
        dest.writeString(image);
        dest.writeByte((byte) (isWon ? 1 : 0));
    }
}
