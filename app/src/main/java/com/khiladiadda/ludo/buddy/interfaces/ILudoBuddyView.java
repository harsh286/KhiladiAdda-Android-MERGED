package com.khiladiadda.ludo.buddy.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.BuddyResponse;

public interface ILudoBuddyView {

    void onBuddyListSuccess(BuddyResponse responseModel);

    void onBuddyListFailure(ApiError error);

    void onChallengeSuccess(BaseResponse response);

    void onChallengeFailure(ApiError error);

    void onLUBuddyListSuccess(BuddyResponse responseModel);

    void onLUBuddyListFailure(ApiError error);

}