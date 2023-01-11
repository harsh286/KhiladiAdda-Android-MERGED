package com.khiladiadda.playerStats;

import com.khiladiadda.network.model.response.CricScorce;
import com.khiladiadda.network.model.CricApiError;
import com.khiladiadda.playerStats.model.PlayerProfileRes;

public interface IPlayerProfile {
    interface IPlayerProfileView{
        void onSuccess(PlayerProfileRes playerProfileRes);

        void showError(String message);

        void onLive(CricScorce response);
        void onFailure(CricApiError error);
    }

}