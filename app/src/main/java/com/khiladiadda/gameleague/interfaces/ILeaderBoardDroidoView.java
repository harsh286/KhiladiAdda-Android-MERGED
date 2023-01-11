package com.khiladiadda.gameleague.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.droid_doresponse.LeaderBoardDroidoResponse;

public interface ILeaderBoardDroidoView {

    void onParticipantsSuccess(LeaderBoardDroidoResponse response);

    void onParticipantsFailure(ApiError error);

}
