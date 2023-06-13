package com.khiladiadda.rummy.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;
import com.khiladiadda.network.model.request.LudoContestRequest;
import com.khiladiadda.network.model.request.OpponentLudoRequest;

public interface IRummyPresenter extends IBasePresenter {

    void getRummyList(String arenaType);
    void getRummyRefreshToken();
    void getCheckGameStatus(String cardId);
}