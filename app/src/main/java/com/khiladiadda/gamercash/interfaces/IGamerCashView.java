package com.khiladiadda.gamercash.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.gamer_cash.GamerCashResponse;

public interface IGamerCashView {

    void onGamerCashSuccess(GamerCashResponse response);

    void onGamerCashFailure(ApiError error);


}
