package com.khiladiadda.clashx2.main;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.response.CxBannerMainResponse;
import com.khiladiadda.network.model.response.HTHCancelResponse;
import com.khiladiadda.network.model.response.hth.HTHMainResponse;
import com.khiladiadda.network.model.response.hth.Result;

import rx.Subscription;

public class HTHInteractor {

    public Subscription getHTHMatches(IApiListener<HTHMainResponse> listener, String id, int type) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getMyHTHBattle(false, id, type)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription onCancelBattle(IApiListener<HTHCancelResponse> listener, String id) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.CancelBattle(id)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription onResultBattle(IApiListener<Result> listener, String id) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getResult(id)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getMatchStatus(IApiListener<HTHMainResponse> listener, String matchId) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getMatchStatus(matchId)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription getCxBanner(IApiListener<CxBannerMainResponse> listener, String type) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getCxBanner(type)).subscribe(new SubscriberCallback<>(listener));
    }

}