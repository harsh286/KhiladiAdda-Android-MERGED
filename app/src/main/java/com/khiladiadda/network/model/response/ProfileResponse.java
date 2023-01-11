package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class ProfileResponse extends BaseResponse {

    @SerializedName("response") @Expose private ProfileDetails response;

    public ProfileDetails getResponse() {
        return response;
    }

    public void setResponse(ProfileDetails response) {
        this.response = response;
    }
}