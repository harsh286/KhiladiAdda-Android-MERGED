package com.khiladiadda.network.model.response.droid_doresponse;

import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class TournamentDetailResponse extends BaseResponse {
    @SerializedName("response")
    private TrendingDetailRespo response;

    public TrendingDetailRespo getResponse() {
        return response;
    }

    public void setResponse(TrendingDetailRespo response) {
        this.response = response;
    }
}
