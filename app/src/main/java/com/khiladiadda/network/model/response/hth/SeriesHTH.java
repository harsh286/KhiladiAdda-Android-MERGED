package com.khiladiadda.network.model.response.hth;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeriesHTH implements Parcelable {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("shortName")
    @Expose
    private String shortName;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.shortName);
    }

    public void readFromParcel(Parcel source) {
        this.name = source.readString();
        this.shortName = source.readString();
    }

    public SeriesHTH() {
    }

    protected SeriesHTH(Parcel in) {
        this.name = in.readString();
        this.shortName = in.readString();
    }

    public static final Parcelable.Creator<SeriesHTH> CREATOR = new Parcelable.Creator<SeriesHTH>() {
        @Override
        public SeriesHTH createFromParcel(Parcel source) {
            return new SeriesHTH(source);
        }

        @Override
        public SeriesHTH[] newArray(int size) {
            return new SeriesHTH[size];
        }
    };
}

