package com.khiladiadda.gameleague.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.droid_doresponse.MyTournamentResponse;
import com.khiladiadda.network.model.response.droid_doresponse.TrendingTournamentResponse;

public interface ITrendingTournamentView {

    void onGameTrendingTournamentSuccess(TrendingTournamentResponse response);

    void onGameTrendingTournamentFailure(ApiError error);

    void onMyTournamentSuccess(MyTournamentResponse response);

    void onMyTournamentFailure(ApiError error);

    void getFiltersTournamentSuccess(TrendingTournamentResponse response);

    void getFiltersTournamentFailed(ApiError error);

}
