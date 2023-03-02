package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class WordSearchCategoriesQuizzesMainResponse extends BaseResponse implements Parcelable {
    @SerializedName("response")
    @Expose
    private List<WordSearchCategoriesQuizzesResponse> response = null;

    public List<WordSearchCategoriesQuizzesResponse> getResponse() {
        return response;
    }

    public void setResponse(List<WordSearchCategoriesQuizzesResponse> response) {
        this.response = response;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.response);
    }

    public void readFromParcel(Parcel source) {
        this.response = source.createTypedArrayList(WordSearchCategoriesQuizzesResponse.CREATOR);
    }

    public WordSearchCategoriesQuizzesMainResponse() {
    }

    protected WordSearchCategoriesQuizzesMainResponse(Parcel in) {
        this.response = in.createTypedArrayList(WordSearchCategoriesQuizzesResponse.CREATOR);
    }

    public static final Creator<WordSearchCategoriesQuizzesMainResponse> CREATOR = new Creator<WordSearchCategoriesQuizzesMainResponse>() {
        @Override
        public WordSearchCategoriesQuizzesMainResponse createFromParcel(Parcel source) {
            return new WordSearchCategoriesQuizzesMainResponse(source);
        }

        @Override
        public WordSearchCategoriesQuizzesMainResponse[] newArray(int size) {
            return new WordSearchCategoriesQuizzesMainResponse[size];
        }
    };
}
