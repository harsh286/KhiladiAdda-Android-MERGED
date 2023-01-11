package com.khiladiadda.base;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.response.VersionResponse;

import rx.Subscription;

public class BaseInteractor {

    Subscription getVersionDetails(IApiListener<VersionResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getVersionDetails()).subscribe(new SubscriberCallback<>(listener));
    }

}