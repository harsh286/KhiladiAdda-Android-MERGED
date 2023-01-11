package com.khiladiadda.splash;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.response.MasterResponse;
import com.khiladiadda.network.model.response.VersionResponse;

import rx.Subscription;

public class SplashInteractor {

    public Subscription getMaster(IApiListener<MasterResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getMaster()).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getVersionDetails(IApiListener<VersionResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getVersionDetails()).subscribe(new SubscriberCallback<>(listener));
    }

}