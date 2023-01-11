package com.khiladiadda.referhistory;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.response.ReferResponse;

import rx.Subscription;

public class ReferInteractor {

    public Subscription getRefer(IApiListener<ReferResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getRefer()).subscribe(new SubscriberCallback<>(listener));
    }

}