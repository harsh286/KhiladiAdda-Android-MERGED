package com.khiladiadda.fanleague.interfaces;

import com.khiladiadda.network.model.response.CricScorce;
import com.khiladiadda.network.model.response.MatchResponse;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.CricApiError;

public interface IMyFanLeagueView {

    void onMyFanLeagueComplete(MatchResponse responseModel);

    void onMyFanLeagueFailure(ApiError error);

    void onLiveScoreComplete(CricScorce cricScorce);

    void onLiveScoreFailure(CricApiError error);

}