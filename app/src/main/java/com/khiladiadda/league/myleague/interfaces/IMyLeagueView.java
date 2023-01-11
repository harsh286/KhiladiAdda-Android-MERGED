package com.khiladiadda.league.myleague.interfaces;


import com.khiladiadda.network.model.response.MyLeagueResponse;
import com.khiladiadda.network.model.response.MyTeamResponse;
import com.khiladiadda.network.model.ApiError;

public interface IMyLeagueView {

    void onMyLeagueComplete(MyLeagueResponse responseModel);

    void onMyLeagueFailure(ApiError error);

    void onMyTeamComplete(MyTeamResponse responseModel);

    void onMyTeamFailure(ApiError error);


}
