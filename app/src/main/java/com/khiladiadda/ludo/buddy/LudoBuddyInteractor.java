package com.khiladiadda.ludo.buddy;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.LudoBuddyChallengeRequest;
import com.khiladiadda.network.model.response.BuddyResponse;

import rx.Subscription;

public class LudoBuddyInteractor {

    Subscription getBuddyList(IApiListener<BuddyResponse> listener, String contestType) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getBuddyList(contestType)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription sendChallengeRequest(LudoBuddyChallengeRequest request, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.sendChallengeRequest(request)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getBuddyListUniverse(IApiListener<BuddyResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getLUBuddyList()).subscribe(new SubscriberCallback<>(listener));
    }
}