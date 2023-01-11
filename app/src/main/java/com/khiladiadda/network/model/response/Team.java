package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Team {

    @SerializedName("code") @Expose private String code;
    @SerializedName("is_captain") @Expose private boolean isCaptain;
    @SerializedName("name") @Expose private String name;
    @SerializedName("n_participants") @Expose private long nParticipants;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isIsCaptain() {
        return isCaptain;
    }

    public void setIsCaptain(boolean isCaptain) {
        this.isCaptain = isCaptain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNParticipants() {
        return nParticipants;
    }

    public void setNParticipants(long nParticipants) {
        this.nParticipants = nParticipants;
    }
}
