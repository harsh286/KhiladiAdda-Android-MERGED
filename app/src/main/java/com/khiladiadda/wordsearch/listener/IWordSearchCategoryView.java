package com.khiladiadda.wordsearch.listener;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.wordsearch_new.WordSearchCategoryMainResponse;

public interface IWordSearchCategoryView {

    void onWordSearchComplete(WordSearchCategoryMainResponse responseModel);

    void onWordSearchFailure(ApiError error);
}
