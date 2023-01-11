package com.khiladiadda.gamercash.ip;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.response.gamer_cash.GamerCashResponse;

import rx.Subscription;

public class GamerCashUserInteractor {
    Subscription getGamerCash(IApiListener<GamerCashResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getGamerCashData()).subscribe(new SubscriberCallback<>(listener));
    }

}