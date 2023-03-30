package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class LudoContestResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private List<LudoContest> response = null;
    @SerializedName("my_contests")
    @Expose
    private List<LudoContest> myContests = null;
    @SerializedName("banners")
    @Expose
    private List<BannerDetails> banner = null;
    @SerializedName("profile")
    @Expose
    private ProfileDetails profile;
    @SerializedName("is_popular_enabled")
    @Expose
    private boolean is_popular_enabled;
    @SerializedName("isLudoEnabled")
    @Expose
    private boolean isLudoEnabled;
    @SerializedName("ludo_apk_link")
    private String LudoApkLink;
    @SerializedName("apk_version")
    private String apk_version;
    @SerializedName("auto_roomcode_enabled")
    private boolean auto_roomcode_enabled;

    public boolean isAuto_roomcode_enabled() {
        return auto_roomcode_enabled;
    }

    public void setAuto_roomcode_enabled(boolean auto_roomcode_enabled) {
        this.auto_roomcode_enabled = auto_roomcode_enabled;
    }

    public List<LudoContest> getResponse() {
        return response;
    }

    public void setResponse(List<LudoContest> response) {
        this.response = response;
    }

    public List<LudoContest> getMyContests() {
        return myContests;
    }

    public void setMyContests(List<LudoContest> myContests) {
        this.myContests = myContests;
    }

    public List<BannerDetails> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerDetails> banner) {
        this.banner = banner;
    }

    public ProfileDetails getProfile() {
        return profile;
    }

    public void setProfile(ProfileDetails profile) {
        this.profile = profile;
    }

    public boolean isIs_popular_enabled() {
        return is_popular_enabled;
    }

    public void setIs_popular_enabled(boolean is_popular_enabled) {
        this.is_popular_enabled = is_popular_enabled;
    }

    public boolean isLudoEnabled() {
        return isLudoEnabled;
    }

    public void setLudoEnabled(boolean ludoEnabled) {
        isLudoEnabled = ludoEnabled;
    }

    public String getLudoApkLink() {
        return LudoApkLink;
    }

    public void setLudoApkLink(String ludoApkLink) {
        LudoApkLink = ludoApkLink;
    }

    public String getApk_version() {
        return apk_version;
    }

    public void setApk_version(String apk_version) {
        this.apk_version = apk_version;
    }

}