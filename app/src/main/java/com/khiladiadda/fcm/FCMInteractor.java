package com.khiladiadda.fcm;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.CricBuzzApiManger;
import com.khiladiadda.network.CricSubscriberCallback;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.CricAPiListerner;
import com.khiladiadda.network.model.request.FcmRequest;
import com.khiladiadda.network.model.response.CricScorce;
import com.khiladiadda.network.model.response.MatchResponse;

import rx.Subscription;

public class FCMInteractor {

    public FCMInteractor() { }

    public Subscription updateLeague(IApiListener<BaseResponse> listener, FcmRequest request) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.updateLeague(request)).subscribe(new SubscriberCallback<>(listener));
    }

}