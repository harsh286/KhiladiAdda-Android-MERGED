package com.khiladiadda.ludoTournament.ip;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtAllTournamentMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtJoinMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtMyMatchMainResponse;

public interface ILudoTmtView {

    void onGetAllTournamentComplete(LudoTmtAllTournamentMainResponse response);

    void onGetAllTournamentFailure(ApiError errorMsg);
}
