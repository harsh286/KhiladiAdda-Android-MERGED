package com.khiladiadda.league.participant.interfaces;


import com.khiladiadda.network.model.request.FBBattleParticipantResponse;
import com.khiladiadda.network.model.response.FBParticipantResponse;
import com.khiladiadda.network.model.response.ParticipantResponse;
import com.khiladiadda.network.model.response.TeamResponse;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.QuizParticipantResponse;

public interface IParticipantView {

    void onParticipantComplete(ParticipantResponse responseModel);

    void onParticipantFailure(ApiError error);

    void onTeamComplete(TeamResponse responseModel);

    void onTeamFailure(ApiError error);

    void onQuizParticipantComplete(QuizParticipantResponse responseModel);

    void onQuizParticipantFailure(ApiError error);

    void onFBGroupParticipantComplete(FBParticipantResponse responseModel);

    void onFBGroupParticipantFailure(ApiError error);

    void onFBBattleParticipantComplete(FBParticipantResponse responseModel);

    void onFBBattleParticipantFailure(ApiError error);

    void onFBMatchParticipantComplete(FBParticipantResponse responseModel);

    void onFBMatchParticipantFailure(ApiError error);
}
