package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrizePoolBreakthrough implements Parcelable {

    @SerializedName("_id") @Expose private String id;
    @SerializedName("from") @Expose private long from;
    @SerializedName("prizeMoney") @Expose private long prizeMoney;
    @SerializedName("to") @Expose private long to;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getPrizeMoney() {
        return prizeMoney;
    }

    public void setPrizeMoney(long prizeMoney) {
        this.prizeMoney = prizeMoney;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeLong(this.from);
        dest.writeLong(this.prizeMoney);
        dest.writeLong(this.to);
    }

    public PrizePoolBreakthrough() {
    }

    protected PrizePoolBreakthrough(Parcel in) {
        this.id = in.readString();
        this.from = in.readLong();
        this.prizeMoney = in.readLong();
        this.to = in.readLong();
    }

    public static final Parcelable.Creator<PrizePoolBreakthrough> CREATOR = new Parcelable.Creator<PrizePoolBreakthrough>() {
        @Override public PrizePoolBreakthrough createFromParcel(Parcel source) {
            return new PrizePoolBreakthrough(source);
        }

        @Override public PrizePoolBreakthrough[] newArray(int size) {
            return new PrizePoolBreakthrough[size];
        }
    };
}