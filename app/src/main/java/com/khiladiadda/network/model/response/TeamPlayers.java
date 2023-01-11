package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamPlayers implements Parcelable {
    @SerializedName("homeTeam") @Expose private Teams homeTeam;
    @SerializedName("awayTeam") @Expose private Teams awayTeam;

    protected TeamPlayers(Parcel in) {
        homeTeam = in.readParcelable(Teams.class.getClassLoader());
        awayTeam = in.readParcelable(Teams.class.getClassLoader());
    }

    public static final Creator<TeamPlayers> CREATOR = new Creator<TeamPlayers>() {
        @Override public TeamPlayers createFromParcel(Parcel in) {
            return new TeamPlayers(in);
        }

        @Override public TeamPlayers[] newArray(int size) {
            return new TeamPlayers[size];
        }
    };

    public Teams getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Teams homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Teams getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Teams awayTeam) {
        this.awayTeam = awayTeam;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(homeTeam, flags);
        dest.writeParcelable(awayTeam, flags);
    }
}
