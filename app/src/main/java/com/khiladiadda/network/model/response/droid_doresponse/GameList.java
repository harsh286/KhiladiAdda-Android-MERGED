package com.khiladiadda.network.model.response.droid_doresponse;

import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

public class GameList extends BaseResponse {
        @SerializedName("game_type")
        public int gameType;
        @SerializedName("game_link")
        public String gameLink;
        @SerializedName("_id")
        public String id;
        @SerializedName("name")
        public String name;
        @SerializedName("image")
        public String image;

        public GameList(int gameType, String gameLink, String id, String name, String image) {
            this.gameType = gameType;
            this.gameLink = gameLink;
            this.id = id;
            this.name = name;
            this.image = image;
        }

        public int getGameType() {
            return gameType;
        }

        public void setGameType(int gameType) {
            this.gameType = gameType;
        }

        public String getGameLink() {
            return gameLink;
        }

        public void setGameLink(String gameLink) {
            this.gameLink = gameLink;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }