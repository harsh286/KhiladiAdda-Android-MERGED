package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MasterDetails {

    @SerializedName("games") @Expose private List<Active> games = null;
    @SerializedName("profile") @Expose private ProfileDetails profile;
    @SerializedName("version") @Expose private VersionDetails version;
    @SerializedName("quizzes_categories") @Expose private List<CategoryList> quizzesCategories = null;

    public List<Active> getGames() {
        return games;
    }

    public void setGames(List<Active> games) {
        this.games = games;
    }

    public ProfileDetails getProfile() {
        return profile;
    }

    public void setProfile(ProfileDetails profile) {
        this.profile = profile;
    }

    public VersionDetails getVersion() {
        return version;
    }

    public void setVersion(VersionDetails version) {
        this.version = version;
    }

    public List<CategoryList> getQuizzesCategories() {
        return quizzesCategories;
    }

    public void setQuizzesCategories(List<CategoryList> quizzesCategories) {
        this.quizzesCategories = quizzesCategories;
    }
}