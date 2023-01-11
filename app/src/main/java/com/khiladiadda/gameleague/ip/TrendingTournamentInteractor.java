package com.khiladiadda.gameleague.ip;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.response.droid_doresponse.MyTournamentResponse;
import com.khiladiadda.network.model.response.droid_doresponse.TrendingTournamentResponse;

import rx.Subscription;

public class TrendingTournamentInteractor {
    Subscription getTrendingTournament(IApiListener<TrendingTournamentResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getTrendingTournamentList()).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getMyTournament(IApiListener<MyTournamentResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getMyTournamentList()).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getFilterDataList(IApiListener<TrendingTournamentResponse> listener,int v) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getFilterTournament(v)).subscribe(new SubscriberCallback<>(listener));
    }

}