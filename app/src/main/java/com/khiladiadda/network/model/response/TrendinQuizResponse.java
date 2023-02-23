package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.QuizListDetails;

import java.util.List;

public class TrendinQuizResponse extends BaseResponse {

    @SerializedName("response") @Expose private List<QuizListDetails> response = null;

    public List<QuizListDetails> getResponse() {
        return response;
    }

    public void setResponse(List<QuizListDetails> response) {
        this.response = response;
    }

    @SerializedName("banners")
    @Expose
    private List<BannerDetails> banner = null;

    public List<BannerDetails> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerDetails> banner) {
        this.banner = banner;
    }
}