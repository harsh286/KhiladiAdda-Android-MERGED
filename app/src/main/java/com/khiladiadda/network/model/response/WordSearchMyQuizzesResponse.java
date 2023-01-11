package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WordSearchMyQuizzesResponse implements Parcelable {

    @SerializedName("winning_amount")
    @Expose
    private Integer winningAmount;
    @SerializedName("quiz_id")
    @Expose
    private String quizId;
    @SerializedName("entry_fees")
    @Expose
    private Integer entryFees;
    @SerializedName("quiz")
    @Expose
    private List<WordSearchMyQuizzesQuizResponse> quiz = null;

    public Integer getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(Integer winningAmount) {
        this.winningAmount = winningAmount;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public Integer getEntryFees() {
        return entryFees;
    }

    public void setEntryFees(Integer entryFees) {
        this.entryFees = entryFees;
    }

    public List<WordSearchMyQuizzesQuizResponse> getQuiz() {
        return quiz;
    }

    public void setQuiz(List<WordSearchMyQuizzesQuizResponse> quiz) {
        this.quiz = quiz;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.winningAmount);
        dest.writeString(this.quizId);
        dest.writeValue(this.entryFees);
        dest.writeTypedList(this.quiz);
    }

    public void readFromParcel(Parcel source) {
        this.winningAmount = (Integer) source.readValue(Integer.class.getClassLoader());
        this.quizId = source.readString();
        this.entryFees = (Integer) source.readValue(Integer.class.getClassLoader());
        this.quiz = source.createTypedArrayList(WordSearchMyQuizzesQuizResponse.CREATOR);
    }

    public WordSearchMyQuizzesResponse() {
    }

    protected WordSearchMyQuizzesResponse(Parcel in) {
        this.winningAmount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.quizId = in.readString();
        this.entryFees = (Integer) in.readValue(Integer.class.getClassLoader());
        this.quiz = in.createTypedArrayList(WordSearchMyQuizzesQuizResponse.CREATOR);
    }

    public static final Creator<WordSearchMyQuizzesResponse> CREATOR = new Creator<WordSearchMyQuizzesResponse>() {
        @Override
        public WordSearchMyQuizzesResponse createFromParcel(Parcel source) {
            return new WordSearchMyQuizzesResponse(source);
        }

        @Override
        public WordSearchMyQuizzesResponse[] newArray(int size) {
            return new WordSearchMyQuizzesResponse[size];
        }
    };
}