package com.khiladiadda.network.model.response.hth;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayersHTH implements Parcelable {
    @SerializedName("awayTeam")
    @Expose
    private AwayTeamHTH awayTeam;
    @SerializedName("homeTeam")
    @Expose
    private HomeTeamHTH homeTeam;

    public AwayTeamHTH getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(AwayTeamHTH awayTeam) {
        this.awayTeam = awayTeam;
    }

    public HomeTeamHTH getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(HomeTeamHTH homeTeam) {
        this.homeTeam = homeTeam;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.awayTeam, flags);
        dest.writeParcelable(this.homeTeam, flags);
    }

    public void readFromParcel(Parcel source) {
        this.awayTeam = source.readParcelable(AwayTeamHTH.class.getClassLoader());
        this.homeTeam = source.readParcelable(HomeTeamHTH.class.getClassLoader());
    }

    public PlayersHTH() {
    }

    protected PlayersHTH(Parcel in) {
        this.awayTeam = in.readParcelable(AwayTeamHTH.class.getClassLoader());
        this.homeTeam = in.readParcelable(HomeTeamHTH.class.getClassLoader());
    }

    public static final Parcelable.Creator<PlayersHTH> CREATOR = new Parcelable.Creator<PlayersHTH>() {
        @Override
        public PlayersHTH createFromParcel(Parcel source) {
            return new PlayersHTH(source);
        }

        @Override
        public PlayersHTH[] newArray(int size) {
            return new PlayersHTH[size];
        }
    };
}
