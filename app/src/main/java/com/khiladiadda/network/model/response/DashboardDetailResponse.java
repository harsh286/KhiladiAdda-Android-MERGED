package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardDetailResponse {

    @SerializedName("fav") @Expose private List<Integer> favourites = null;

    public List<Integer> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<Integer> favourites) {
        this.favourites = favourites;
    }

    @SerializedName("topUsers") @Expose private List<TopUsersDetails> topUsers = null;

    public List<TopUsersDetails> getTopUsers() {
        return topUsers;
    }

    public void setTopUsers(List<TopUsersDetails> topUsers) {
        this.topUsers = topUsers;
    }

    @SerializedName("banners") @Expose private List<BannerDetails> banners = null;

    public List<BannerDetails> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerDetails> banners) {
        this.banners = banners;
    }

    @SerializedName("trendingQuiz") @Expose private List<QuizListDetails> trendingQuiz = null;

    public List<QuizListDetails> getTrendingQuiz() {
        return trendingQuiz;
    }

    public void setTrendingQuiz(List<QuizListDetails> trendingQuiz) {
        this.trendingQuiz = trendingQuiz;
    }

    @SerializedName("dialogue_banner")
    @Expose
    private NewBannerReposne dialogueBanner;

    public NewBannerReposne getDialogueBanner() {
        return dialogueBanner;
    }

    public void setDialogueBanner(NewBannerReposne dialogueBanner) {
        this.dialogueBanner = dialogueBanner;
    }

    @SerializedName("notification_diasbled")
    @Expose
    private boolean isNotificationDisabled;

    public boolean isNotificationDisabled() {
        return isNotificationDisabled;
    }

    public void setNotificationDisabled(boolean notificationDisabled) {
        isNotificationDisabled = notificationDisabled;
    }

}