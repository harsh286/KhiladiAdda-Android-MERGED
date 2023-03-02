package com.khiladiadda.wordsearch.listener;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.WordSearchLiveLeaderBoardMainResponse;

public interface IWordSearchLiveLeaderboardView {

    void onWordSearchLiveLeaderboardComplete(WordSearchLiveLeaderBoardMainResponse responseModel);

    void onWordSearchLiveLeaderboardFailure(ApiError error);

}
