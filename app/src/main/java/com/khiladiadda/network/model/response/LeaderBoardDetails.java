package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeaderBoardDetails {
    @SerializedName("leaderboard") @Expose private List<LeaderBoard> leaderboard = null;
    @SerializedName("myRank") @Expose private long myRank;

    public List<LeaderBoard> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(List<LeaderBoard> leaderboard) {
        this.leaderboard = leaderboard;
    }

    public long getMyRank() {
        return myRank;
    }

    public void setMyRank(long myRank) {
        this.myRank = myRank;
    }
}