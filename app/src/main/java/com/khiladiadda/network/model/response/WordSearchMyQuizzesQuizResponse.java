package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WordSearchMyQuizzesQuizResponse implements Parcelable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("totalparticipants")
    @Expose
    private Integer totalparticipants;
    @SerializedName("playedparticipants")
    @Expose
    private Integer playedparticipants;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("entry_fees")
    @Expose
    private Integer entryFees;
    @SerializedName("prize_pool_breakthrough")
    @Expose
    private List<PrizePoolBreakthrough> prizePoolBreakthrough = null;
    @SerializedName("bonus_code")
    @Expose
    private Integer bonusCode;
    @SerializedName("prizemoney")
    @Expose
    private Integer prizemoney;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("attemptedQuiz")
    @Expose
    private Integer attemptedQuiz;
    @SerializedName("quiz_status")
    @Expose
    private Integer quizStatus;
    @SerializedName("n_row")
    @Expose
    private Integer nRow;
    @SerializedName("n_columns")
    @Expose
    private Integer nColumns;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTotalparticipants() {
        return totalparticipants;
    }

    public void setTotalparticipants(Integer totalparticipants) {
        this.totalparticipants = totalparticipants;
    }

    public Integer getPlayedparticipants() {
        return playedparticipants;
    }

    public void setPlayedparticipants(Integer playedparticipants) {
        this.playedparticipants = playedparticipants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Integer getEntryFees() {
        return entryFees;
    }

    public void setEntryFees(Integer entryFees) {
        this.entryFees = entryFees;
    }

    public List<PrizePoolBreakthrough> getPrizePoolBreakthrough() {
        return prizePoolBreakthrough;
    }

    public void setPrizePoolBreakthrough(List<PrizePoolBreakthrough> prizePoolBreakthrough) {
        this.prizePoolBreakthrough = prizePoolBreakthrough;
    }

    public Integer getBonusCode() {
        return bonusCode;
    }

    public void setBonusCode(Integer bonusCode) {
        this.bonusCode = bonusCode;
    }

    public Integer getPrizemoney() {
        return prizemoney;
    }

    public void setPrizemoney(Integer prizemoney) {
        this.prizemoney = prizemoney;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getAttemptedQuiz() {
        return attemptedQuiz;
    }

    public void setAttemptedQuiz(Integer attemptedQuiz) {
        this.attemptedQuiz = attemptedQuiz;
    }

    public Integer getQuizStatus() {
        return quizStatus;
    }

    public void setQuizStatus(Integer quizStatus) {
        this.quizStatus = quizStatus;
    }

    public Integer getnRow() {
        return nRow;
    }

    public void setnRow(Integer nRow) {
        this.nRow = nRow;
    }

    public Integer getnColumns() {
        return nColumns;
    }

    public void setnColumns(Integer nColumns) {
        this.nColumns = nColumns;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeValue(this.totalparticipants);
        dest.writeValue(this.playedparticipants);
        dest.writeString(this.name);
        dest.writeString(this.end);
        dest.writeValue(this.entryFees);
        dest.writeTypedList(this.prizePoolBreakthrough);
        dest.writeValue(this.bonusCode);
        dest.writeValue(this.prizemoney);
        dest.writeString(this.image);
        dest.writeValue(this.attemptedQuiz);
        dest.writeValue(this.quizStatus);
        dest.writeValue(this.nRow);
        dest.writeValue(this.nColumns);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.totalparticipants = (Integer) source.readValue(Integer.class.getClassLoader());
        this.playedparticipants = (Integer) source.readValue(Integer.class.getClassLoader());
        this.name = source.readString();
        this.end = source.readString();
        this.entryFees = (Integer) source.readValue(Integer.class.getClassLoader());
        this.prizePoolBreakthrough = source.createTypedArrayList(PrizePoolBreakthrough.CREATOR);
        this.bonusCode = (Integer) source.readValue(Integer.class.getClassLoader());
        this.prizemoney = (Integer) source.readValue(Integer.class.getClassLoader());
        this.image = source.readString();
        this.attemptedQuiz = (Integer) source.readValue(Integer.class.getClassLoader());
        this.quizStatus = (Integer) source.readValue(Integer.class.getClassLoader());
        this.nRow = (Integer) source.readValue(Integer.class.getClassLoader());
        this.nColumns = (Integer) source.readValue(Integer.class.getClassLoader());
    }

    public WordSearchMyQuizzesQuizResponse() {
    }

    protected WordSearchMyQuizzesQuizResponse(Parcel in) {
        this.id = in.readString();
        this.totalparticipants = (Integer) in.readValue(Integer.class.getClassLoader());
        this.playedparticipants = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.end = in.readString();
        this.entryFees = (Integer) in.readValue(Integer.class.getClassLoader());
        this.prizePoolBreakthrough = in.createTypedArrayList(PrizePoolBreakthrough.CREATOR);
        this.bonusCode = (Integer) in.readValue(Integer.class.getClassLoader());
        this.prizemoney = (Integer) in.readValue(Integer.class.getClassLoader());
        this.image = in.readString();
        this.attemptedQuiz = (Integer) in.readValue(Integer.class.getClassLoader());
        this.quizStatus = (Integer) in.readValue(Integer.class.getClassLoader());
        this.nRow = (Integer) in.readValue(Integer.class.getClassLoader());
        this.nColumns = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<WordSearchMyQuizzesQuizResponse> CREATOR = new Creator<WordSearchMyQuizzesQuizResponse>() {
        @Override
        public WordSearchMyQuizzesQuizResponse createFromParcel(Parcel source) {
            return new WordSearchMyQuizzesQuizResponse(source);
        }

        @Override
        public WordSearchMyQuizzesQuizResponse[] newArray(int size) {
            return new WordSearchMyQuizzesQuizResponse[size];
        }
    };
}