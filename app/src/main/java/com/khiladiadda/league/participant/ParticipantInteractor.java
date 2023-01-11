package com.khiladiadda.league.participant;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.request.FBBattleParticipantRequest;
import com.khiladiadda.network.model.request.FBBattleParticipantResponse;
import com.khiladiadda.network.model.response.FBParticipantResponse;
import com.khiladiadda.network.model.response.ParticipantResponse;
import com.khiladiadda.network.model.response.QuizParticipantResponse;
import com.khiladiadda.network.model.response.TeamResponse;

import rx.Subscription;

public class ParticipantInteractor {

    public Subscription getParticipant(String gameId, IApiListener<ParticipantResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getParticipant(gameId)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getTeam(String gameId, IApiListener<TeamResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getTeam(gameId)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getQuizParticipant(String gameId, IApiListener<QuizParticipantResponse> listener, int page, int limit) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getQuizParticipant(gameId, page, limit)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getFBGroupParticipant(FBBattleParticipantRequest request, int page, int limit, boolean isLeaderboard, IApiListener<FBParticipantResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getFBGroupParticipant(request, page, limit, isLeaderboard)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getFBBattleParticipant(FBBattleParticipantRequest request, int page, int limit, IApiListener<FBParticipantResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getFBBattleParticipant(request, page, limit)).subscribe(new SubscriberCallback<>(listener));
    }
    public Subscription getFBMatchParticipant(FBBattleParticipantRequest request, int page, int limit, IApiListener<FBParticipantResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getFBMatchParticipant(request, page, limit)).subscribe(new SubscriberCallback<>(listener));
    }
}
