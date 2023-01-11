package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionOptions implements Parcelable {

    @SerializedName("_id") @Expose private String id;
    @SerializedName("option") @Expose private String option;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.option);
    }

    public QuestionOptions() {
    }

    protected QuestionOptions(Parcel in) {
        this.id = in.readString();
        this.option = in.readString();
    }

    public static final Parcelable.Creator<QuestionOptions> CREATOR = new Parcelable.Creator<QuestionOptions>() {
        @Override public QuestionOptions createFromParcel(Parcel source) {
            return new QuestionOptions(source);
        }

        @Override public QuestionOptions[] newArray(int size) {
            return new QuestionOptions[size];
        }
    };
}
