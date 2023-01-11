package com.khiladiadda.ludo;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.LudoContestRequest;
import com.khiladiadda.network.model.request.OpponentLudoRequest;
import com.khiladiadda.network.model.response.LudoContestResponse;

import retrofit2.http.Query;
import rx.Subscription;

public class LudoChallengeInteractor {

    Subscription getLudoContestList(IApiListener<LudoContestResponse> listener, String date, String contestType, boolean banner, String bannerType, boolean profile, int mode, int entryFee, int from, int to) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getLudoContestList(date, contestType, banner, bannerType, profile, mode, entryFee, from, to)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getAllLudoContestList(IApiListener<LudoContestResponse> listener, String contestType, int page, int limit) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getAllLudoContestList(contestType, page, limit)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription addLudoChallenge(LudoContestRequest request, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.addLudoChallengeResponse(request)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription joinLudoContest(String contestId, OpponentLudoRequest ludoRequest, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.joinLudoContest(contestId, ludoRequest)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription cancelLudoContest(String contestId, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.cancelLudoContest(contestId)).subscribe(new SubscriberCallback<>(listener));
    }

}