package com.khiladiadda.network.model.response.droid_doresponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLeaderBoardUserInfo implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dp")
    @Expose
    private String dp;

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

    public ResponseLeaderBoardUserInfo() {
    }

    protected ResponseLeaderBoardUserInfo(Parcel in) {
        this.name = in.readString();
        this.dp = in.readString();
    }

    public static final Creator<ResponseLeaderBoardUserInfo> CREATOR = new Creator<ResponseLeaderBoardUserInfo>() {
        @Override
        public ResponseLeaderBoardUserInfo createFromParcel(Parcel source) {
            return new ResponseLeaderBoardUserInfo(source);
        }

        @Override
        public ResponseLeaderBoardUserInfo[] newArray(int size) {
            return new ResponseLeaderBoardUserInfo[size];
        }
    };
}