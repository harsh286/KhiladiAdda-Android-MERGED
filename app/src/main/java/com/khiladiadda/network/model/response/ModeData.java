package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModeData {

    @SerializedName("config")
    @Expose
    private ModeConfig config;
    @SerializedName("banners")
    @Expose
    private List<BannerDetails> banners = null;

    public ModeConfig getConfig() {
        return config;
    }

    public void setConfig(ModeConfig config) {
        this.config = config;
    }

    public List<BannerDetails> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerDetails> banners) {
        this.banners = banners;
    }

}
