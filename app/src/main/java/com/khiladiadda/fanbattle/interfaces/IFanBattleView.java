package com.khiladiadda.fanbattle.interfaces;

import com.khiladiadda.network.model.response.MasterResponse;
import com.khiladiadda.network.model.response.MatchResponse;
import com.khiladiadda.network.model.ApiError;

public interface IFanBattleView {

    void onGetMatchListComplete(MatchResponse responseModel);

    void onGetMatchListFailure(ApiError error);

    void onMasterComplete(MasterResponse responseModel);

    void onMasterFailure(ApiError error);

}