package com.khiladiadda.clashx2.main.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.HTHCancelResponse;
import com.khiladiadda.network.model.response.hth.HTHMainResponse;
import com.khiladiadda.network.model.response.hth.KaPlayerHTH;
import com.khiladiadda.network.model.response.hth.Result;

public interface IHTHBattleView {
    void onGetHTHMatchListComplete(HTHMainResponse responseModel);

    void onGetHTHMatchListFailure(ApiError error);

    void onCancelBattle(HTHCancelResponse responseModel);

    void onCancelBattleFailure(ApiError error);

    void onGetResultBattle(Result responseModel);

    void onResultBattleFailure(ApiError error);

    void onMatchStatus(HTHMainResponse responseModel);

    void onMatchStatusError(ApiError error);

}
