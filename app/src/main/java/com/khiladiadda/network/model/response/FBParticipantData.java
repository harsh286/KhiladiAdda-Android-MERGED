package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FBParticipantData {

    @SerializedName("username") @Expose private String username;
    @SerializedName("name") @Expose private String name;
    @SerializedName("dp") @Expose private String dp;

    public String getUserId() {
        return username;
    }

    public void setUserId(String userId) {
        this.username = userId;
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

}
