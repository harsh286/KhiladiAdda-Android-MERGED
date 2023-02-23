package com.khiladiadda.rummy;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.RummyCheckGameResponse;
import com.khiladiadda.network.model.response.RummyRefreshTokenMainResponse;
import com.khiladiadda.network.model.response.RummyResponse;
import com.khiladiadda.rummy.interfaces.IRummyPresenter;
import com.khiladiadda.rummy.interfaces.IRummyView;

import rx.Subscription;

public class RummyPresenter implements IRummyPresenter {

    private IRummyView mView;
    private RummyInteractor mInteractor;
    private Subscription mSubscription;

    public RummyPresenter(IRummyView mView) {
        this.mView = mView;
        mInteractor = new RummyInteractor();
    }

    @Override
    public void getRummyList(String arenaType) {
        mSubscription = mInteractor.getRummyList(mGetLudoApiListener, arenaType);
    }

    private IApiListener<RummyResponse> mGetLudoApiListener = new IApiListener<RummyResponse>() {
        @Override
        public void onSuccess(RummyResponse response) {
            mView.onGetContestSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onGetContestFailure(error);
        }
    };

    @Override
    public void getRummyRefreshToken() {
        mSubscription = mInteractor.getRummyRefreshToken(mGetRummyRefreshTokenApiListener);
    }

    private IApiListener<RummyRefreshTokenMainResponse> mGetRummyRefreshTokenApiListener = new IApiListener<RummyRefreshTokenMainResponse>() {
        @Override
        public void onSuccess(RummyRefreshTokenMainResponse response) {
            mView.onGetContestRefreshTokenSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onGetContestRefreshTokenFailure(error);
        }
    };



    @Override
    public void getCheckGameStatus() {
        mSubscription = mInteractor.getCheckGameStatus(mGetLudoCheckApiListener);
    }

    private IApiListener<RummyCheckGameResponse> mGetLudoCheckApiListener = new IApiListener<RummyCheckGameResponse>() {
        @Override
        public void onSuccess(RummyCheckGameResponse response) {
            mView.onGetContestCheckGameSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onGetContestCheckGameFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}