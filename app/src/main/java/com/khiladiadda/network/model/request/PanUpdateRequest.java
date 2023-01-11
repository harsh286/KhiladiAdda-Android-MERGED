package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PanUpdateRequest {

    @SerializedName("pan_info") @Expose private PanInfo panInfo;

    public PanInfo getPanInfo() {
        return panInfo;
    }

    public void setPanInfo(PanInfo panInfo) {
        this.panInfo = panInfo;
    }

    public PanUpdateRequest(PanInfo panInfo) {
        this.panInfo = panInfo;
    }
}