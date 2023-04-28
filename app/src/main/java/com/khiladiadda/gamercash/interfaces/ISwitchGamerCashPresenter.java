package com.khiladiadda.gamercash.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;
import com.khiladiadda.network.model.response.gamer_cash.SwitchGamerCashRequest;

public interface ISwitchGamerCashPresenter extends IBasePresenter {

    void getSwitchGamerCashUserData(SwitchGamerCashRequest switchGamerCashRequest);

}