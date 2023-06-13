package com.khiladiadda.rummy.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.RummyHelpResponse;
import com.khiladiadda.network.model.response.RummyHistoryMainResponse;

public interface IRummyHelpRuleView {
    void onGetRummyHelpRuleSuccess(RummyHelpResponse responseModel);

    void onGetRummyHelpRuleFailure(ApiError error);
}
