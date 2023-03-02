package com.khiladiadda.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WordSearchTrendingResponse implements Parcelable {
    @SerializedName("trendingQuiz")
    @Expose
    private List<WordSearchTrendingQuizResponse> trendingQuiz = null;
    @SerializedName("categoryQuiz")
    @Expose
    private List<WordSearchCategoryQuizResponse> categoryQuiz = null;
    @SerializedName("wordsearch_apk_link")
    private String WSLink;
    @SerializedName("apk_version")
    private String apk_version;

    @SerializedName("banners")
    @Expose
    private List<BannerDetails> banner = null;

    public List<BannerDetails> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerDetails> banner) {
        this.banner = banner;
    }

    public List<WordSearchTrendingQuizResponse> getTrendingQuiz() {
        return trendingQuiz;
    }

    public void setTrendingQuiz(List<WordSearchTrendingQuizResponse> trendingQuiz) {
        this.trendingQuiz = trendingQuiz;
    }

    public List<WordSearchCategoryQuizResponse> getCategoryQuiz() {
        return categoryQuiz;
    }

    public void setCategoryQuiz(List<WordSearchCategoryQuizResponse> categoryQuiz) {
        this.categoryQuiz = categoryQuiz;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.trendingQuiz);
        dest.writeTypedList(this.categoryQuiz);
    }

    public void readFromParcel(Parcel source) {
        this.trendingQuiz = source.createTypedArrayList(WordSearchTrendingQuizResponse.CREATOR);
        this.categoryQuiz = source.createTypedArrayList(WordSearchCategoryQuizResponse.CREATOR);
    }

    public WordSearchTrendingResponse() {
    }

    protected WordSearchTrendingResponse(Parcel in) {
        this.trendingQuiz = in.createTypedArrayList(WordSearchTrendingQuizResponse.CREATOR);
        this.categoryQuiz = in.createTypedArrayList(WordSearchCategoryQuizResponse.CREATOR);
    }

    public static final Creator<WordSearchTrendingResponse> CREATOR = new Creator<WordSearchTrendingResponse>() {
        @Override
        public WordSearchTrendingResponse createFromParcel(Parcel source) {
            return new WordSearchTrendingResponse(source);
        }

        @Override
        public WordSearchTrendingResponse[] newArray(int size) {
            return new WordSearchTrendingResponse[size];
        }
    };

    public String getWSLink() {
        return WSLink;
    }

    public void setWSLink(String WSLink) {
        this.WSLink = WSLink;
    }

    public String getApk_version() {
        return apk_version;
    }

    public void setApk_version(String apk_version) {
        this.apk_version = apk_version;
    }
}
