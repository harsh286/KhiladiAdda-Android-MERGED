package com.khiladiadda.chat;

import com.khiladiadda.network.model.response.NotifyResponse;
import com.khiladiadda.network.model.request.UpdateChatIdRequest;
import com.khiladiadda.network.model.response.ProfileResponse;
import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;

import rx.Subscription;

public class ChatInteractor {

    Subscription updateChatId(IApiListener<ProfileResponse> listener, UpdateChatIdRequest request) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.updateChatId(request)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getOpponentTokens(IApiListener<NotifyResponse> listener, String id) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getOpponentTokens(id)).subscribe(new SubscriberCallback<>(listener));
    }

}