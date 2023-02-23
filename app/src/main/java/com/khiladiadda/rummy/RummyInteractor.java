package com.khiladiadda.rummy;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.LudoContestRequest;
import com.khiladiadda.network.model.request.OpponentLudoRequest;
import com.khiladiadda.network.model.response.LudoContestResponse;
import com.khiladiadda.network.model.response.ModeResponse;
import com.khiladiadda.network.model.response.RummyCheckGameResponse;
import com.khiladiadda.network.model.response.RummyRefreshTokenMainResponse;
import com.khiladiadda.network.model.response.RummyResponse;

import rx.Subscription;

public class RummyInteractor {

    Subscription getRummyList(IApiListener<RummyResponse> listener, String arenaType) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getRummyList(arenaType, "21")).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getRummyRefreshToken(IApiListener<RummyRefreshTokenMainResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getRummyRefershToken()).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getCheckGameStatus(IApiListener<RummyCheckGameResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getRummyCheckGameStatus()).subscribe(new SubscriberCallback<>(listener));
    }

}