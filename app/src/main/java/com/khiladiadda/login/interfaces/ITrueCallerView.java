package com.khiladiadda.login.interfaces;

import com.khiladiadda.login.TrueCallerResponse;
import com.khiladiadda.network.model.ApiError;

public interface ITrueCallerView {
    void onTrueCallerCompletion(TrueCallerResponse response);

    void onTrueCallerFailure(ApiError errorMsg);
}
