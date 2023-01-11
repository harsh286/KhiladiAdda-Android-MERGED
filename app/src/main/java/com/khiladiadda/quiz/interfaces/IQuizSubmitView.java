package com.khiladiadda.quiz.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.QuizSubmitResponse;

public interface IQuizSubmitView {

    void onQuizSubmitComplete(QuizSubmitResponse responseModel);

    void onQuizSubmitFailure(ApiError error);

}