package com.khiladiadda.network.model.response.ludoTournament;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.BannerDetails;
import com.khiladiadda.network.model.response.LudoMetaInfo;
import com.khiladiadda.network.model.response.ProfileDetails;

import java.util.List;

public class LudoTmtAllTournamentMainResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private List<LudoTmtAllTournamentResponse> response = null;
    @SerializedName("profile")
    @Expose
    private ProfileDetails profile;
    @SerializedName("isLudoEnabled")
    @Expose
    private Boolean isLudoEnabled;
    @SerializedName("ludo_apk_link")
    @Expose
    private String ludoApkLink;
    @SerializedName("apk_version")
    @Expose
    private String apkVersion;
    @SerializedName("timerKA")
    @Expose
    private Integer timerKA;
    @SerializedName("banners")
    @Expose
    private List<BannerDetails> banner = null;

    public List<LudoTmtAllTournamentResponse> getResponse() {
        return response;
    }

    public void setResponse(List<LudoTmtAllTournamentResponse> response) {
        this.response = response;
    }

    public ProfileDetails getProfile() {
        return profile;
    }

    public void setProfile(ProfileDetails profile) {
        this.profile = profile;
    }

    public Boolean getLudoEnabled() {
        return isLudoEnabled;
    }

    public void setLudoEnabled(Boolean ludoEnabled) {
        isLudoEnabled = ludoEnabled;
    }

    public String getLudoApkLink() {
        return ludoApkLink;
    }

    public void setLudoApkLink(String ludoApkLink) {
        this.ludoApkLink = ludoApkLink;
    }

    public String getApkVersion() {
        return apkVersion;
    }

    public void setApkVersion(String apkVersion) {
        this.apkVersion = apkVersion;
    }

    public Integer getTimerKA() {
        return timerKA;
    }

    public void setTimerKA(Integer timerKA) {
        this.timerKA = timerKA;
    }

    public List<BannerDetails> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerDetails> banner) {
        this.banner = banner;
    }
}
