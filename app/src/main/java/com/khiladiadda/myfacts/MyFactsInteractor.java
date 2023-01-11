package com.khiladiadda.myfacts;

import com.khiladiadda.network.model.response.FactsResponse;
import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;

import rx.Subscription;

public class MyFactsInteractor {

    public Subscription getLikedFacts(IApiListener<FactsResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getLikedFacts())
                .subscribe(new SubscriberCallback<FactsResponse>(listener));
    }

    public Subscription getBookmarkedFacts(IApiListener<FactsResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getBookmarkedFacts())
                .subscribe(new SubscriberCallback<FactsResponse>(listener));
    }

}