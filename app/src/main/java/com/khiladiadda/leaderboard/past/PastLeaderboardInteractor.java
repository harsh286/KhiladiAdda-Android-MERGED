package com.khiladiadda.leaderboard.past;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.response.SquadLeaderbordResponse;

import rx.Subscription;

public class PastLeaderboardInteractor {

    public Subscription getPastLeaderboard(IApiListener<SquadLeaderbordResponse> listener, String id, int page, int limit) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getSquadDuoLeaderBoard(id, page, limit))
                .subscribe(new SubscriberCallback<SquadLeaderbordResponse>(listener));
    }

}