package com.khiladiadda.network.model.response.droid_doresponse;

import com.google.gson.annotations.SerializedName;

public class userInfoParticipants {
    @SerializedName("name")
    public String name;

    public userInfoParticipants(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
