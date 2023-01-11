package com.khiladiadda.clashx2.football.createbattle;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.hth.CreateBattleRequest;
import com.khiladiadda.network.model.request.hth.UpdateOpponentPlayers;
import com.khiladiadda.network.model.response.hth.BattleResponseHTH;
import com.khiladiadda.network.model.response.hth.CreateBattleResponse;
import com.khiladiadda.network.model.response.hth.HTHMainResponse;

import rx.Subscription;

public class FootballCreateBattleInteractor {

    public Subscription createBattle(IApiListener<CreateBattleResponse> listener, CreateBattleRequest request) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.createBattleGroup(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription updateBattle(IApiListener<BaseResponse> listener, CreateBattleRequest request, String groupId) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.updateBattleGroup(request, groupId)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription acceptBattle(IApiListener<CreateBattleResponse> listener, CreateBattleRequest request) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.createBattleGroup(request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getAllBattles(IApiListener<BattleResponseHTH> listener, String matchId, String battleId, boolean user, boolean highestBattle, boolean lowestBattle, boolean newestBattle) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getMyBattles(matchId, battleId, user, highestBattle, lowestBattle, newestBattle)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getMyBattles(IApiListener<BattleResponseHTH> listener, String matchId, String battleId, boolean user, boolean highestBattle, boolean lowestBattle, boolean newestBattle) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getMyBattles(matchId, battleId, user, highestBattle, lowestBattle, newestBattle)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getMyLegues(IApiListener<HTHMainResponse> listener, boolean upcoming, boolean past, boolean live) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getMylegues(upcoming, past, live)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription UpdatePlayer(IApiListener<BaseResponse> listener, String battleId, UpdateOpponentPlayers request) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.updateOpponentPlayer(battleId, request)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription PlayerStatus(IApiListener<HTHMainResponse> listener, String matchId, UpdateOpponentPlayers request) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getPlayerSatus(matchId, request)).subscribe(new SubscriberCallback<>(listener));
    }
}