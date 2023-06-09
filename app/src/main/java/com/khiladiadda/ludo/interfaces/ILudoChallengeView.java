package com.khiladiadda.ludo.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.LudoContestResponse;

public interface ILudoChallengeView {

    void onGetContestSuccess(LudoContestResponse responseModel);

    void onGetContestFailure(ApiError error);

    void addChallengeSuccess(BaseResponse response);

    void addChallengeFailure(ApiError error);

    void acceptContestSuccess(BaseResponse response);

    void acceptContestFailure(ApiError error);

    void cancelContestSuccess(BaseResponse response);

    void cancelContestFailure(ApiError error);

}