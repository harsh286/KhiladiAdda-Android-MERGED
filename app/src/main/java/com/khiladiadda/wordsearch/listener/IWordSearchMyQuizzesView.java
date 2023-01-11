package com.khiladiadda.wordsearch.listener;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.WordSearchMyQuizzesMainResponse;

public interface IWordSearchMyQuizzesView {

    void onWordSearchMyQuizzesComplete(WordSearchMyQuizzesMainResponse responseModel);

    void onWordSearchMyQuizzesFailure(ApiError error);

}
