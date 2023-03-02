package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WordSearchLiveLeaderBoardUserResponse {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dp")
    @Expose
    private String dp;
    @SerializedName("user_id")
    @Expose
    private String userId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
