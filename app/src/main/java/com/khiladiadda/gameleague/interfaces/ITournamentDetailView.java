package com.khiladiadda.gameleague.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.droid_doresponse.TournamentDetailResponse;

public interface ITournamentDetailView {

    void onTournamentDetailSuccess(TournamentDetailResponse response);

    void onTournamentDetailFailure(ApiError error);


}
