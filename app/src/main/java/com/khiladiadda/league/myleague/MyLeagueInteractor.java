package com.khiladiadda.league.myleague;

import com.khiladiadda.network.model.response.MyLeagueResponse;
import com.khiladiadda.network.model.response.MyTeamResponse;
import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;

import rx.Subscription;

public class MyLeagueInteractor {

    public Subscription getMyLeague(IApiListener<MyLeagueResponse> listener, String id, boolean upcoming, boolean past, boolean live) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getMyLeague(id, upcoming, past, live)).subscribe(new SubscriberCallback<MyLeagueResponse>(listener));
    }

    public Subscription getMyTeam(String gameId, IApiListener<MyTeamResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getMyTeam(gameId)).subscribe(new SubscriberCallback<MyTeamResponse>(listener));
    }

}