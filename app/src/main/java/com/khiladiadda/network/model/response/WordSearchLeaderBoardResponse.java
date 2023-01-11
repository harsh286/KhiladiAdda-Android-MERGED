package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WordSearchLeaderBoardResponse {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dp")
    @Expose
    private String dp;
    @SerializedName("n_quiz")
    @Expose
    private WordSearchNQuizResponse nQuiz;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public WordSearchNQuizResponse getnQuiz() {
        return nQuiz;
    }

    public void setnQuiz(WordSearchNQuizResponse nQuiz) {
        this.nQuiz = nQuiz;
    }
}
