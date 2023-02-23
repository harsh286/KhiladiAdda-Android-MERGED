package com.khiladiadda.leaderboard;

import com.khiladiadda.leaderboard.interfaces.ILeaderboardPresenter;
import com.khiladiadda.leaderboard.interfaces.ILeaderboardView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.AllLeaderBoardResponse;
import com.khiladiadda.network.model.response.LeaderboardMainResponse;
import com.khiladiadda.network.model.response.LudoAddaMainResponse;
import com.khiladiadda.network.model.response.LudoLeaderboardResponse;
import com.khiladiadda.network.model.response.OverallLeadBoardResponse;
import com.khiladiadda.network.model.response.hth.LeaderBoardHthResponse;

import rx.Subscription;

public class LeaderboardPresenter implements ILeaderboardPresenter {

    private ILeaderboardView mView;
    private LeaderboardInteractor mInteractor;
    private Subscription mSubscription;

    public LeaderboardPresenter(ILeaderboardView view) {
        mView = view;
        mInteractor = new LeaderboardInteractor();
    }

    @Override
    public void getQuizAll(int page, int limit) {
        mSubscription = mInteractor.getQuizAll(mAllApiListener, page, limit);
    }

    @Override
    public void getQuizDaily(int page, int limit) {
        mSubscription = mInteractor.getQuizMonth(mAllApiListener, page, limit, 3);
    }

    @Override
    public void getQuizWeekly(int page, int limit) {
        mSubscription = mInteractor.getQuizMonth(mAllApiListener, page, limit, 2);
    }

    @Override
    public void getQuizMonth(int page, int limit) {
        mSubscription = mInteractor.getQuizMonth(mAllApiListener, page, limit, 0);
    }

    @Override
    public void getGameAll(String id, int page, int limit) {
        mSubscription = mInteractor.getGameAll(mAllApiListener, id, page, limit, 0);
    }

    @Override
    public void getGameDaily(String id, int page, int limit) {
        mSubscription = mInteractor.getGameAll(mAllApiListener, id, page, limit, 3);
    }

    @Override
    public void getGameWeekly(String id, int page, int limit) {
        mSubscription = mInteractor.getGameAll(mAllApiListener, id, page, limit, 2);
    }

    @Override
    public void getGameMonth(String id, int page, int limit) {
        mSubscription = mInteractor.getGameMonth(mAllApiListener, id, page, limit);
    }

    @Override
    public void getGameById(String id, int page, int limit) {
        mSubscription = mInteractor.getGameById(mAllApiListener, id, page, limit);
    }

    @Override
    public void getLudo(int page, int limit, String type, int contestType) {
        mSubscription = mInteractor.getLudo(mLudoApiListener, page, limit, type, contestType);
    }

    @Override
    public void getFanBattle(int page, int limit, int type) {
        mSubscription = mInteractor.getFanBattle(mFanBattleListener, type, page, limit);
    }

    @Override
    public void getHTHBattles(int page, int limit, String type) {
        mSubscription = mInteractor.getHTHBattle(mHTHApiListener, page, limit, type);
    }

    @Override
    public void getLudoAdda(int page, int limit, String type) {
        mSubscription = mInteractor.getLudoAdda(mLudoAddaListerner, page, limit, type);
    }


    private IApiListener<LudoAddaMainResponse> mLudoAddaListerner = new IApiListener<LudoAddaMainResponse>() {
        @Override
        public void onSuccess(LudoAddaMainResponse response) {
            mView.onLeaderLudoAddaComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onLeaderLudoAdda(error);
        }
    };

    @Override
    public void getWS(int page, int limit, String type) {
        mSubscription = mInteractor.getWS(mWSListerner, page, limit, type);


    }

    @Override
    public void getDroidDo(int page, int limit, String type) {
        mSubscription = mInteractor.getDroid_Do(mDroidListerner, page, limit, type);

    }

    @Override
    public void getLudoTournament(String type, int page, int limit) {
        mSubscription = mInteractor.getLeaderBoardLudoTournament(mAllLeaderboardListerner, page, limit, type);
    }

    @Override
    public void getCourtPiece(String type, int page, int limit) {
        mSubscription = mInteractor.getLeaderBoardCallBreak(mAllLeaderboardListerner, page, limit, type);
    }

    @Override
    public void getRummy(String type, int page, int limit) {
        mSubscription = mInteractor.getLeaderBoardRummy(mAllLeaderboardListerner, page, limit, type);
    }


    private IApiListener<LeaderboardMainResponse> mAllLeaderboardListerner = new IApiListener<LeaderboardMainResponse>() {
        @Override
        public void onSuccess(LeaderboardMainResponse response) {
            mView.onAllLeaderBoardComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onAllLeaderBoardError(error);
        }
    };


    private IApiListener<AllLeaderBoardResponse> mWSListerner = new IApiListener<AllLeaderBoardResponse>() {
        @Override
        public void onSuccess(AllLeaderBoardResponse response) {
            mView.onLeaderWSComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onLeaderWS(error);
        }
    };
    private IApiListener<AllLeaderBoardResponse> mDroidListerner = new IApiListener<AllLeaderBoardResponse>() {
        @Override
        public void onSuccess(AllLeaderBoardResponse response) {
            mView.onLeaderWSComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onLeaderWS(error);
        }
    };

    private IApiListener<LeaderBoardHthResponse> mHTHApiListener = new IApiListener<LeaderBoardHthResponse>() {
        @Override
        public void onSuccess(LeaderBoardHthResponse response) {
            mView.onLeaderHTHBattleComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onLeaderHTHBattleFailure(error);
        }
    };

    private IApiListener<LudoLeaderboardResponse> mLudoApiListener = new IApiListener<LudoLeaderboardResponse>() {
        @Override
        public void onSuccess(LudoLeaderboardResponse response) {
            mView.onLudoLeaderBoardComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onLudoLeaderBoardFailure(error);
        }
    };

    private IApiListener<OverallLeadBoardResponse> mFanBattleListener = new IApiListener<OverallLeadBoardResponse>() {
        @Override
        public void onSuccess(OverallLeadBoardResponse response) {
            mView.onLeaderFanBattleComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onLeaderFanBattleFailure(error);

        }
    };

    private IApiListener<AllLeaderBoardResponse> mAllApiListener = new IApiListener<AllLeaderBoardResponse>() {
        @Override
        public void onSuccess(AllLeaderBoardResponse response) {
            mView.onLeaderBoardComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onLeaderBoardFailure(error);
        }
    };


    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}