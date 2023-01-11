package com.khiladiadda.league.participant.interfaces;


import com.khiladiadda.base.interfaces.IBasePresenter;

public interface IParticipantPresenter extends IBasePresenter {

    void getParticipant(String gameId);

    void getTeam(String gameId);

    void getQuizParticipant(String gameId, int page, int limit);

    void getFBGroupParticipant(String groupId, int page, int limit, boolean isLeaderboard);

    void getFBBattleParticipant(String battleId, int page, int limit);

    void getFBMatchParticipant(String matchId, int page, int limit);

}