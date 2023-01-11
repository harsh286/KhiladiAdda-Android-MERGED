package com.khiladiadda.fanleague.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;

public interface IMyFanLeaguePresenter extends IBasePresenter {

    void getMyFanLeague(boolean upcoming, boolean past, boolean live);

    void getLiveScore(String api, int matchId);

}