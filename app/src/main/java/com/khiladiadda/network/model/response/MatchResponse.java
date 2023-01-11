package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class MatchResponse extends BaseResponse {
    @SerializedName("response") @Expose private List<MatchDetails> response = null;
    @SerializedName("game") @Expose private GameDetails gameDetails;
    @SerializedName("banners") @Expose private List<BannerDetails> banners = null;

    public List<MatchDetails> getResponse() {
        return response;
    }

    public void setResponse(List<MatchDetails> response) {
        this.response = response;
    }

    public GameDetails getGameDetails() {
        return gameDetails;
    }

    public void setGameDetails(GameDetails gameDetails) {
        this.gameDetails = gameDetails;
    }

    public List<BannerDetails> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerDetails> banners) {
        this.banners = banners;
    }
}