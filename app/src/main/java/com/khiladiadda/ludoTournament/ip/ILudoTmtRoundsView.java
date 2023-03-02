package com.khiladiadda.ludoTournament.ip;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtRoundsDetailsMainResponse;

public interface ILudoTmtRoundsView {
    void onGetRoundsTournamentComplete(LudoTmtRoundsDetailsMainResponse response);

    void onGetRoundsTournamentFailure(ApiError errorMsg);
}
