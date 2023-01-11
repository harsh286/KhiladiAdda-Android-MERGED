package com.khiladiadda.scratchcard;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.ChecksumRequest;
import com.khiladiadda.network.model.response.ChecksumResponse;
import com.khiladiadda.network.model.response.ScratchCardResponse;

import rx.Subscription;

public class ScratchInteractor {

    Subscription getScratchCards(IApiListener<ScratchCardResponse> listener, int type) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getAllScratchCards(type)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription applyScratchCard(IApiListener<BaseResponse> listener, String scractchCardid) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.scratchedCard(scractchCardid)).subscribe(new SubscriberCallback<>(listener));
    }

}