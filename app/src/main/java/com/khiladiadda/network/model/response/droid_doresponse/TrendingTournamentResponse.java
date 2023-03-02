package com.khiladiadda.network.model.response.droid_doresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.BannerDetails;

import java.util.List;

public class TrendingTournamentResponse extends BaseResponse {

    @SerializedName("response")
    @Expose
    private List<ResponseData> response;

    public List<ResponseData> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseData> response) {
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
