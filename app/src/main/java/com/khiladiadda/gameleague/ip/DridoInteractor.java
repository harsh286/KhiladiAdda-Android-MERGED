package com.khiladiadda.gameleague.ip;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.response.droid_doresponse.ItemGamesMainResponse;

import rx.Subscription;

public class DridoInteractor {
    Subscription getGameList(IApiListener<ItemGamesMainResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getGameList()).subscribe(new SubscriberCallback<>(listener));
    }



}