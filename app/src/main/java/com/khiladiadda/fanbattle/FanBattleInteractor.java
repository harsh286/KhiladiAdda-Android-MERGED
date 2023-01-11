package com.khiladiadda.fanbattle;

import com.khiladiadda.network.model.response.MatchResponse;
import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;

import rx.Subscription;

public class FanBattleInteractor {

    public Subscription getCategory(IApiListener<MatchResponse> listener, String id) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getMatchList(id, true)).subscribe(new SubscriberCallback<>(listener));
    }

}