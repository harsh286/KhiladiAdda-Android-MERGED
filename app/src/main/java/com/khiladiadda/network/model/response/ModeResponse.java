package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class ModeResponse extends BaseResponse {

    @SerializedName("response")
    @Expose
    private ModeData response;

    public ModeData getResponse() {
        return response;
    }

    public void setResponse(ModeData response) {
        this.response = response;
    }

    @SerializedName("metaInfo")
    @Expose
    private LudoMetaInfo metaInfo;

    public LudoMetaInfo getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(LudoMetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }
}
