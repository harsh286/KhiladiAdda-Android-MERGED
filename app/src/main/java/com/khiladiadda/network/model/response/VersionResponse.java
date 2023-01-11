package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class VersionResponse extends BaseResponse {

    @SerializedName("response") @Expose private VersionDetails response = null;

    public VersionDetails getVersion() {
        return response;
    }

    public void setVersion(VersionDetails version) {
        this.response = version;
    }

    @SerializedName("topUsers") @Expose private List<TopUsersDetails> topUsers = null;

    public List<TopUsersDetails> getTopUsers() {
        return topUsers;
    }

    public void setTopUsers(List<TopUsersDetails> topUsers) {
        this.topUsers = topUsers;
    }

    @SerializedName("banners") @Expose private List<BannerDetails> banners = null;

    public List<BannerDetails> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerDetails> banners) {
        this.banners = banners;
    }

    @SerializedName("bannedState") @Expose private List<String> bannerStates = null;
    @SerializedName("allowedCountry") @Expose private List<String> allowCountries = null;

    public VersionDetails getResponse() {
        return response;
    }

    public void setResponse(VersionDetails response) {
        this.response = response;
    }

    public List<String> getBannerStates() {
        return bannerStates;
    }

    public void setBannerStates(List<String> bannerStates) {
        this.bannerStates = bannerStates;
    }

    public List<String> getAllowCountries() {
        return allowCountries;
    }

    public void setAllowCountries(List<String> allowCountries) {
        this.allowCountries = allowCountries;
    }
}