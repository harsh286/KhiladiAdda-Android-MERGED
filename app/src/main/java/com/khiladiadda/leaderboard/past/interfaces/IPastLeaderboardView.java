package com.khiladiadda.leaderboard.past.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.SquadLeaderbordResponse;

public interface IPastLeaderboardView {

    void onPastLeaderBoardComplete(SquadLeaderbordResponse responseModel);

    void onPastLeaderBoardFailure(ApiError error);

}
