package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Series implements Parcelable {
    @SerializedName("id") @Expose private long id;
    @SerializedName("name") @Expose private String name;
    @SerializedName("shortName") @Expose private String shortName;

    protected Series(Parcel in) {
        id = in.readLong();
        name = in.readString();
        shortName = in.readString();
    }

    public static final Creator<Series> CREATOR = new Creator<Series>() {
        @Override public Series createFromParcel(Parcel in) {
            return new Series(in);
        }

        @Override public Series[] newArray(int size) {
            return new Series[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(shortName);
    }
}
