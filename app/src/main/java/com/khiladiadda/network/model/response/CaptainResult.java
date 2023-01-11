package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CaptainResult implements Parcelable {
    @SerializedName("rank") @Expose private long rank = 0;
    @SerializedName("image") @Expose private String image;
    @SerializedName("is_won") @Expose private boolean isWon;

    protected CaptainResult(Parcel in) {
        rank = in.readLong();
        image = in.readString();
        isWon = in.readByte() != 0;
    }

    public static final Creator<CaptainResult> CREATOR = new Creator<CaptainResult>() {
        @Override public CaptainResult createFromParcel(Parcel in) {
            return new CaptainResult(in);
        }

        @Override public CaptainResult[] newArray(int size) {
            return new CaptainResult[size];
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
