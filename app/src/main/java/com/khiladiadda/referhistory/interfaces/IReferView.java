package com.khiladiadda.referhistory.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.ReferResponse;

public interface IReferView {

    void onReferComplete(ReferResponse responseModel);

    void onReferFailure(ApiError error);

}