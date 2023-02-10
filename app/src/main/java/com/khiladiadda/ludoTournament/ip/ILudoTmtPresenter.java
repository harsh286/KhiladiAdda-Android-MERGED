package com.khiladiadda.ludoTournament.ip;

import com.khiladiadda.base.interfaces.IBasePresenter;
import com.khiladiadda.network.model.request.ludoTournament.LudoTournamentFetchRequest;

public interface ILudoTmtPresenter extends IBasePresenter {

    //Get All Ludo Tournament
    void getAllTournament(boolean startDate, int type);

    //Get All Ludo Tournament
    void getPastTournament();

    //Get All Ludo Tournament
    void getLiveTournament();

    //Get All Ludo Tournament
    void getUpcomingTournament();
}
