package com.khiladiadda.scratchcard.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.ScratchCardResponse;

public interface IScratchView {
    void onScratchCardComplete(ScratchCardResponse responseModel);

    void onScratchCardFailure(ApiError error);

    void onScratchedCardComplete(BaseResponse responseModel);

    void onScratchedCardFailure(ApiError error);
}
