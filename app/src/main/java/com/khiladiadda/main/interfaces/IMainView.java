package com.khiladiadda.main.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.DashboardResponse;
import com.khiladiadda.network.model.response.TrendinQuizResponse;

public interface IMainView {

    void onTrendingQuizComplete(DashboardResponse responseModel);

    void onTrendingQuizFailure(ApiError error);

    void onUpdateFavouriteComplete(BaseResponse responseModel);

    void onUpdateFavouriteFailure(ApiError error);

}