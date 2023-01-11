package com.khiladiadda.network.model.response.hth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.ProfileDetails;

public class CreateBattleResponse extends BaseResponse {
    @SerializedName("user") @Expose private ProfileDetails profile;
    public ProfileDetails getProfile() {
        return profile;
    }

    public void setProfile(ProfileDetails profile) {
        this.profile = profile;
    }
}
