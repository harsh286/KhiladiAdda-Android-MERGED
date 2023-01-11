package com.khiladiadda.battle;

import com.khiladiadda.battle.interfaces.IBattlePresenter;
import com.khiladiadda.battle.interfaces.IBattleView;
import com.khiladiadda.battle.model.BannerResponse;
import com.khiladiadda.battle.model.BattleGroupResponse;
import com.khiladiadda.battle.model.BattleResponse;
import com.khiladiadda.battle.model.JoinGroupRequest;
import com.khiladiadda.battle.model.JoinGroupSubstituteRequest;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;

import rx.Subscription;

public class BattlePresenter implements IBattlePresenter {

    private IBattleView mView;
    private BattleInteractor mInteractor;
    private Subscription mSubscription;

    public BattlePresenter(IBattleView view) {
        mView = view;
        mInteractor = new BattleInteractor();
    }

    @Override public void getBattleList(String id, boolean isPlayed, boolean live, boolean past) {
        mSubscription = mInteractor.getBattle(mGetApiListener, id, isPlayed, live, past);
    }

    private IApiListener<BattleResponse> mGetApiListener = new IApiListener<BattleResponse>() {
        @Override public void onSuccess(BattleResponse response) {
            mView.onGetBattleListComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onGetBattleListFailure(error);
        }
    };



    @Override public void getGroupList(String id,boolean is_reverse, boolean profile) {
        mSubscription = mInteractor.getGroup(mGetGroupApiListener, id, is_reverse,profile);
    }

    private IApiListener<BattleGroupResponse> mGetGroupApiListener = new IApiListener<BattleGroupResponse>() {
        @Override public void onSuccess(BattleGroupResponse response) {
            mView.onGetGroupListComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onGetGroupListFailure(error);
        }
    };

    @Override public void joinBattleGroup(JoinGroupRequest request, String id) {
        mSubscription = mInteractor.joinGroup(mJoinGroupApiListener, request, id);
    }

    private IApiListener<BaseResponse> mJoinGroupApiListener = new IApiListener<BaseResponse>() {
        @Override public void onSuccess(BaseResponse response) {
            mView.onJoinComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onJoinFailure(error);
        }
    };

    @Override public void getCalculationBanner() {
        mSubscription = mInteractor.getCalculationBanner(mGetBannerApiListener);
    }

    private IApiListener<BannerResponse> mGetBannerApiListener = new IApiListener<BannerResponse>() {
        @Override public void onSuccess(BannerResponse response) {
            mView.onGetCalculationBannerComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onGetCalculationBannerFailure(error);
        }
    };

    @Override public void cancelGroup(String groupId) {
        mSubscription = mInteractor.cancelGroup(mCancelApiListener, groupId);
    }

    private IApiListener<BaseResponse> mCancelApiListener = new IApiListener<BaseResponse>() {
        @Override public void onSuccess(BaseResponse response) {
            mView.onCancelComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onCancelFailure(error);
        }
    };

    @Override public void joinGroupForSubstitute(JoinGroupSubstituteRequest request) {
        mSubscription = mInteractor.joinSubstituteGroup(mJoinSubstituteGroupApiListener, request);
    }

    private IApiListener<BaseResponse> mJoinSubstituteGroupApiListener = new IApiListener<BaseResponse>() {
        @Override public void onSuccess(BaseResponse response) {
            mView.onJoinSubstituteComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onJoinSubstituteFailure(error);
        }
    };

    @Override public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}