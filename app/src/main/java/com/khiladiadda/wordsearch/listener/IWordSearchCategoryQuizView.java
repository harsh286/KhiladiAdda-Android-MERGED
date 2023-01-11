package com.khiladiadda.wordsearch.listener;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.WordSearchCategoriesQuizzesMainResponse;

public interface IWordSearchCategoryQuizView {

    void onWordSearchQuizComplete(WordSearchCategoriesQuizzesMainResponse responseModel);

    void onWordSearchQuizFailure(ApiError error);
}
