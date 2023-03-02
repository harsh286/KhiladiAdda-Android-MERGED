package com.khiladiadda.wordsearch.listener;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.WordSearchLeaderBoardMainResponse;

public interface IWordSearchParticipantsView {

    void onWordSearchParticipantsComplete(WordSearchLeaderBoardMainResponse responseModel);

    void onWordSearchParticipantsFailure(ApiError error);
}
