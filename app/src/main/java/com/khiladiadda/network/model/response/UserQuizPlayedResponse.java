package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class UserQuizPlayedResponse extends BaseResponse {

    @SerializedName("profile") @Expose private ProfileDetails profile;

    public ProfileDetails getProfile() {
        return profile;
    }

    public void setProfile(ProfileDetails profile) {
        this.profile = profile;
    }

    @SerializedName("is_played") @Expose private boolean isPlayed;

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean played) {
        isPlayed = played;
    }

    @SerializedName("response") @Expose private QuizListDetails response = null;

    public QuizListDetails getResponse() {
        return response;
    }

    public void setResponse(QuizListDetails response) {
        this.response = response;
    }

}