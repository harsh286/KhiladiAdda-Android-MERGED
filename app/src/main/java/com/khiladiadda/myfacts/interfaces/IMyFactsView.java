package com.khiladiadda.myfacts.interfaces;

import com.khiladiadda.network.model.response.FactsResponse;
import com.khiladiadda.network.model.ApiError;

public interface IMyFactsView {

    void onLikedComplete(FactsResponse responseModel);

    void onLikedFailure(ApiError error);

    void onBookmarkedComplete(FactsResponse responseModel);

    void onBookmarkedFailure(ApiError error);

}
