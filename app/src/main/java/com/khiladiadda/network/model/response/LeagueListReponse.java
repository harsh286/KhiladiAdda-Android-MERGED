package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class LeagueListReponse extends BaseResponse {

    @SerializedName("response") @Expose public List<LeagueListDetails> response = null;
    @SerializedName("liveLeagues") @Expose public List<LeagueListDetails> liveLeagueRes=null;
    @SerializedName("banners") @Expose private List<BannerDetails> banners = null;

    public List<LeagueListDetails> getLiveLeagueRes() {
        return liveLeagueRes;
    }

    public void setLiveLeagueRes(List<LeagueListDetails> liveLeagueRes) {
        this.liveLeagueRes = liveLeagueRes;
    }

    public List<LeagueListDetails> getResponse() {
        return response;
    }

    public void setResponse(List<LeagueListDetails> response) {
        this.response = response;
    }
    public List<BannerDetails> getBanners() {
        return banners;
    }
    public void setBanners(List<BannerDetails> banners) {
        this.banners = banners;
    }
}