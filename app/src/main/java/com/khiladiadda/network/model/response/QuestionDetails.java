package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class QuestionDetails implements Parcelable {

    @SerializedName("_id") @Expose private String id;
    @SerializedName("quiz_id") @Expose private String quizId;
    @SerializedName("title") @Expose private String title;
    @SerializedName("desc") @Expose private String desc;
    @SerializedName("image") @Expose private String image;
    @SerializedName("video") @Expose private String video;
    @SerializedName("audio") @Expose private String audio;
    @SerializedName("options") @Expose private List<QuestionOptions> options = new ArrayList<>();
    @SerializedName("created_at") @Expose private String createdAt;
    @SerializedName("updated_at") @Expose private String updatedAt;

    private QuestionOptions mSelectedOption;

    private int mTimeTaken;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public List<QuestionOptions> getOptions() {
        return options;
    }

    public void setOptions(List<QuestionOptions> options) {
        this.options = options;
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

    public QuestionOptions getSelectedOption() {
        return mSelectedOption;
    }

    public void setSelectedOption(QuestionOptions mSelectedOption) {
        this.mSelectedOption = mSelectedOption;
    }

    public int getTimeTaken() {
        return mTimeTaken;
    }

    public void setTimeTaken(int mTimeTaken) {
        this.mTimeTaken = mTimeTaken;
    }

    public QuestionDetails() {
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.quizId);
        dest.writeString(this.title);
        dest.writeString(this.desc);
        dest.writeString(this.image);
        dest.writeString(this.video);
        dest.writeString(this.audio);
        dest.writeTypedList(this.options);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
    }

    protected QuestionDetails(Parcel in) {
        this.id = in.readString();
        this.quizId = in.readString();
        this.title = in.readString();
        this.desc = in.readString();
        this.image = in.readString();
        this.video = in.readString();
        this.audio = in.readString();
        this.options = in.createTypedArrayList(QuestionOptions.CREATOR);
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
    }

    public static final Parcelable.Creator<QuestionDetails> CREATOR = new Parcelable.Creator<QuestionDetails>() {
        @Override public QuestionDetails createFromParcel(Parcel source) {
            return new QuestionDetails(source);
        }

        @Override public QuestionDetails[] newArray(int size) {
            return new QuestionDetails[size];
        }
    };
}
