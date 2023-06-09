package com.khiladiadda.ludoTournament;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.request.AddBeneficiaryRequest;
import com.khiladiadda.network.model.request.ludoTournament.LudoTournamentFetchRequest;
import com.khiladiadda.network.model.response.AddBeneficiaryResponse;
import com.khiladiadda.network.model.response.PayoutResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtAllPastRoundsMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtAllTournamentMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtJoinMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtMyMatchMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtRoundsDetailsMainResponse;

import retrofit2.http.Query;
import rx.Subscription;

public class LudoTmtInteractor {

    public Subscription getLudoTmtAllTournament(IApiListener<LudoTmtAllTournamentMainResponse> listener, boolean startDate, int type, boolean banners, String banner_type, boolean profile) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.onGetLudoTmtAllTournament(startDate, type, banners, banner_type, profile)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription onJoinLudoTournament(IApiListener<LudoTmtJoinMainResponse> listener, String tournament_id) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.onJoinLudoTournament(tournament_id)).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription onPastLudoTournament(IApiListener<LudoTmtMyMatchMainResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.onPastLudoTournament()).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription onLiveLudoTournament(IApiListener<LudoTmtMyMatchMainResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.onLiveLudoTournament()).subscribe(new SubscriberCallback<>(listener));
    }

    public Subscription onUpcomingLudoTournament(IApiListener<LudoTmtMyMatchMainResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.onUpcomingLudoTournament()).subscribe(new SubscriberCallback<>(listener));
    }
    public Subscription onLudoTournamentRound(IApiListener<LudoTmtRoundsDetailsMainResponse> listener, String id) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.onLudoTournamentRound(id)).subscribe(new SubscriberCallback<>(listener));
    }
    public Subscription onLudoTournamentPastRound(IApiListener<LudoTmtAllPastRoundsMainResponse> listener, String id) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.onLudoTournamentPastRound(id)).subscribe(new SubscriberCallback<>(listener));
    }
}
