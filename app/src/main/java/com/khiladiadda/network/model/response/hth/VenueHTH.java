package com.khiladiadda.network.model.response.hth;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VenueHTH implements Parcelable {
    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    public void readFromParcel(Parcel source) {
        this.name = source.readString();
    }

    public VenueHTH() {
    }

    protected VenueHTH(Parcel in) {
        this.name = in.readString();
    }

    public static final Parcelable.Creator<VenueHTH> CREATOR = new Parcelable.Creator<VenueHTH>() {
        @Override
        public VenueHTH createFromParcel(Parcel source) {
            return new VenueHTH(source);
        }

        @Override
        public VenueHTH[] newArray(int size) {
            return new VenueHTH[size];
        }
    };
}
