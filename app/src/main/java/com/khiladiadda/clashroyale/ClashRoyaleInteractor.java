package com.khiladiadda.clashroyale;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.response.ClashRoyaleFilterReponse;
import com.khiladiadda.network.model.response.LeagueListReponse;

import rx.Subscription;

public class ClashRoyaleInteractor {

    public Subscription getFilter(String gameId, IApiListener<ClashRoyaleFilterReponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getGameCategory(gameId))
                .subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getLeague(String catId, String gameId,int type,IApiListener<LeagueListReponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getCRLeague(catId,gameId,type))
                .subscribe(new SubscriberCallback<>(listener));
    }

}