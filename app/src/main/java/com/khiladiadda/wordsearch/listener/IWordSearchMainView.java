package com.khiladiadda.wordsearch.listener;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.WordSearchTrendingMainResponse;

public interface IWordSearchMainView {

    void onWordSearchQuizComplete(WordSearchTrendingMainResponse responseModel);

    void onWordSearchQuizFailure(ApiError error);

}
