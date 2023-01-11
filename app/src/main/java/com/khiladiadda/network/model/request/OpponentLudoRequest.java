package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpponentLudoRequest {

    @SerializedName("opponent_ludo_id") @Expose private String opponentLudoId;

    public OpponentLudoRequest(String opponentLudoId) {
        this.opponentLudoId = opponentLudoId;
    }

    public String getOpponentLudoId() {
        return opponentLudoId;
    }

    public void setOpponentLudoId(String opponentLudoId) {
        this.opponentLudoId = opponentLudoId;
    }
}