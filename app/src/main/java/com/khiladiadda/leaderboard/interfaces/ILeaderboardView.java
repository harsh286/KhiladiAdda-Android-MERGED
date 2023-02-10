package com.khiladiadda.leaderboard.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.AllLeaderBoardResponse;
import com.khiladiadda.network.model.response.LeaderboardMainResponse;
import com.khiladiadda.network.model.response.LudoAddaMainResponse;
import com.khiladiadda.network.model.response.LudoLeaderboardResponse;
import com.khiladiadda.network.model.response.OverallLeadBoardResponse;
import com.khiladiadda.network.model.response.hth.LeaderBoardHthResponse;

public interface ILeaderboardView {

    void onLeaderBoardComplete(AllLeaderBoardResponse responseModel);

    void onLeaderBoardFailure(ApiError error);

    void onLudoLeaderBoardComplete(LudoLeaderboardResponse responseModel);

    void onLudoLeaderBoardFailure(ApiError error);

    void onLeaderFanBattleComplete(OverallLeadBoardResponse responseModel);

    void onLeaderFanBattleFailure(ApiError error);

    void onLeaderHTHBattleComplete(LeaderBoardHthResponse responseModel);

    void onLeaderHTHBattleFailure(ApiError error);

    void onLeaderLudoAddaComplete(LudoAddaMainResponse responseModel);

    void onLeaderLudoAdda(ApiError error);

    void onLeaderWSComplete(AllLeaderBoardResponse responseModel);

    void onLeaderWS(ApiError error);

    void onLeaderDroidComplete(AllLeaderBoardResponse responseModel);

    void onLeaderDroidError(ApiError error);

    void onAllLeaderBoardComplete(LeaderboardMainResponse responseModel);

    void onAllLeaderBoardError(ApiError error);

}