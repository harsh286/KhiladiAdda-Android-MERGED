package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class WordSearchLiveLeaderBoardlbResponse implements Parcelable {

    @SerializedName("user")
    @Expose
    private WordSearchLiveLeaderBoardUserResponse user;
    @SerializedName("right_answers")
    @Expose
    private Integer rightAnswers;
    @SerializedName("time_taken")
    @Expose
    private Integer timeTaken;
    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("winning_amount")
    @Expose
    private Integer winningAmount;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("myRank")
    private Integer myRank;
    public WordSearchLiveLeaderBoardlbResponse(String userId) {
        this.userId = userId;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public WordSearchLiveLeaderBoardUserResponse getUser() {
        return user;
    }

    public void setUser(WordSearchLiveLeaderBoardUserResponse user) {
        this.user = user;
    }

    public Integer getRightAnswers() {
        return rightAnswers;
    }

    public void setRightAnswers(Integer rightAnswers) {
        this.rightAnswers = rightAnswers;
    }

    public Integer getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Integer timeTaken) {
        this.timeTaken = timeTaken;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable((Parcelable) this.user, flags);
        dest.writeValue(this.rightAnswers);
        dest.writeValue(this.timeTaken);
        dest.writeValue(this.rank);
        dest.writeValue(this.winningAmount);
        dest.writeString(this.userId);
        dest.writeValue(this.status);
    }

    public void readFromParcel(Parcel source) {
        this.user = source.readParcelable(WordSearchLiveLeaderBoardUserResponse.class.getClassLoader());
        this.rightAnswers = (Integer) source.readValue(Integer.class.getClassLoader());
        this.timeTaken = (Integer) source.readValue(Integer.class.getClassLoader());
        this.rank = (Integer) source.readValue(Integer.class.getClassLoader());
        this.winningAmount = (Integer) source.readValue(Integer.class.getClassLoader());
        this.userId = source.readString();
        this.status = (Integer) source.readValue(Integer.class.getClassLoader());
    }

    public WordSearchLiveLeaderBoardlbResponse() {
    }

    protected WordSearchLiveLeaderBoardlbResponse(Parcel in) {
        this.user = in.readParcelable(WordSearchLiveLeaderBoardUserResponse.class.getClassLoader());
        this.rightAnswers = (Integer) in.readValue(Integer.class.getClassLoader());
        this.timeTaken = (Integer) in.readValue(Integer.class.getClassLoader());
        this.rank = (Integer) in.readValue(Integer.class.getClassLoader());
        this.winningAmount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userId = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<WordSearchLiveLeaderBoardlbResponse> CREATOR = new Creator<WordSearchLiveLeaderBoardlbResponse>() {
        @Override
        public WordSearchLiveLeaderBoardlbResponse createFromParcel(Parcel source) {
            return new WordSearchLiveLeaderBoardlbResponse(source);
        }

        @Override
        public WordSearchLiveLeaderBoardlbResponse[] newArray(int size) {
            return new WordSearchLiveLeaderBoardlbResponse[size];
        }
    };
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordSearchLiveLeaderBoardlbResponse  that = (WordSearchLiveLeaderBoardlbResponse) o;
        return userId.equals(that.userId);
    }


    @Override public int hashCode() {
        return Objects.hash(userId);
    }

    public Integer getMyRank() {
        return myRank;
    }

    public void setMyRank(Integer myRank) {
        this.myRank = myRank;
    }
}