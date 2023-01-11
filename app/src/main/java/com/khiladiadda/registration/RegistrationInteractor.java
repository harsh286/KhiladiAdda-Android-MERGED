package com.khiladiadda.registration;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.RegistrationRequest;

import rx.Subscription;

public class RegistrationInteractor {

    public Subscription doRegister(RegistrationRequest request, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.register(request)).subscribe(new SubscriberCallback<>(listener));
    }

}