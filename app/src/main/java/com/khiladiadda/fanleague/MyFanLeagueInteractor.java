package com.khiladiadda.fanleague;

import com.khiladiadda.network.model.response.CricScorce;
import com.khiladiadda.network.model.response.MatchResponse;
import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.CricBuzzApiManger;
import com.khiladiadda.network.CricSubscriberCallback;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.CricAPiListerner;

import rx.Subscription;

public class MyFanLeagueInteractor {

    public MyFanLeagueInteractor() { }

    public Subscription getMyFanLeague(IApiListener<MatchResponse> listener, boolean upcoming, boolean past, boolean live) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getMyFanLeague(upcoming, past, live)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getlive(CricAPiListerner<CricScorce> listener, String apikey, int matchid) {
        CricBuzzApiManger manager = CricBuzzApiManger.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getlivescore(apikey, matchid)).subscribe(new CricSubscriberCallback<>(listener));
    }

}