package com.khiladiadda.clashx2.main.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;

public interface IHTHBattlePresenter extends IBasePresenter {

    void getHTHMatchList(String id, int type);

    void onCancelBattle(String id);

    void getBattleResult(String id);

    void getMatchStatus(String id);

}