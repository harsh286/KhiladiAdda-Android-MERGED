package com.khiladiadda.gamercash.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.gamer_cash.GetGamerCashResponse;

public interface IGetGamerCashView {

    void onGetGamerCashSuccess(GetGamerCashResponse response);

    void onGetGamerCashFailure(ApiError error);


}
