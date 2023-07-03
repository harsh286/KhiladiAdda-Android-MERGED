package com.khiladiadda.network.model.response.droid_doresponse;

import com.google.gson.annotations.SerializedName;

public class ResponseItem {
    @SerializedName("user_info")
    private userInfoParticipants participants;
    public ResponseItem(userInfoParticipants participants) {
        this.participants = participants;
    }
    public userInfoParticipants getParticipants() {
        return participants;
    }
    public void setParticipants(userInfoParticipants participants) {
        this.participants = participants;
    }
}
