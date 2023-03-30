package com.khiladiadda.ludoTournament.ip;

import com.khiladiadda.ludoTournament.LudoTmtInteractor;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.ludoTournament.LudoTmtJoinMainResponse;

import rx.Subscription;

public class LudoTmtJoinPresenter implements ILudoTmtDetailsPresenter {

    private ILudoTmtDetailsView mView;
    private LudoTmtInteractor mInteractor;
    private Subscription mSubscription;

    public LudoTmtJoinPresenter(ILudoTmtDetailsView view) {
        mView = view;
        mInteractor = new LudoTmtInteractor();
    }

    @Override
    public void onJoinTournament(String id) {
        mSubscription = mInteractor.onJoinLudoTournament(mLudoTmtJoinMainResponseIApiListener, id);
    }

    private IApiListener<LudoTmtJoinMainResponse> mLudoTmtJoinMainResponseIApiListener = new IApiListener<LudoTmtJoinMainResponse>() {
        @Override
        public void onSuccess(LudoTmtJoinMainResponse response) {
            mView.onJoinLudoTournamentComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onJoinLudoTournamentFailure(error);
        }
    };

    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
