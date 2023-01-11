package com.khiladiadda.league.details.interfaces;

import com.khiladiadda.network.model.response.CreateTeamResponse;
import com.khiladiadda.network.model.response.MyTeamResponse;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.ProfileTransactionResponse;
import com.khiladiadda.network.model.response.StartQuizResponse;

public interface ILeagueDetailsView {

    void onStartLeagueComplete(StartQuizResponse responseModel);

    void onStartLeagueFailure(ApiError error);

    void onCreateTeamComplete(CreateTeamResponse responseModel);

    void onCreateTeamFailure(ApiError error);

    void onMyTeamComplete(MyTeamResponse responseModel);

    void onMyTeamFailure(ApiError error);

    void onProfileComplete(ProfileTransactionResponse responseModel);

    void onProfileFailure(ApiError error);
}
