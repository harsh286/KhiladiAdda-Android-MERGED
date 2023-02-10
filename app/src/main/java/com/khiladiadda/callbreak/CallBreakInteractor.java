package com.khiladiadda.callbreak;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.CallBreakJoinMainResponse;
import com.khiladiadda.network.model.response.CallBreakResponse;

import rx.Subscription;

public class CallBreakInteractor {

    Subscription getCallBreak(IApiListener<CallBreakResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getCallBreak()).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getCallBreakJoin(IApiListener<CallBreakJoinMainResponse> listener, String id) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getCallBreakJoin(id)).subscribe(new SubscriberCallback<>(listener));
    }

}