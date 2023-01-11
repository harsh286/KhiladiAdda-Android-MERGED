package com.khiladiadda.league.interfaces;


import com.khiladiadda.network.model.response.LeagueListReponse;
import com.khiladiadda.network.model.ApiError;

public interface ILeagueListView {

    void onGameComplete(LeagueListReponse responseModel);

    void onGameFailure(ApiError error);


}
