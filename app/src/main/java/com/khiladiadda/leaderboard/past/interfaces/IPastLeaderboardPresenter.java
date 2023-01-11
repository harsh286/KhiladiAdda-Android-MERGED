package com.khiladiadda.leaderboard.past.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;

public interface IPastLeaderboardPresenter extends IBasePresenter {

    void getLeaderboard(String id, int page, int limit);

}