package com.khiladiadda.main;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.UpdateFavouriteRequest;
import com.khiladiadda.network.model.response.DashboardResponse;
import com.khiladiadda.network.model.response.TrendinQuizResponse;

import rx.Subscription;

public class MainInteractor {

    public Subscription getTrending(IApiListener<DashboardResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getDashboardData())
                .subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription updateFavourite(IApiListener<BaseResponse> listener, UpdateFavouriteRequest request) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.updateFavourite(request))
                .subscribe(new SubscriberCallback<>(listener));
    }
}
