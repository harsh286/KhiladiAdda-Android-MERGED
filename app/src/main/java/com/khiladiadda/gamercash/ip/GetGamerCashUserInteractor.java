package com.khiladiadda.gamercash.ip;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.response.GetGamerCashResponse;

import rx.Subscription;

public class GetGamerCashUserInteractor {
    Subscription getGamerData(IApiListener<GetGamerCashResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.payGamerCashData()).subscribe(new SubscriberCallback<>(listener));
    }

}