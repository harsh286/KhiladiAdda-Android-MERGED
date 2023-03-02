package com.khiladiadda.gamercash.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.GamerCashResponse;

public interface IGamerCashView {

    void onGamerCashSuccess(GamerCashResponse response);

    void onGamerCashFailure(ApiError error);


}
