package com.khiladiadda.clashx2.main;

import com.khiladiadda.clashx2.main.interfaces.IHTHBattlePresenter;
import com.khiladiadda.clashx2.main.interfaces.IHTHBattleView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.HTHCancelResponse;
import com.khiladiadda.network.model.response.hth.HTHMainResponse;
import com.khiladiadda.network.model.response.hth.Result;

import rx.Subscription;

public class HTHPresenter implements IHTHBattlePresenter {

    private IHTHBattleView mView;
    private HTHInteractor mInteractor;
    private Subscription mSubscription;

    public HTHPresenter(IHTHBattleView view) {
        mView = view;
        mInteractor = new HTHInteractor();
    }

    @Override
    public void getHTHMatchList(String id, int type) {
        mSubscription = mInteractor.getHTHMatches(mGetApiListener, id, type);
    }

    @Override
    public void onCancelBattle(String id) {
        mSubscription = mInteractor.onCancelBattle(mCancelApiListener, id);
    }

    @Override
    public void getBattleResult(String id) {
        mSubscription = mInteractor.onResultBattle(mGetBattleResultlApiListener, id);
    }

    private IApiListener<Result> mGetBattleResultlApiListener = new IApiListener<Result>() {
        @Override
        public void onSuccess(Result response) {
            mView.onGetResultBattle(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onResultBattleFailure(error);
        }
    };

    private IApiListener<HTHCancelResponse> mCancelApiListener = new IApiListener<HTHCancelResponse>() {
        @Override
        public void onSuccess(HTHCancelResponse response) {
            mView.onCancelBattle(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onCancelBattleFailure(error);
        }
    };

    private IApiListener<HTHMainResponse> mGetApiListener = new IApiListener<HTHMainResponse>() {
        @Override
        public void onSuccess(HTHMainResponse response) {
            mView.onGetHTHMatchListComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onGetHTHMatchListFailure(error);
        }
    };

    @Override
    public void getMatchStatus(String id) {
        mSubscription = mInteractor.getMatchStatus(mMatchStatusApiListener, id);
    }

    private IApiListener<HTHMainResponse> mMatchStatusApiListener = new IApiListener<HTHMainResponse>() {
        @Override
        public void onSuccess(HTHMainResponse response) {
            mView.onMatchStatus(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onMatchStatusError(error);

        }
    };

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}