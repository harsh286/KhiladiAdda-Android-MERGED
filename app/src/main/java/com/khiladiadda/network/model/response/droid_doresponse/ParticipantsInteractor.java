package com.khiladiadda.network.model.response.droid_doresponse;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;

import rx.Subscription;

public class ParticipantsInteractor {
    Subscription getParticipants(IApiListener<GameParticipantsDataResponse> listener, String id) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getParticipantsList(id)).subscribe(new SubscriberCallback<>(listener));
    }

}