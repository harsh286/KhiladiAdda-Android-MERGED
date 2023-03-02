package com.khiladiadda.ludoTournament.ip;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtMyMatchMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtRoundsDetailsMainResponse;

public interface ILudoTmtMyMatchView {
    void onGetMyMatchTournamentComplete(LudoTmtMyMatchMainResponse response);

    void onGetMyMatchTournamentFailure(ApiError errorMsg);

}
