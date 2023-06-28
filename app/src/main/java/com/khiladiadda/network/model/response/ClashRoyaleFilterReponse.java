package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class ClashRoyaleFilterReponse extends BaseResponse {

    @SerializedName("response")
    @Expose public Active response = null;

    public Active getResponse() {
        return response;
    }

    public void setResponse(Active response) {
        this.response = response;
    }
}