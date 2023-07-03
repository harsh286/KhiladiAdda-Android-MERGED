package com.khiladiadda.network.model.response.droid_doresponse;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
public class ResponseLeaderBoard implements Parcelable {
    @SerializedName("leaderboard")
    @Expose
    private ArrayList<ResponseLeaderBoardSub> leaderboard = null;
    @SerializedName("myRank")
    @Expose
    private MyRankDroidoLeaderboard myRank;

    public ArrayList<ResponseLeaderBoardSub> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(ArrayList<ResponseLeaderBoardSub> leaderboard) {
        this.leaderboard = leaderboard;
    }

    public MyRankDroidoLeaderboard getMyRank() {
        return myRank;
    }

    public void setMyRank(MyRankDroidoLeaderboard myRank) {
        this.myRank = myRank;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.leaderboard);
        dest.writeParcelable(this.myRank, flags);
    }

    public void readFromParcel(Parcel source) {
        this.leaderboard = source.createTypedArrayList(ResponseLeaderBoardSub.CREATOR);
        this.myRank = source.readParcelable(MyRankDroidoLeaderboard.class.getClassLoader());
    }

    public ResponseLeaderBoard() {
    }

    protected ResponseLeaderBoard(Parcel in) {
        this.leaderboard = in.createTypedArrayList(ResponseLeaderBoardSub.CREATOR);
        this.myRank = in.readParcelable(MyRankDroidoLeaderboard.class.getClassLoader());
    }

    public static final Creator<ResponseLeaderBoard> CREATOR = new Creator<ResponseLeaderBoard>() {
        @Override
        public ResponseLeaderBoard createFromParcel(Parcel source) {
            return new ResponseLeaderBoard(source);
        }

        @Override
        public ResponseLeaderBoard[] newArray(int size) {
            return new ResponseLeaderBoard[size];
        }
    };
}