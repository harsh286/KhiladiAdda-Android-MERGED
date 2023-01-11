package com.khiladiadda.quiz.result.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.LeaderBoardResponse;
import com.khiladiadda.network.model.response.StartQuizResponse;

public interface IQuizResultView {

    void onStartQuizComplete(StartQuizResponse responseModel);

    void onStartQuizFailure(ApiError error);

    void onLeaderBoardComplete(LeaderBoardResponse responseModel);

    void onLeaderBoardFailure(ApiError error);
}