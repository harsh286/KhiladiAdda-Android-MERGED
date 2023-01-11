package com.khiladiadda.network.model.response.droid_doresponse;

import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class TrendingTournamentResponse extends BaseResponse {

    @SerializedName("response")
    private ResponseData responseData;

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }
}
