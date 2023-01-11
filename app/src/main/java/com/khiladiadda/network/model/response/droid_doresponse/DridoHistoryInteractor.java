package com.khiladiadda.network.model.response.droid_doresponse;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;

import rx.Subscription;

public class DridoHistoryInteractor {

    Subscription getDroidoHistoryGameList(IApiListener<DroidoHistoryGameList> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getDroidoHistoryGameList()).subscribe(new SubscriberCallback<>(listener));
    }

//    Subscription getTrendingTournament(IApiListener<TrendingTournamentResponse> listener, String id) {
//        ApiManager manager = ApiManager.getInstance();
//        ApiService service = manager.createService();
//        return manager.createObservable(service.getTrendingTournamentList(id)).subscribe(new SubscriberCallback<>(listener));
//    }

}