package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class RummyHistoryMainResponse extends BaseResponse {
    @SerializedName("response")
    @Expose
    private List<RummyHistoryResponse> response;

    public List<RummyHistoryResponse> getResponse() {
        return response;
    }

    public void setResponse(List<RummyHistoryResponse> response) {
        this.response = response;
    }
}
