package com.khiladiadda.clashroyale.interfaces;

import com.khiladiadda.network.model.response.ClashRoyaleFilterReponse;
import com.khiladiadda.network.model.response.LeagueListReponse;
import com.khiladiadda.network.model.ApiError;

public interface IClashRoyaleView {

    void onFilterComplete(ClashRoyaleFilterReponse responseModel);

    void onFilterFailure(ApiError error);

    void onLeagueComplete(LeagueListReponse responseModel);

    void onLeagueFailure(ApiError error);

}