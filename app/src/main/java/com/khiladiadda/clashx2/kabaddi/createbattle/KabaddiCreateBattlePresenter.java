package com.khiladiadda.clashx2.kabaddi.createbattle;

import com.khiladiadda.clashx2.kabaddi.createbattle.interfaces.IFootballCreateBattlePresenter;
import com.khiladiadda.clashx2.kabaddi.createbattle.interfaces.IFootballCreateBattleView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.hth.CreateBattleRequest;
import com.khiladiadda.network.model.request.hth.UpdateOpponentPlayers;
import com.khiladiadda.network.model.response.hth.BattleResponseHTH;
import com.khiladiadda.network.model.response.hth.CreateBattleResponse;
import com.khiladiadda.network.model.response.hth.HTHMainResponse;

import rx.Subscription;

public class KabaddiCreateBattlePresenter implements IFootballCreateBattlePresenter {

    private IFootballCreateBattleView mView;
    private KabaddiCreateBattleInteractor mInteractor;
    private Subscription mSubscription;

    public KabaddiCreateBattlePresenter(IFootballCreateBattleView view) {
        mView = view;
        mInteractor = new KabaddiCreateBattleInteractor();
    }

    @Override
    public void createBattle(CreateBattleRequest request) {
        mSubscription = mInteractor.createBattle(mCreateGroupApiListener, request);
    }

    private IApiListener<CreateBattleResponse> mCreateGroupApiListener = new IApiListener<CreateBattleResponse>() {
        @Override
        public void onSuccess(CreateBattleResponse response) {
            mView.onJoinComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onJoinFailure(error);
        }
    };

    @Override
    public void updateBattleGroup(CreateBattleRequest request, String groupId) {
        mSubscription = mInteractor.updateBattle(mUpdateGroupApiListener, request, groupId);
    }

    private IApiListener<BaseResponse> mUpdateGroupApiListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onUpdateComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onUpdateFailure(error);
        }
    };

    @Override
    public void acceptBattleGroup(CreateBattleRequest request) {
        mSubscription = mInteractor.acceptBattle(mAcceptGroupApiListener, request);
    }

    private IApiListener<CreateBattleResponse> mAcceptGroupApiListener = new IApiListener<CreateBattleResponse>() {
        @Override
        public void onSuccess(CreateBattleResponse response) {
            mView.onAcceptComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onAllBattlesFailure(error);
        }
    };

    @Override
    public void getALLBattles(String matchId, String battleId, boolean user, boolean highestBattle, boolean lowestBattle, boolean newestBattle) {
        mSubscription = mInteractor.getAllBattles(mAllBattlesApiListener, matchId, battleId, user, highestBattle, lowestBattle, newestBattle);
    }

    @Override
    public void getLeguesBattles(boolean upcoming, boolean past, boolean live) {
        mSubscription = mInteractor.getMyLegues(mAllLegues, upcoming, past, live);
    }

    @Override
    public void updateOpponentPlayer(String id, UpdateOpponentPlayers request) {
        mSubscription = mInteractor.UpdatePlayer(mUpdatePlayer, id, request);
    }

    @Override
    public void getPlayerStatus(String id, UpdateOpponentPlayers players) {
        mSubscription = mInteractor.PlayerStatus(mPlayerStatus, id, players);
    }

    private IApiListener<HTHMainResponse> mPlayerStatus = new IApiListener<HTHMainResponse>() {
        @Override
        public void onSuccess(HTHMainResponse response) {
            mView.onPlayerStatus(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onPlayerStatusError(error);
        }
    };
    private IApiListener<BaseResponse> mUpdatePlayer = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onUpdatePLayers(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onUpdatePLayerError(error);
        }
    };

    private IApiListener<HTHMainResponse> mAllLegues = new IApiListener<HTHMainResponse>() {
        @Override
        public void onSuccess(HTHMainResponse response) {
            mView.onFetchLegues(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onFetchLegues(error);

        }
    };

    private IApiListener<BattleResponseHTH> mAllBattlesApiListener = new IApiListener<BattleResponseHTH>() {
        @Override
        public void onSuccess(BattleResponseHTH response) {
            mView.onAllBattlesComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onAcceptFailure(error);
        }
    };

    @Override
    public void getMyBattles(String matchId, String battleId, boolean user, boolean highestBattle, boolean lowestBattle, boolean newestBattle) {
        mSubscription = mInteractor.getMyBattles(mMyBattlesApiListener, matchId, battleId, user, highestBattle, lowestBattle, newestBattle);
    }

    private IApiListener<BattleResponseHTH> mMyBattlesApiListener = new IApiListener<BattleResponseHTH>() {
        @Override
        public void onSuccess(BattleResponseHTH response) {
            mView.onMyBattlesComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onMyBattlesFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}