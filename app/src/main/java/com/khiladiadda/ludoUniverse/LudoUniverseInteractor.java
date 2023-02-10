package com.khiladiadda.ludoUniverse;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.LudoContestRequest;
import com.khiladiadda.network.model.request.OpponentLudoRequest;
import com.khiladiadda.network.model.response.LudoContestResponse;
import com.khiladiadda.network.model.response.ModeResponse;

import rx.Subscription;

public class LudoUniverseInteractor {

    Subscription getLudoContestList(IApiListener<LudoContestResponse> listener, String date, String contestType, boolean banner, String bannerType, boolean profile, int mode, int entryFee, int from, int to, int fromMode) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getList(date, contestType, banner, bannerType, profile, mode, entryFee, from, to, fromMode)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getAllLudoContestList(IApiListener<LudoContestResponse> listener, int page, int limit, int contestMode) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getAllList(page, limit, contestMode)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription addLudoChallenge(LudoContestRequest request, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.addChallenge(request)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription joinLudoContest(String contestId, OpponentLudoRequest ludoRequest, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.acceptChallenge(contestId)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription cancelLudoContest(String contestId, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.cancelChallenge(contestId)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getStatusLudoContest(String contestId, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getLUStatus(contestId)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getMode(IApiListener<ModeResponse> listener, String bannerType) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getMode(bannerType)).subscribe(new SubscriberCallback<>(listener));
    }

}