package com.khiladiadda.network.model.response.hth;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CaptainHTH implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("dp")
    @Expose
    private String dp;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("is_team_updated")
    private boolean is_team_updated;
    @SerializedName("is_won") boolean is_won;
    @SerializedName("prize") private double prize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIs_team_updated() {
        return is_team_updated;
    }

    public void setIs_team_updated(boolean is_team_updated) {
        this.is_team_updated = is_team_updated;
    }

    public boolean isIs_won() {
        return is_won;
    }

    public void setIs_won(boolean is_won) {
        this.is_won = is_won;
    }

    public double getPrize() {
        return prize;
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.username);
        dest.writeString(this.dp);
        dest.writeString(this.name);
        dest.writeByte(this.is_team_updated ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_won ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.prize);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.username = source.readString();
        this.dp = source.readString();
        this.name = source.readString();
        this.is_team_updated = source.readByte() != 0;
        this.is_won = source.readByte() != 0;
        this.prize = source.readDouble();
    }

    public CaptainHTH() {
    }

    protected CaptainHTH(Parcel in) {
        this.id = in.readString();
        this.username = in.readString();
        this.dp = in.readString();
        this.name = in.readString();
        this.is_team_updated = in.readByte() != 0;
        this.is_won = in.readByte() != 0;
        this.prize = in.readDouble();
    }

    public static final Parcelable.Creator<CaptainHTH> CREATOR = new Parcelable.Creator<CaptainHTH>() {
        @Override
        public CaptainHTH createFromParcel(Parcel source) {
            return new CaptainHTH(source);
        }

        @Override
        public CaptainHTH[] newArray(int size) {
            return new CaptainHTH[size];
        }
    };
}
