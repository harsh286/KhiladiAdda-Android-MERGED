package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LudoUpdateRequest {
    @SerializedName("room_id") @Expose
    private String roomId;
    @SerializedName("room_code_copy")
    @Expose
    private boolean isRoomCodeCopy;

    public LudoUpdateRequest(String roomId,boolean isRoomCodeCopy) {
        this.roomId = roomId;
        this.isRoomCodeCopy=isRoomCodeCopy;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isRoomCodeCopy() {
        return isRoomCodeCopy;
    }
    public void setRoomCodeCopy(boolean roomCodeCopy) {
        isRoomCodeCopy = roomCodeCopy;
    }
}
