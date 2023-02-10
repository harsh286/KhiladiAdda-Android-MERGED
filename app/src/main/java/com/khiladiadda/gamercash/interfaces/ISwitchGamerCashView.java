package com.khiladiadda.gamercash.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.gamer_cash.SwitchGamerCashResponse;

public interface ISwitchGamerCashView {

    void onSwitchGamerCashSuccess(SwitchGamerCashResponse response);

    void onSwitchGamerCashFailure(ApiError error);


}
