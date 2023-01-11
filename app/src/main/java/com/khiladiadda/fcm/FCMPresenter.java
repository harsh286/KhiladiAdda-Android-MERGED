package com.khiladiadda.fcm;

import com.khiladiadda.fanleague.MyFanLeagueInteractor;
import com.khiladiadda.fanleague.interfaces.IMyFanLeaguePresenter;
import com.khiladiadda.fanleague.interfaces.IMyFanLeagueView;
import com.khiladiadda.fcm.interfaces.IFCMPresenter;
import com.khiladiadda.fcm.interfaces.IFCMView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.CricAPiListerner;
import com.khiladiadda.network.model.CricApiError;
import com.khiladiadda.network.model.request.FcmRequest;
import com.khiladiadda.network.model.response.CricScorce;
import com.khiladiadda.network.model.response.MatchResponse;

import rx.Subscription;

public class FCMPresenter implements IFCMPresenter {

    private IFCMView mView;
    private FCMInteractor mInteractor;
    private Subscription mAddSubscription;

    public FCMPresenter(IFCMView view) {
        mView = view;
        mInteractor = new FCMInteractor();
    }

    @Override
    public void updateLeague(boolean leagueDisabled) {
        FcmRequest request = new FcmRequest();
        request.setLeagueDisabled(leagueDisabled);
        mAddSubscription = mInteractor.updateLeague(mAddApiListener, request);
    }

    private IApiListener<BaseResponse> mAddApiListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onFcmUpdateComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onFcmUpdateFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mAddSubscription != null && !mAddSubscription.isUnsubscribed()) {
            mAddSubscription.unsubscribe();
        }
    }

}