package com.khiladiadda.gamercash.ip;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.request.RaceConditionPayoutRequest;
import com.khiladiadda.network.model.response.gamer_cash.GamerCashResponse;
import com.khiladiadda.network.model.response.gamer_cash.SwitchGamerCashRequest;
import com.khiladiadda.network.model.response.gamer_cash.SwitchGamerCashResponse;

import rx.Subscription;

public class SwitchGamerCashInteractor {
    Subscription getSwitchGamerCash(IApiListener<SwitchGamerCashResponse> listener, SwitchGamerCashRequest switchGamerCashRequest) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.switchGamerData(switchGamerCashRequest)).subscribe(new SubscriberCallback<>(listener));
    }

}