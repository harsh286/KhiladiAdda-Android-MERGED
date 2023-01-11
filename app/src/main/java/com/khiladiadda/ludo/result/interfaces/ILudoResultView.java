package com.khiladiadda.ludo.result.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;

public interface ILudoResultView {

    void cancelLudoContestSuccess(BaseResponse response);

    void cancelLudoContestFailure(ApiError error);

    void onLudoUpdateSuccess(BaseResponse ludoUpdateResponse);

    void onLudoUpdateFailure(ApiError error);

    void onLudoResultSuccess(BaseResponse response);

    void onLudoResultFailure(ApiError error);

}