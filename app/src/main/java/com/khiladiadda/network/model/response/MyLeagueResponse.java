package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class MyLeagueResponse extends BaseResponse {

    @SerializedName("response") @Expose public MyLeagueDetails response;

    public MyLeagueDetails getResponse() {
        return response;
    }

    public void setResponse(MyLeagueDetails response) {
        this.response = response;
    }
}