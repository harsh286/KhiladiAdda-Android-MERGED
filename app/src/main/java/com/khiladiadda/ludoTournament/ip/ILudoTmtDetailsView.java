package com.khiladiadda.ludoTournament.ip;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtJoinMainResponse;

public interface ILudoTmtDetailsView {
    void onJoinLudoTournamentComplete(LudoTmtJoinMainResponse response);

    void onJoinLudoTournamentFailure(ApiError errorMsg);
}
