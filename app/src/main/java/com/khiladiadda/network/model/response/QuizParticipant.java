package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizParticipant {

    @SerializedName("user_id") @Expose private String userId;
    @SerializedName("name") @Expose private String name;
    @SerializedName("dp") @Expose private String dp;
    @SerializedName("email") @Expose private String email;
    @SerializedName("n_quiz") @Expose private NQuiz nQuiz;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public NQuiz getNQuiz() {
        return nQuiz;
    }

    public void setNQuiz(NQuiz nQuiz) {
        this.nQuiz = nQuiz;
    }
}
