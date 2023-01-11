package com.khiladiadda.quiz.splash.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.QuizQuestionResponse;

public interface IQuizSplashView {

//    void onQuestionsComplete(QuizQuestionResponse responseModel);
//
//    void onQuestionsFailure(ApiError error);

    void onQuizTimeComplete(BaseResponse responseModel);

    void onQuizTimeFailure(ApiError error);

}