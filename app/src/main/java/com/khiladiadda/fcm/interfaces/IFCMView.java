package com.khiladiadda.fcm.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.CricApiError;
import com.khiladiadda.network.model.response.CricScorce;
import com.khiladiadda.network.model.response.MatchResponse;

public interface IFCMView {

    void onFcmUpdateComplete(BaseResponse responseModel);

    void onFcmUpdateFailure(ApiError error);

}