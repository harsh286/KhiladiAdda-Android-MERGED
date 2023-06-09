package com.khiladiadda.callbreak.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.CallBreakJoinMainResponse;
import com.khiladiadda.network.model.response.CallBreakResponse;
import com.khiladiadda.network.model.response.CbHistoryRankMainResponse;

public interface ICallBreakView {

    void onGetContestSuccess(CallBreakResponse responseModel);

    void onGetContestFailure(ApiError error);

    void onGetContestJoinSuccess(CallBreakJoinMainResponse responseModel);

    void onGetContestJoinFailure(ApiError error);

    void onGetHistorySuccess(CallBreakResponse responseModel);

    void onGetHistoryFailure(ApiError error);

    void onGetHistoryRankSuccess(CbHistoryRankMainResponse responseModel);

    void onGetHistoryRankFailure(ApiError error);

}