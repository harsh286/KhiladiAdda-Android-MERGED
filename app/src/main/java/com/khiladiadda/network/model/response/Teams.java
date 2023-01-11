package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Teams implements Parcelable{
    @SerializedName("team")
    @Expose private FBTeamDetails teamDetails;
//    @SerializedName("players") @Expose private List<Object> players = null;

    protected Teams(Parcel in) {
        teamDetails = in.readParcelable(FBTeamDetails.class.getClassLoader());
    }

    public static final Creator<Teams> CREATOR = new Creator<Teams>() {
        @Override public Teams createFromParcel(Parcel in) {
            return new Teams(in);
        }

        @Override public Teams[] newArray(int size) {
            return new Teams[size];
        }
    };

    public FBTeamDetails getTeamDetails() {
        return teamDetails;
    }

    public void setTeamDetails(FBTeamDetails teamDetails) {
        this.teamDetails = teamDetails;
    }

//    public List<Object> getPlayers() {
//        return players;
//    }
//
//    public void setPlayers(List<Object> players) {
//        this.players = players;
//    }


    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(teamDetails, flags);
    }
}