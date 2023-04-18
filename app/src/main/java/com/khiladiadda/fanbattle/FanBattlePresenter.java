package com.khiladiadda.fanbattle;

import com.khiladiadda.fanbattle.interfaces.IFanBattlePresenter;
import com.khiladiadda.fanbattle.interfaces.IFanBattleView;
import com.khiladiadda.network.model.response.MasterResponse;
import com.khiladiadda.network.model.response.MatchResponse;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;

import rx.Subscription;

public class FanBattlePresenter implements IFanBattlePresenter {

    private IFanBattleView mView;
    private FanBattleInteractor mInteractor;
    private Subscription mSubscription;

    public FanBattlePresenter(IFanBattleView view) {
        mView = view;
        mInteractor = new FanBattleInteractor();
    }

    @Override public void getMatchList(String id) {
        mSubscription = mInteractor.getCategory(mGetApiListener, id);
    }

    private IApiListener<MatchResponse> mGetApiListener = new IApiListener<MatchResponse>() {
        @Override public void onSuccess(MatchResponse response) {
            mView.onGetMatchListComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onGetMatchListFailure(error);
        }
    };

    @Override public void getMasterData() {
        mSubscription = mInteractor.getMaster(mMasterApiListener);
    }

    private IApiListener<MasterResponse> mMasterApiListener = new IApiListener<MasterResponse>() {
        @Override public void onSuccess(MasterResponse response) {
            mView.onMasterComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onMasterFailure(error);
        }
    };

    @Override public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}