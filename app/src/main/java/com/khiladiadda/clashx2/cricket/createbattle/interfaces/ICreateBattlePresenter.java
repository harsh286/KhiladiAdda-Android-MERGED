package com.khiladiadda.clashx2.cricket.createbattle.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.request.PlayerId;
import com.khiladiadda.network.model.request.hth.CreateBattleRequest;
import com.khiladiadda.network.model.request.hth.UpdateOpponentPlayers;
import com.khiladiadda.network.model.response.hth.KaPlayerHTH;

public interface ICreateBattlePresenter extends IBasePresenter {

    void createBattle(CreateBattleRequest request);

    void updateBattleGroup(CreateBattleRequest request, String groupId);

    void acceptBattleGroup(CreateBattleRequest request);

    void getMyBattles(String matchId, String battleId, boolean user, boolean highestBattle, boolean lowestBattle, boolean newestBattle);

    void getALLBattles(String matchId, String battleId, boolean user, boolean highestBattle, boolean lowestBattle, boolean newestBattle);

    void getLeguesBattles(boolean upcoming, boolean past, boolean live);

    void updateOpponentPlayer(String id, UpdateOpponentPlayers request);

    void getPlayerStatus(String id, UpdateOpponentPlayers players);
}