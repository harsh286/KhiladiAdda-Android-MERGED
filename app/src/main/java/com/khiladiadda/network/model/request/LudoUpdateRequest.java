package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LudoUpdateRequest {
    @SerializedName("room_id") @Expose private String roomId;

    public LudoUpdateRequest(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }
}
