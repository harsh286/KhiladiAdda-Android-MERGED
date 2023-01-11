package com.khiladiadda.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GameCredential {

    @SerializedName("_id") @Expose private String id;
    @SerializedName("game_id") @Expose private String gameId;
    @SerializedName("game_character_id") @Expose private String gameCharacterId;
    @SerializedName("game_username") @Expose private String gameUsername;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameCharacterId() {
        return gameCharacterId;
    }

    public void setGameCharacterId(String gameCharacterId) {
        this.gameCharacterId = gameCharacterId;
    }

    public String getGameUsername() {
        return gameUsername;
    }

    public void setGameUsername(String gameUsername) {
        this.gameUsername = gameUsername;
    }

}
