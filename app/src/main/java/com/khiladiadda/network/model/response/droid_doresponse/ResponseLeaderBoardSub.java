package com.khiladiadda.network.model.response.droid_doresponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLeaderBoardSub implements Parcelable {
    @SerializedName("user_info")
    @Expose
    private ResponseLeaderBoardUserInfo userInfo;
    @SerializedName("game_name")
    @Expose
    private String gameName;
    @SerializedName("game_type")
    @Expose
    private Integer gameType;
    @SerializedName("time_taken")
    @Expose
    private Integer timeTaken;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("winning_amount")
    @Expose
    private Integer winningAmount;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("tournament_status")
    @Expose
    private Integer mTournamentStatus;

    public ResponseLeaderBoardUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ResponseLeaderBoardUserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Integer getGameType() {
        return gameType;
    }

    public void setGameType(Integer gameType) {
        this.gameType = gameType;
    }

    public Integer getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Integer timeTaken) {
        this.timeTaken = timeTaken;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(Integer winningAmount) {
        this.winningAmount = winningAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getmTournamentStatus() {
        return mTournamentStatus;
    }

    public void setmTournamentStatus(Integer mTournamentStatus) {
        this.mTournamentStatus = mTournamentStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.userInfo, flags);
        dest.writeString(this.gameName);
        dest.writeValue(this.gameType);
        dest.writeValue(this.timeTaken);
        dest.writeValue(this.score);
        dest.writeValue(this.winningAmount);
        dest.writeString(this.userId);
        dest.writeValue(this.rank);
        dest.writeValue(this.mTournamentStatus);
    }

    public void readFromParcel(Parcel source) {
        this.userInfo = source.readParcelable(ResponseLeaderBoardUserInfo.class.getClassLoader());
        this.gameName = source.readString();
        this.gameType = (Integer) source.readValue(Integer.class.getClassLoader());
        this.timeTaken = (Integer) source.readValue(Integer.class.getClassLoader());
        this.score = (Integer) source.readValue(Integer.class.getClassLoader());
        this.winningAmount = (Integer) source.readValue(Integer.class.getClassLoader());
        this.userId = source.readString();
        this.rank = (Integer) source.readValue(Integer.class.getClassLoader());
        this.mTournamentStatus = (Integer) source.readValue(Integer.class.getClassLoader());
    }

    public ResponseLeaderBoardSub() {
    }

    protected ResponseLeaderBoardSub(Parcel in) {
        this.userInfo = in.readParcelable(ResponseLeaderBoardUserInfo.class.getClassLoader());
        this.gameName = in.readString();
        this.gameType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.timeTaken = (Integer) in.readValue(Integer.class.getClassLoader());
        this.score = (Integer) in.readValue(Integer.class.getClassLoader());
        this.winningAmount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userId = in.readString();
        this.rank = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mTournamentStatus = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<ResponseLeaderBoardSub> CREATOR = new Creator<ResponseLeaderBoardSub>() {
        @Override
        public ResponseLeaderBoardSub createFromParcel(Parcel source) {
            return new ResponseLeaderBoardSub(source);
        }

        @Override
        public ResponseLeaderBoardSub[] newArray(int size) {
            return new ResponseLeaderBoardSub[size];
        }
    };
}