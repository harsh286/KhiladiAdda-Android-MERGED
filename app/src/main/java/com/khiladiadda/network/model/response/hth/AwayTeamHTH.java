package com.khiladiadda.network.model.response.hth;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AwayTeamHTH implements Parcelable {
    @SerializedName("team")
    @Expose
    private TeamHTH team;

    public TeamHTH getTeam() {
        return team;
    }

    public void setTeam(TeamHTH team) {
        this.team = team;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.team, flags);
    }

    public void readFromParcel(Parcel source) {
        this.team = source.readParcelable(TeamHTH.class.getClassLoader());
    }

    public AwayTeamHTH() {
    }

    protected AwayTeamHTH(Parcel in) {
        this.team = in.readParcelable(TeamHTH.class.getClassLoader());
    }

    public static final Parcelable.Creator<AwayTeamHTH> CREATOR = new Parcelable.Creator<AwayTeamHTH>() {
        @Override
        public AwayTeamHTH createFromParcel(Parcel source) {
            return new AwayTeamHTH(source);
        }

        @Override
        public AwayTeamHTH[] newArray(int size) {
            return new AwayTeamHTH[size];
        }
    };
}
