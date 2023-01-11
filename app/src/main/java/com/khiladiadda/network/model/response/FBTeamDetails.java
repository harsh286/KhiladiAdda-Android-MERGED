package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FBTeamDetails implements Parcelable {
    @SerializedName("id") @Expose private long id;
    @SerializedName("name") @Expose private String name;
    @SerializedName("shortName") @Expose private String shortName;
    @SerializedName("logoUrl") @Expose private String logoUrl;

    protected FBTeamDetails(Parcel in) {
        id = in.readLong();
        name = in.readString();
        shortName = in.readString();
        logoUrl = in.readString();
    }

    public static final Creator<FBTeamDetails> CREATOR = new Creator<FBTeamDetails>() {
        @Override public FBTeamDetails createFromParcel(Parcel in) {
            return new FBTeamDetails(in);
        }

        @Override public FBTeamDetails[] newArray(int size) {
            return new FBTeamDetails[size];
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(shortName);
        dest.writeString(logoUrl);
    }
}
