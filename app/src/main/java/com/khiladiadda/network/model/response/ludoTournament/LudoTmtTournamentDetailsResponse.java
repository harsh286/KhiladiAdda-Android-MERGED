package com.khiladiadda.network.model.response.ludoTournament;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LudoTmtTournamentDetailsResponse {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("tStatus")
    @Expose
    private Integer tStatus;
    @SerializedName("is_out")
    @Expose
    private Boolean isOut;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer gettStatus() {
        return tStatus;
    }

    public void settStatus(Integer tStatus) {
        this.tStatus = tStatus;
    }

    public Boolean getOut() {
        return isOut;
    }

    public void setOut(Boolean out) {
        isOut = out;
    }
}