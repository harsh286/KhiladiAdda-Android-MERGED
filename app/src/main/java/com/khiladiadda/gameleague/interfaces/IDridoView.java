package com.khiladiadda.gameleague.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.droid_doresponse.ItemGamesMainResponse;

public interface IDridoView {

    void onGameListFailure(ApiError error);

    void onGameListSuccess(ItemGamesMainResponse response);

}
