package com.khiladiadda.wordsearch.listener;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.WordSearchStartMainResponse;

public interface IWordSearchStartView {

    void onWordSearchStartComplete(WordSearchStartMainResponse responseModel);

    void onWordSearchStartFailure(ApiError error);
}
