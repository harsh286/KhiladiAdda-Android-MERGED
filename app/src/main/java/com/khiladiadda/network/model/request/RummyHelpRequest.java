package com.khiladiadda.network.model.request;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class RummyHelpRequest {
    @SerializedName("categoryType")
    @Expose
    private Integer mCategoryType;
    @SerializedName("SubCategoryType")
    @Expose
    private Integer mSubCategoryType;
    @SerializedName("languageType")
    @Expose
    private Integer mLanguageType;

    public RummyHelpRequest(Integer mCategoryType, Integer mSubCategoryType, Integer mLanguageType) {
        this.mCategoryType = mCategoryType;
        this.mSubCategoryType = mSubCategoryType;
        this.mLanguageType = mLanguageType;
    }

    public Integer getmCategoryType() {
        return mCategoryType;
    }

    public void setmCategoryType(Integer mCategoryType) {
        this.mCategoryType = mCategoryType;
    }

    public Integer getmSubCategoryType() {
        return mSubCategoryType;
    }

    public void setmSubCategoryType(Integer mSubCategoryType) {
        this.mSubCategoryType = mSubCategoryType;
    }

    public Integer getmLanguageType() {
        return mLanguageType;
    }

    public void setmLanguageType(Integer mLanguageType) {
        this.mLanguageType = mLanguageType;
    }
}
