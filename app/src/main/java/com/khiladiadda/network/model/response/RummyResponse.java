package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class RummyResponse extends BaseResponse {

    @SerializedName("response")
    @Expose
    private List<RummyDetails> response;
    @SerializedName("lastGames")
    @Expose
    private List<RummyDetails> mLiveTableRes;
    @SerializedName("banners")
    @Expose
    private List<BannerDetails> banner = null;

    public List<RummyDetails> getResponse() {
        return response;
    }

    public List<RummyDetails> getmLiveTableRes() {
        return mLiveTableRes;
    }

    public void setmLiveTableRes(List<RummyDetails> mLiveTableRes) {
        this.mLiveTableRes = mLiveTableRes;
    }

    public void setResponse(List<RummyDetails> response) {
        this.response = response;
    }

    public List<BannerDetails> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerDetails> banner) {
        this.banner = banner;
    }
}
