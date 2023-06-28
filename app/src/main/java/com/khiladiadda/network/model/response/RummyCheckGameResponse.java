package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class RummyCheckGameResponse extends BaseResponse{

    @SerializedName("response")
    @Expose
    private int response;
    public int getResponse() {
        return response;
    }
    public void setResponse(int response) {
        this.response = response;
    }


}
