package com.khiladiadda.network.model.response.droid_doresponse;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;

import rx.Subscription;

public class TournamentDetailInteractor {
    Subscription getTournamentDetail(TournamentDetailRequest tournamentDetailRequest,IApiListener<TournamentDetailResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getTournamentStart(tournamentDetailRequest)).subscribe(new SubscriberCallback<>(listener));

//        return manager.createObservable(service.getTournamentStart(tournamentDetailRequest),tournamentDetailRequest).subscribe(new SubscriberCallback<>(listener));
    }

}