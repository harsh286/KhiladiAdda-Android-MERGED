package com.khiladiadda.clashroyale.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;

public interface IClashRoyalePresenter extends IBasePresenter {

    void getFilterList();

    void getLeagueList(String catId,int type,String gameId);

}