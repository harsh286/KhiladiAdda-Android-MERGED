package com.khiladiadda.main.facts.interfaces;

import com.khiladiadda.network.model.response.FactDetailsResponse;
import com.khiladiadda.network.model.response.FactsList;
import com.khiladiadda.network.model.response.FactsResponse;
import com.khiladiadda.network.model.response.LikeFactResponse;
import com.khiladiadda.network.model.ApiError;

import java.util.List;

public interface IFactsView {

    void onTrendingFactsComplete(FactsResponse responseModel);

    void onTrendingFactsFailure(ApiError error);

    void onFactsComplete(FactsResponse responseModel);

    void onFactsFailure(ApiError error);

    void onFactDetailsComplete(FactDetailsResponse responseModel);

    void onFactDetailsFailure(ApiError error);

    void onLikeFactComplete(LikeFactResponse responseModel);

    void onLikeFactFailure(ApiError error);

    void onBookmarkFactComplete(LikeFactResponse responseModel);

    void onBookmarkFactFailure(ApiError error);

    void onFactsFetchedFromDB(List<FactsList> eventList);

}
