package com.khiladiadda.network.model.response;

import com.google.gson.annotations.SerializedName;

public class LudoMetaInfo {

    @SerializedName("ludo_apk_link")
    private String LudoApkLink;
    @SerializedName("ludoadda_apk_version")
    private String apk_version;

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
