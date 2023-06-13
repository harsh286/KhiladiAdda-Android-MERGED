package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class RummyHelpResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private RummyHelpDataResponse response;

    public RummyHelpDataResponse getResponse() {
        return response;
    }

    public void setResponse(RummyHelpDataResponse response) {
        this.response = response;
    }
}
