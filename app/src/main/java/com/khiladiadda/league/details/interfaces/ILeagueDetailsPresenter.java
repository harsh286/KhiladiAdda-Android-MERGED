package com.khiladiadda.league.details.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;
import com.khiladiadda.network.model.request.AddCredential;

public interface ILeagueDetailsPresenter extends IBasePresenter {

    void startLeague(AddCredential addCredential, String gameId);

    void createTeam(AddCredential addCredential, String gameId);

    void getMyTeam(String gameId);

    void getProfile();

}