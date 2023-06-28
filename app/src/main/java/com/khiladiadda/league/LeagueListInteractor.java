package com.khiladiadda.league;

import com.khiladiadda.network.model.response.LeagueListReponse;
import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;

import rx.Subscription;

public class LeagueListInteractor {

    public Subscription getGame(String catId, int type,IApiListener<LeagueListReponse> listener,String gameId ) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getCRLeague(catId,gameId,type))
                .subscribe(new SubscriberCallback<>(listener));
    }

}
