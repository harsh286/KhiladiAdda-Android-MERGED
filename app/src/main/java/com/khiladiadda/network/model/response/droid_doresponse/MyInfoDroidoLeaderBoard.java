package com.khiladiadda.network.model.response.droid_doresponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyInfoDroidoLeaderBoard implements Parcelable {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dp")
    @Expose
    private String dp;

    public MyInfoDroidoLeaderBoard(String name, String dp) {
        this.name = name;
        this.dp = dp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.dp);
    }

    public void readFromParcel(Parcel source) {
        this.name = source.readString();
        this.dp = source.readString();
    }

    protected MyInfoDroidoLeaderBoard(Parcel in) {
        this.name = in.readString();
        this.dp = in.readString();
    }

    public static final Creator<MyInfoDroidoLeaderBoard> CREATOR = new Creator<MyInfoDroidoLeaderBoard>() {
        @Override
        public MyInfoDroidoLeaderBoard createFromParcel(Parcel source) {
            return new MyInfoDroidoLeaderBoard(source);
        }

        @Override
        public MyInfoDroidoLeaderBoard[] newArray(int size) {
            return new MyInfoDroidoLeaderBoard[size];
        }
    };
}
