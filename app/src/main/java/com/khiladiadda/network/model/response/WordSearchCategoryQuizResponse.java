package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WordSearchCategoryQuizResponse implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("colour")
    @Expose
    private String colour;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("quizzes")
    @Expose
    private List<WordSearchQuizResponse> quizzes = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<WordSearchQuizResponse> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<WordSearchQuizResponse> quizzes) {
        this.quizzes = quizzes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.colour);
        dest.writeString(this.logo);
        dest.writeString(this.image);
        dest.writeTypedList(this.quizzes);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.name = source.readString();
        this.colour = source.readString();
        this.logo = source.readString();
        this.image = source.readString();
        this.quizzes = source.createTypedArrayList(WordSearchQuizResponse.CREATOR);
    }

    public WordSearchCategoryQuizResponse() {
    }

    protected WordSearchCategoryQuizResponse(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.colour = in.readString();
        this.logo = in.readString();
        this.image = in.readString();
        this.quizzes = in.createTypedArrayList(WordSearchQuizResponse.CREATOR);
    }

    public static final Creator<WordSearchCategoryQuizResponse> CREATOR = new Creator<WordSearchCategoryQuizResponse>() {
        @Override
        public WordSearchCategoryQuizResponse createFromParcel(Parcel source) {
            return new WordSearchCategoryQuizResponse(source);
        }

        @Override
        public WordSearchCategoryQuizResponse[] newArray(int size) {
            return new WordSearchCategoryQuizResponse[size];
        }
    };
}