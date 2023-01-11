package com.khiladiadda.playerStats;

import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.CricBuzzApiManger;
import com.khiladiadda.network.CricPlayerSubsricberCallback;
import com.khiladiadda.network.model.CricAPiListerner;
import com.khiladiadda.playerStats.model.PlayerProfileRes;

import rx.Subscription;

public class PlayerProfileIntracter {

    public Subscription getplayerprofile(CricAPiListerner<PlayerProfileRes> listener, String apikey, int pid) {
        CricBuzzApiManger manager = CricBuzzApiManger.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getPlayerProfile(apikey, pid)).subscribe(new CricPlayerSubsricberCallback<>(listener));
    }
}
