package com.khiladiadda.network.model.response.droid_doresponse;

import com.khiladiadda.gameleague.interfaces.ILeaderBoardDroidoPresenter;
import com.khiladiadda.gameleague.interfaces.ILeaderBoardDroidoView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;

import rx.Subscription;

public class LeaderBoardPresenter implements ILeaderBoardDroidoPresenter {
    private ILeaderBoardDroidoView mLeaderBoardView;
    private LeaderBoardInteractor mLeaderBoardInteractor;
    private Subscription mSubscription;

    public LeaderBoardPresenter(ILeaderBoardDroidoView mLeaderBoardView) {
        this.mLeaderBoardView = mLeaderBoardView;
        mLeaderBoardInteractor = new LeaderBoardInteractor();
    }

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    //get LeaderBoardDroidoList
    @Override
    public void getLeaderBoardData(String tournamentId) {
        mSubscription = mLeaderBoardInteractor.getLeaderBoardDroidoList(mLeaderBoardDroidoListener, tournamentId);

    }

    private IApiListener<LeaderBoardDroidoResponse> mLeaderBoardDroidoListener = new IApiListener<LeaderBoardDroidoResponse>() {
        @Override
        public void onSuccess(LeaderBoardDroidoResponse response) {
            mLeaderBoardView.onParticipantsSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mLeaderBoardView.onParticipantsFailure(error);
        }
    };

}
