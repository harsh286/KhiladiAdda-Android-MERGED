package com.khiladiadda.leaderboard.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;

public interface ILeaderboardPresenter extends IBasePresenter {

    void getQuizAll(int page, int limit);

    void getQuizDaily(int page, int limit);

    void getQuizWeekly(int page, int limit);

    void getQuizMonth(int page, int limit);

    void getGameAll(String id, int page, int limit);

    void getGameDaily(String id, int page, int limit);

    void getGameWeekly(String id, int page, int limit);

    void getGameMonth(String id, int page, int limit);

    void getGameById(String id, int page, int limit);

    void getLudo(int page, int limit, String type, int contestType);

    void getFanBattle(int page, int limit, int type);

    void getHTHBattles(int page, int limit, String type);

    void getLudoAdda(int page, int limit, String type);

    void getWS(int page, int limit, String type);

    void getDroidDo(int page, int limit, String type);

    void getLudoTournament(String type, int page, int limit);

    void getCourtPiece(String type, int page, int limit);

    void getRummy(String type, int page, int limit);

}