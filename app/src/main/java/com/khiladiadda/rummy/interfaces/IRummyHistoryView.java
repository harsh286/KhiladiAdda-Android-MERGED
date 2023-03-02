package com.khiladiadda.rummy.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.RummyHistoryMainResponse;

public interface IRummyHistoryView {
    void onGetRummyHistorySuccess(RummyHistoryMainResponse responseModel);

    void onGetRummyHistoryFailure(ApiError error);
}
