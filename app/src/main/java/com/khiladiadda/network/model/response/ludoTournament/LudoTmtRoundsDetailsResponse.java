package com.khiladiadda.network.model.response.ludoTournament;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LudoTmtRoundsDetailsResponse {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("userFirstInfo")
    @Expose
    private LudoTmtUserFirstInfoResponse userFirstInfo;
    @SerializedName("userSecondInfo")
    @Expose
    private LudoTmtUserSecondInfoResponse userSecondInfo;
    @SerializedName("userFirst")
    @Expose
    private String userFirst;
    @SerializedName("userSecond")
    @Expose
    private String userSecond;
    @SerializedName("roomStatus")
    @Expose
    private Integer roomStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LudoTmtUserFirstInfoResponse getUserFirstInfo() {
        return userFirstInfo;
    }

    public void setUserFirstInfo(LudoTmtUserFirstInfoResponse userFirstInfo) {
        this.userFirstInfo = userFirstInfo;
    }

    public LudoTmtUserSecondInfoResponse getUserSecondInfo() {
        return userSecondInfo;
    }

    public void setUserSecondInfo(LudoTmtUserSecondInfoResponse userSecondInfo) {
        this.userSecondInfo = userSecondInfo;
    }

    public String getUserFirst() {
        return userFirst;
    }

    public void setUserFirst(String userFirst) {
        this.userFirst = userFirst;
    }

    public String getUserSecond() {
        return userSecond;
    }

    public void setUserSecond(String userSecond) {
        this.userSecond = userSecond;
    }

    public Integer getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(Integer roomStatus) {
        this.roomStatus = roomStatus;
    }
}
