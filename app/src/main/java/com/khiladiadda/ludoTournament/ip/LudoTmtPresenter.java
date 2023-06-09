package com.khiladiadda.ludoTournament.ip;

import com.khiladiadda.ludoTournament.LudoTmtInteractor;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.request.ludoTournament.LudoTournamentFetchRequest;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtAllTournamentMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtJoinMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtMyMatchMainResponse;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtRoundsDetailsMainResponse;

import retrofit2.http.Query;
import rx.Subscription;

public class LudoTmtPresenter implements ILudoTmtPresenter {

    private ILudoTmtView mView;
    private ILudoTmtMyMatchView mMyView;
    private LudoTmtInteractor mInteractor;
    private Subscription mSubscription;

    public LudoTmtPresenter(ILudoTmtView view) {
        mView = view;
        mInteractor = new LudoTmtInteractor();
    }

    public LudoTmtPresenter(ILudoTmtMyMatchView view) {
        mMyView = view;
        mInteractor = new LudoTmtInteractor();
    }

    @Override
    public void getAllTournament(boolean startDate, int type, boolean banners, String banner_type, boolean profile) {
        mSubscription = mInteractor.getLudoTmtAllTournament(ludoTmtAllTournamentMainResponseIApiListener, startDate, type, banners, banner_type, profile );
    }

    private IApiListener<LudoTmtAllTournamentMainResponse> ludoTmtAllTournamentMainResponseIApiListener = new IApiListener<LudoTmtAllTournamentMainResponse>() {
        @Override
        public void onSuccess(LudoTmtAllTournamentMainResponse response) {
            mView.onGetAllTournamentComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onGetAllTournamentFailure(error);
        }
    };

    @Override
    public void getPastTournament() {
        mSubscription = mInteractor.onPastLudoTournament(ludoTmtMyMatchMainResponseIApiListener);
    }

    @Override
    public void getLiveTournament() {
        mSubscription = mInteractor.onLiveLudoTournament(ludoTmtMyMatchMainResponseIApiListener);
    }

    @Override
    public void getUpcomingTournament() {
        mSubscription = mInteractor.onUpcomingLudoTournament(ludoTmtMyMatchMainResponseIApiListener);
    }

    private IApiListener<LudoTmtMyMatchMainResponse> ludoTmtMyMatchMainResponseIApiListener = new IApiListener<LudoTmtMyMatchMainResponse>() {
        @Override
        public void onSuccess(LudoTmtMyMatchMainResponse response) {
            mMyView.onGetMyMatchTournamentComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mMyView.onGetMyMatchTournamentFailure(error);
        }
    };


    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
