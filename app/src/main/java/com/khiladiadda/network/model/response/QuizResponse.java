package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuizResponse {

    @SerializedName("past") @Expose private List<QuizListDetails> past = null;
    @SerializedName("live") @Expose private List<QuizListDetails> live = null;
    @SerializedName("upcoming") @Expose private List<QuizListDetails> upcoming = null;

    public List<QuizListDetails> getPast() {
        return past;
    }

    public void setPast(List<QuizListDetails> past) {
        this.past = past;
    }

    public List<QuizListDetails> getLive() {
        return live;
    }

    public void setLive(List<QuizListDetails> live) {
        this.live = live;
    }

    public List<QuizListDetails> getUpcoming() {
        return upcoming;
    }

    public void setUpcoming(List<QuizListDetails> upcoming) {
        this.upcoming = upcoming;
    }

}
