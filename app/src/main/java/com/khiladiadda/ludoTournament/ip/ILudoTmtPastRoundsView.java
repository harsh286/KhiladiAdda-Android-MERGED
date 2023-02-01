package com.khiladiadda.ludoTournament.ip;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtAllPastRoundsMainResponse;

public interface ILudoTmtPastRoundsView {
    void onGetPastRoundsTournamentComplete(LudoTmtAllPastRoundsMainResponse response);

    void onGetPastRoundsTournamentFailure(ApiError errorMsg);
}
