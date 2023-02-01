package com.khiladiadda.network.model.response.ludoTournament;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LudoTmtAllPastRoundsResponse {

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
    @SerializedName("is_won")
    @Expose
    private Boolean isWon;
    @SerializedName("level")
    @Expose
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

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

    public Boolean getWon() {
        return isWon;
    }

    public void setWon(Boolean won) {
        isWon = won;
    }
}
