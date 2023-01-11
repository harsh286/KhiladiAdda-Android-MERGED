package com.khiladiadda.league;

import com.khiladiadda.network.model.response.LeagueListReponse;
import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;

import rx.Subscription;

public class LeagueListInteractor {

    public Subscription getGame(String gameId, int type, IApiListener<LeagueListReponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getCRLeague(gameId, type))
                .subscribe(new SubscriberCallback<>(listener));
    }

}
