package com.khiladiadda.league.interfaces;


import com.khiladiadda.base.interfaces.IBasePresenter;

public interface ILeagueListPresenter extends IBasePresenter {

    void getGameDetails(String catId,int type,String gameId);

}