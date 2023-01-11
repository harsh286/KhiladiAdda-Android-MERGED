package com.khiladiadda.quiz.list.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.QuizListResponse;
import com.khiladiadda.network.model.response.StartQuizResponse;
import com.khiladiadda.network.model.response.UserQuizPlayedResponse;

public interface IQuizListView {

    void onStartQuizComplete(StartQuizResponse responseModel);

    void onStartQuizFailure(ApiError error);

    void onQuizListComplete(QuizListResponse responseModel);

    void onQuizListFailure(ApiError error);

    void onUserPlayedQuizComplete(UserQuizPlayedResponse responseModel);

    void onUserPlayedQuizFailure(ApiError error);

}