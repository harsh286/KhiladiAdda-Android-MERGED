package com.khiladiadda.league.myleague.interfaces;


import com.khiladiadda.base.interfaces.IBasePresenter;

public interface IMyLeaguePresenter extends IBasePresenter {

    void getMyLeague(String id, boolean upcoming, boolean past, boolean live);

    void getMyTeam(String gameId);

}
