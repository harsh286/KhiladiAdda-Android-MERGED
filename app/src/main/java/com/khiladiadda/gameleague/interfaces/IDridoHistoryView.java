package com.khiladiadda.gameleague.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.droid_doresponse.DroidoHistoryGameList;

public interface IDridoHistoryView {

    void onDroidoHistoryListFailure(ApiError error);

    void onDroidoHistoryListSuccess(DroidoHistoryGameList response);

}
