package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class DashboardResponse extends BaseResponse {

    @SerializedName("response")
    @Expose
    private DashboardDetailResponse response = null;

    public DashboardDetailResponse getResponse() {
        return response;
    }

    public void setVersion(DashboardDetailResponse version) {
        this.response = version;
    }

}
