package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PubgPrizePool implements Parcelable {

    @SerializedName("from") @Expose private Integer from;
    @SerializedName("to") @Expose private Integer to;
    @SerializedName("prize_money") @Expose private double prizeMoney;

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public double getPrizeMoney() {
        return prizeMoney;
    }

    public void setPrizeMoney(double prizeMoney) {
        this.prizeMoney = prizeMoney;
    }


    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.from);
        dest.writeValue(this.to);
        dest.writeValue(this.prizeMoney);
    }

    public PubgPrizePool() {
    }

    protected PubgPrizePool(Parcel in) {
        this.from = (Integer) in.readValue(Integer.class.getClassLoader());
        this.to = (Integer) in.readValue(Integer.class.getClassLoader());
        this.prizeMoney = (double) in.readValue(double.class.getClassLoader());
    }

    public static final Creator<PubgPrizePool> CREATOR = new Creator<PubgPrizePool>() {
        @Override public PubgPrizePool createFromParcel(Parcel source) {
            return new PubgPrizePool(source);
        }

        @Override public PubgPrizePool[] newArray(int size) {
            return new PubgPrizePool[size];
        }
    };
}