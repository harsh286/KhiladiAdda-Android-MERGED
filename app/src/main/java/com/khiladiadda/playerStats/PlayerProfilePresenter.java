package com.khiladiadda.playerStats;

import com.khiladiadda.network.model.CricAPiListerner;
import com.khiladiadda.network.model.CricApiError;
import com.khiladiadda.playerStats.model.PlayerProfileRes;

import rx.Subscription;

public class PlayerProfilePresenter implements IPlayerProfile {

    private IPlayerProfile.IPlayerProfileView mView;
    private PlayerProfileIntracter mInteractor;

    public PlayerProfilePresenter(IPlayerProfile.IPlayerProfileView mView) {
        this.mView = mView;
        mInteractor = new PlayerProfileIntracter();
    }

    public void check(String api, int pid) {
        Subscription mAddSubscription = mInteractor.getplayerprofile(mCricAPiLister, api, pid);
    }

    public IPlayerProfile.IPlayerProfileView getmView() {
        return mView;
    }

    private CricAPiListerner<PlayerProfileRes> mCricAPiLister = new CricAPiListerner<PlayerProfileRes>() {
        @Override
        public void onSuccess(PlayerProfileRes response) {
            mView.onSuccess(response);
        }

        @Override
        public void onError(CricApiError error) {
            mView.onFailure(error);
        }
    };

}