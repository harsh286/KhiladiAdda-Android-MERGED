package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HelpDetails implements Parcelable {

    @SerializedName("_id") @Expose private String id;
    @SerializedName("question") @Expose private String question;
    @SerializedName("answer") @Expose private String answer;
    @SerializedName("expand") @Expose private boolean expand;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.question);
        dest.writeString(this.answer);
    }

    public HelpDetails() {
    }

    protected HelpDetails(Parcel in) {
        this.id = in.readString();
        this.question = in.readString();
        this.answer = in.readString();
    }

    public static final Creator<HelpDetails> CREATOR = new Creator<HelpDetails>() {
        @Override public HelpDetails createFromParcel(Parcel source) {
            return new HelpDetails(source);
        }

        @Override public HelpDetails[] newArray(int size) {
            return new HelpDetails[size];
        }
    };
}
