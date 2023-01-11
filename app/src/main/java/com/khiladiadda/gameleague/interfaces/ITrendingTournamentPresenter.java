package com.khiladiadda.gameleague.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;

public interface ITrendingTournamentPresenter extends IBasePresenter {

    void getTrendingTournamentData();

    void getMyTournamentData();

    void getDataFilter(int v);

}
