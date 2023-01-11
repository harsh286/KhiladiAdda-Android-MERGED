package com.khiladiadda.login;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;

import rx.Subscription;

public class TrueCallerInteractor {

    public Subscription getTrueCallerdata(TrueCallerRequest request, IApiListener<TrueCallerResponse> mTrueCallerApiListener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.trueCallerVerify(request)).subscribe(new SubscriberCallback<>(mTrueCallerApiListener));
    }
}
