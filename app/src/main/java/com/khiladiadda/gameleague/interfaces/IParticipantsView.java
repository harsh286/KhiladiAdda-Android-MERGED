package com.khiladiadda.gameleague.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.droid_doresponse.GameParticipantsDataResponse;

public interface IParticipantsView {

    void onParticipantsSuccess(GameParticipantsDataResponse response);

    void onParticipantsFailure(ApiError error);
}
