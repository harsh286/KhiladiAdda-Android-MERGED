package com.khiladiadda.leaderboard.past;

import com.khiladiadda.leaderboard.past.interfaces.IPastLeaderboardPresenter;
import com.khiladiadda.leaderboard.past.interfaces.IPastLeaderboardView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.SquadLeaderbordResponse;

import rx.Subscription;

public class PastLeaderboardPresenter implements IPastLeaderboardPresenter {

    private IPastLeaderboardView mView;
    private PastLeaderboardInteractor mInteractor;
    private Subscription mQuizAllSubscription;

    public PastLeaderboardPresenter(IPastLeaderboardView view) {
        mView = view;
        mInteractor = new PastLeaderboardInteractor();
    }

    @Override
    public void getLeaderboard(String id, int page, int limit) {
        mQuizAllSubscription = mInteractor.getPastLeaderboard(mAllApiListener, id, page, limit);
    }

    private IApiListener<SquadLeaderbordResponse> mAllApiListener = new IApiListener<SquadLeaderbordResponse>() {
        @Override
        public void onSuccess(SquadLeaderbordResponse response) {
            mView.onPastLeaderBoardComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onPastLeaderBoardFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mQuizAllSubscription != null && !mQuizAllSubscription.isUnsubscribed()) {
            mQuizAllSubscription.unsubscribe();
        }
    }

}