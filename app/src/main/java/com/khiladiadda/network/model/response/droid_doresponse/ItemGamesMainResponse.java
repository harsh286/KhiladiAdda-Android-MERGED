package com.khiladiadda.network.model.response.droid_doresponse;

import com.google.gson.annotations.SerializedName;
import com.khiladiadda.network.model.BaseResponse;

import java.util.List;

public class ItemGamesMainResponse extends BaseResponse {
    @SerializedName("response")
    public List<GameList> gameListList;
    public ItemGamesMainResponse(List<GameList> gameListList) {
        this.gameListList = gameListList;
    }

    public List<GameList> getGameListList() {return gameListList;
    }

    public void setGameListList(List<GameList> gameListList) {
        this.gameListList = gameListList;
    }

    public void setResponseList(List<GameList> gameListList) {
        this.gameListList = gameListList;
    }


}
