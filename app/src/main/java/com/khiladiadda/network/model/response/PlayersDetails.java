package com.khiladiadda.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayersDetails {
    @SerializedName("nPlayers")
    @Expose
    private Integer nPlayers;
    @SerializedName("maxWin")
    @Expose
    private Integer maxWin;
    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("cardId")
    @Expose
    private String cardId;

    public Integer getnPlayers() {
        return nPlayers;
    }

    public void setnPlayers(Integer nPlayers) {
        this.nPlayers = nPlayers;
    }

    public Integer getMaxWin() {
        return maxWin;
    }

    public void setMaxWin(Integer maxWin) {
        this.maxWin = maxWin;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
