package com.khiladiadda.network.model.response.hth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.BannerDetails;

import java.util.List;

public class HTHMainResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private List<HTHResponseDetails> response = null;
    @SerializedName("banners")
    @Expose
    private List<BannerDetails> banners = null;

    public List<HTHResponseDetails> getResponse() {
        return response;
    }

    public void setResponse(List<HTHResponseDetails> response) {
        this.response = response;
    }

    public List<BannerDetails> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerDetails> banners) {
        this.banners = banners;
    }
}
