package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizSubmitResponseDetails implements Parcelable {

    @SerializedName("n_attempts") @Expose private long nAttempts;
    @SerializedName("attempted_questions") @Expose private long attemptedQuestions;
    @SerializedName("skipped_questions") @Expose private long skippedQuestions;
    @SerializedName("right_answers") @Expose private long rightAnswers;
    @SerializedName("wrong_answers") @Expose private long wrongAnswers;
    @SerializedName("time_taken") @Expose private long timeTaken;
    @SerializedName("rank") @Expose private long rank;
    @SerializedName("is_won") @Expose private boolean isWon;
    @SerializedName("winning_amount") @Expose private long winningAmount;
    @SerializedName("is_completed") @Expose private boolean isCompleted;
    @SerializedName("_id") @Expose private String id;
    @SerializedName("user_id") @Expose private String userId;
    @SerializedName("quiz_id") @Expose private String quizId;
    @SerializedName("started_at") @Expose private String startedAt;
    @SerializedName("ended_at") @Expose private String endedAt;
    @SerializedName("created_at") @Expose private String createdAt;
    @SerializedName("updated_at") @Expose private String updatedAt;

    public long getNAttempts() {
        return nAttempts;
    }

    public void setNAttempts(long nAttempts) {
        this.nAttempts = nAttempts;
    }

    public long getAttemptedQuestions() {
        return attemptedQuestions;
    }

    public void setAttemptedQuestions(long attemptedQuestions) {
        this.attemptedQuestions = attemptedQuestions;
    }

    public long getSkippedQuestions() {
        return skippedQuestions;
    }

    public void setSkippedQuestions(long skippedQuestions) {
        this.skippedQuestions = skippedQuestions;
    }

    public long getRightAnswers() {
        return rightAnswers;
    }

    public void setRightAnswers(long rightAnswers) {
        this.rightAnswers = rightAnswers;
    }

    public long getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(long wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public boolean isIsWon() {
        return isWon;
    }

    public void setIsWon(boolean isWon) {
        this.isWon = isWon;
    }

    public long getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(long winningAmount) {
        this.winningAmount = winningAmount;
    }

    public boolean isIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(String endedAt) {
        this.endedAt = endedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.nAttempts);
        dest.writeLong(this.attemptedQuestions);
        dest.writeLong(this.skippedQuestions);
        dest.writeLong(this.rightAnswers);
        dest.writeLong(this.wrongAnswers);
        dest.writeLong(this.timeTaken);
        dest.writeLong(this.rank);
        dest.writeByte(this.isWon ? (byte) 1 : (byte) 0);
        dest.writeLong(this.winningAmount);
        dest.writeByte(this.isCompleted ? (byte) 1 : (byte) 0);
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.quizId);
        dest.writeString(this.startedAt);
        dest.writeString(this.endedAt);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
    }

    public QuizSubmitResponseDetails() {
    }

    protected QuizSubmitResponseDetails(Parcel in) {
        this.nAttempts = in.readLong();
        this.attemptedQuestions = in.readLong();
        this.skippedQuestions = in.readLong();
        this.rightAnswers = in.readLong();
        this.wrongAnswers = in.readLong();
        this.timeTaken = in.readLong();
        this.rank = in.readLong();
        this.isWon = in.readByte() != 0;
        this.winningAmount = in.readLong();
        this.isCompleted = in.readByte() != 0;
        this.id = in.readString();
        this.userId = in.readString();
        this.quizId = in.readString();
        this.startedAt = in.readString();
        this.endedAt = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
    }

    public static final Parcelable.Creator<QuizSubmitResponseDetails> CREATOR = new Parcelable.Creator<QuizSubmitResponseDetails>() {
        @Override public QuizSubmitResponseDetails createFromParcel(Parcel source) {
            return new QuizSubmitResponseDetails(source);
        }

        @Override public QuizSubmitResponseDetails[] newArray(int size) {
            return new QuizSubmitResponseDetails[size];
        }
    };
}
