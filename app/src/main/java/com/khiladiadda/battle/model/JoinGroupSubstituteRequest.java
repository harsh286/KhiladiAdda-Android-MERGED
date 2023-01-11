package com.khiladiadda.battle.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JoinGroupSubstituteRequest {

    @SerializedName("oldGroup") @Expose private String oldGroup;
    @SerializedName("newGroup") @Expose private String newGroup;

    public String getOldGroup() {
        return oldGroup;
    }

    public void setOldGroup(String oldGroup) {
        this.oldGroup = oldGroup;
    }

    public String getNewGroup() {
        return newGroup;
    }

    public void setNewGroup(String newGroup) {
        this.newGroup = newGroup;
    }
}
