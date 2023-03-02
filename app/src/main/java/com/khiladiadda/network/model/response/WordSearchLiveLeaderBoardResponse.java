package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WordSearchLiveLeaderBoardResponse implements Parcelable {

    @SerializedName("leaderboard")
    @Expose
    private List<WordSearchLiveLeaderBoardlbResponse> leaderboard = null;

    public List<WordSearchLiveLeaderBoardlbResponse> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(List<WordSearchLiveLeaderBoardlbResponse> leaderboard) {
        this.leaderboard = leaderboard;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.leaderboard);
    }

    public void readFromParcel(Parcel source) {
        this.leaderboard = source.createTypedArrayList(WordSearchLiveLeaderBoardlbResponse.CREATOR);
    }

    public WordSearchLiveLeaderBoardResponse() {
    }

    protected WordSearchLiveLeaderBoardResponse(Parcel in) {
        this.leaderboard = in.createTypedArrayList(WordSearchLiveLeaderBoardlbResponse.CREATOR);
    }

    public static final Creator<WordSearchLiveLeaderBoardResponse> CREATOR = new Creator<WordSearchLiveLeaderBoardResponse>() {
        @Override
        public WordSearchLiveLeaderBoardResponse createFromParcel(Parcel source) {
            return new WordSearchLiveLeaderBoardResponse(source);
        }

        @Override
        public WordSearchLiveLeaderBoardResponse[] newArray(int size) {
            return new WordSearchLiveLeaderBoardResponse[size];
        }
    };
}
