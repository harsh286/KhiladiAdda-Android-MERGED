package com.khiladiadda.network.model.response.droid_doresponse;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;

import rx.Subscription;

public class LeaderBoardInteractor {

    Subscription getLeaderBoardDroidoList(IApiListener<LeaderBoardDroidoResponse> listener, String tournamentId) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getLeaderBoardDroidoList(tournamentId)).subscribe(new SubscriberCallback<>(listener));
    }

}