package com.khiladiadda.main.category.interfaces;

import com.khiladiadda.network.model.response.TrendinQuizResponse;
import com.khiladiadda.network.model.ApiError;

public interface ICategoryView {

    void onTrendingQuizComplete(TrendinQuizResponse responseModel);

    void onTrendingQuizFailure(ApiError error);

}