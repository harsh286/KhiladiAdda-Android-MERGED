package com.khiladiadda.network.model.response.hth;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamHTH implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("isBatting")
    @Expose
    private Boolean isBatting;
    @SerializedName("logoUrl")
    @Expose
    private String logoUrl;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("shortName")
    @Expose
    private String shortName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsBatting() {
        return isBatting;
    }

    public void setIsBatting(Boolean isBatting) {
        this.isBatting = isBatting;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.isBatting);
        dest.writeString(this.logoUrl);
        dest.writeString(this.name);
        dest.writeString(this.shortName);
    }

    public void readFromParcel(Parcel source) {
        this.id = (Integer) source.readValue(Integer.class.getClassLoader());
        this.isBatting = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.logoUrl = source.readString();
        this.name = source.readString();
        this.shortName = source.readString();
    }

    public TeamHTH() {
    }

    protected TeamHTH(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isBatting = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.logoUrl = in.readString();
        this.name = in.readString();
        this.shortName = in.readString();
    }

    public static final Parcelable.Creator<TeamHTH> CREATOR = new Parcelable.Creator<TeamHTH>() {
        @Override
        public TeamHTH createFromParcel(Parcel source) {
            return new TeamHTH(source);
        }

        @Override
        public TeamHTH[] newArray(int size) {
            return new TeamHTH[size];
        }
    };
}
