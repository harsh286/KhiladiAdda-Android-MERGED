package com.khiladiadda.league.details;

import com.khiladiadda.network.model.request.AddCredential;
import com.khiladiadda.network.model.response.CreateTeamResponse;
import com.khiladiadda.network.model.response.MyTeamResponse;
import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.response.ProfileTransactionResponse;
import com.khiladiadda.network.model.response.StartQuizResponse;

import rx.Subscription;

public class LeagueDetailsInteractor {

    public Subscription startLeague(AddCredential credential, String gameId, IApiListener<StartQuizResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.addCredential(credential, gameId))
                .subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription createTeam(AddCredential credential, String gameId, IApiListener<CreateTeamResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.createTeam(credential, gameId))
                .subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getMyTeam(String gameId, IApiListener<MyTeamResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getMyTeam(gameId))
                .subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getProfile(IApiListener<ProfileTransactionResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getProfile(false))
                .subscribe(new SubscriberCallback<>(listener));
    }

}