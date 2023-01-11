package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class HTHCancelResponse extends BaseResponse {

    @SerializedName("profile") @Expose private ProfileDetails profile;

    public ProfileDetails getProfile() {
        return profile;
    }

    public void setProfile(ProfileDetails profile) {
        this.profile = profile;
    }

}