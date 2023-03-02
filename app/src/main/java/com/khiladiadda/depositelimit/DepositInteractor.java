package com.khiladiadda.depositelimit;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.request.deposite.DepositLimitRequest;
import com.khiladiadda.network.model.response.deposite.DepositLimitMainResponse;
import com.khiladiadda.network.model.response.deposite.FetchDepositLimitMainResponse;

import rx.Subscription;

public class DepositInteractor {
    Subscription getDepositAdd(DepositLimitRequest request, IApiListener<DepositLimitMainResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.addDepositLimit(request)).subscribe(new SubscriberCallback<>(listener));
    }
    Subscription getFetchDepositAdd(IApiListener<FetchDepositLimitMainResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.fetchDepositLimit()).subscribe(new SubscriberCallback<>(listener));
    }

}
