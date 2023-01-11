package com.khiladiadda.battle.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.response.ProfileDetails;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class BattleGroupResponse extends BaseResponse {

    @SerializedName("response") @Expose private List<GroupDetails> response = null;

    public List<GroupDetails> getResponse() {
        return response;
    }

    public void setResponse(List<GroupDetails> response) {
        this.response = response;
    }

    @SerializedName("profile") @Expose private ProfileDetails profileDetails;

    public ProfileDetails getProfileDetails() {
        return profileDetails;
    }

    public void setProfileDetails(ProfileDetails profileDetails) {
        this.profileDetails = profileDetails;
    }

    @SerializedName("battleDetail") @Expose private BattleDetails battleDetail;

    public BattleDetails getBattleDetails() {
        return battleDetail;
    }

    public void setBattleDetails(BattleDetails battleDetail) {
        this.battleDetail = battleDetail;
    }
}