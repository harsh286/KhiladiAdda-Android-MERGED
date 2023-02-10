package com.khiladiadda.rummy.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.LudoContestResponse;
import com.khiladiadda.network.model.response.ModeResponse;
import com.khiladiadda.network.model.response.RummyRefreshTokenMainResponse;
import com.khiladiadda.network.model.response.RummyResponse;

public interface IRummyView {

    void onGetContestSuccess(RummyResponse responseModel);

    void onGetContestFailure(ApiError error);

    void onGetContestRefreshTokenSuccess(RummyRefreshTokenMainResponse responseModel);

    void onGetContestRefreshTokenFailure(ApiError error);

}