package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class CxBannerMainResponse extends BaseResponse {

    @SerializedName("response")
    @Expose
    private List<BannerDetails> response = null;

    public List<BannerDetails> getResponse() {
        return response;
    }

    public void setResponse(List<BannerDetails> response) {
        this.response = response;
    }
}
