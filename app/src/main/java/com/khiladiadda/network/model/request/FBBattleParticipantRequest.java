package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FBBattleParticipantRequest {
    @SerializedName("groupId")
    @Expose
    private String groupId;
    @SerializedName("battleId")
    @Expose
    private String battleId;
    @SerializedName("matchId")
    @Expose
    private String matchid;

    public String getGroupId() {
        return groupId;
    }

    public boolean isIsleaderboard() {
        return isleaderboard;
    }

    public void setIsleaderboard(boolean isleaderboard) {
        this.isleaderboard = isleaderboard;
    }

    @SerializedName("is_leaderboard")
    @Expose boolean isleaderboard;

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getBattleId() {
        return battleId;
    }

    public void setBattleId(String battleId) {
        this.battleId = battleId;
    }

    public String getMatchid() {
        return matchid;
    }

    public void setMatchid(String matchid) {
        this.matchid = matchid;
    }
}
