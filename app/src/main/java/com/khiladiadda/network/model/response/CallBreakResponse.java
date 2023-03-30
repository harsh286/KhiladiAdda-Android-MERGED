package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class CallBreakResponse extends BaseResponse {

    @SerializedName("response")
    @Expose
    private List<CallBreakDetails> response;
    @SerializedName("banners")
    @Expose
    private List<BannerDetails> banner = null;
    @SerializedName("isLudoEnabled")
    @Expose
    private Boolean isLudoEnabled;
    @SerializedName("callbreak_apk_link")
    @Expose
    private String callbreakApkLink;
    @SerializedName("apk_version")
    @Expose
    private String apkVersion;
    @SerializedName("timerKA")
    @Expose
    private Long timerKA;


    public List<CallBreakDetails> getResponse() {
        return response;
    }

    public void setResponse(List<CallBreakDetails> response) {
        this.response = response;
    }

    public List<BannerDetails> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerDetails> banner) {
        this.banner = banner;
    }

    public Boolean getLudoEnabled() {
        return isLudoEnabled;
    }

    public void setLudoEnabled(Boolean ludoEnabled) {
        isLudoEnabled = ludoEnabled;
    }

    public String getCallbreakApkLink() {
        return callbreakApkLink;
    }

    public void setCallbreakApkLink(String callbreakApkLink) {
        this.callbreakApkLink = callbreakApkLink;
    }

    public String getApkVersion() {
        return apkVersion;
    }

    public void setApkVersion(String apkVersion) {
        this.apkVersion = apkVersion;
    }

    public Long getTimerKA() {
        return timerKA;
    }

    public void setTimerKA(Long timerKA) {
        this.timerKA = timerKA;
    }
}