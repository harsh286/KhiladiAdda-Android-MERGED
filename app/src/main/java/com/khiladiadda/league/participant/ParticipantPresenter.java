package com.khiladiadda.league.participant;

import com.khiladiadda.league.participant.ParticipantInteractor;
import com.khiladiadda.league.participant.interfaces.IParticipantPresenter;
import com.khiladiadda.league.participant.interfaces.IParticipantView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.request.FBBattleParticipantRequest;
import com.khiladiadda.network.model.response.FBParticipantResponse;
import com.khiladiadda.network.model.response.ParticipantResponse;
import com.khiladiadda.network.model.response.QuizParticipantResponse;
import com.khiladiadda.network.model.response.TeamResponse;

import rx.Subscription;

public class ParticipantPresenter implements IParticipantPresenter {

    private IParticipantView mView;
    private ParticipantInteractor mInteractor;
    private Subscription mSubscription;

    public ParticipantPresenter(IParticipantView view) {
        mView = view;
        mInteractor = new ParticipantInteractor();
    }

    @Override
    public void getParticipant(String gameId) {
        mSubscription = mInteractor.getParticipant(gameId, mAddApiListener);
    }

    @Override
    public void getTeam(String gameId) {
        mSubscription = mInteractor.getTeam(gameId, mTeamApiListener);
    }

    @Override
    public void getQuizParticipant(String gameId, int page, int limit) {
        mSubscription = mInteractor.getQuizParticipant(gameId, mQuizApiListener, page, limit);
    }

    private IApiListener<ParticipantResponse> mAddApiListener = new IApiListener<ParticipantResponse>() {
        @Override
        public void onSuccess(ParticipantResponse response) {
            mView.onParticipantComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onParticipantFailure(error);
        }
    };

    private IApiListener<TeamResponse> mTeamApiListener = new IApiListener<TeamResponse>() {
        @Override
        public void onSuccess(TeamResponse response) {
            mView.onTeamComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onTeamFailure(error);
        }
    };

    private IApiListener<QuizParticipantResponse> mQuizApiListener = new IApiListener<QuizParticipantResponse>() {
        @Override
        public void onSuccess(QuizParticipantResponse response) {
            mView.onQuizParticipantComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onQuizParticipantFailure(error);
        }
    };

    @Override
    public void getFBGroupParticipant(String groupId, int page, int limit,boolean isLeaderboard) {
        FBBattleParticipantRequest request = new FBBattleParticipantRequest();
        request.setBattleId("");
        request.setMatchid("");
        request.setGroupId(groupId);
        request.setIsleaderboard(false);
        mSubscription = mInteractor.getFBGroupParticipant(request, page, limit,isLeaderboard, mGetFBGroupApiListener);
    }

    private IApiListener<FBParticipantResponse> mGetFBGroupApiListener = new IApiListener<FBParticipantResponse>() {
        @Override
        public void onSuccess(FBParticipantResponse response) {
            mView.onFBGroupParticipantComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onFBGroupParticipantFailure(error);
        }
    };

    @Override
    public void getFBBattleParticipant(String battleId, int page, int limit) {
        FBBattleParticipantRequest request = new FBBattleParticipantRequest();
        request.setBattleId(battleId);
        request.setGroupId("");
        request.setMatchid("");
        request.setIsleaderboard(false);
        mSubscription = mInteractor.getFBBattleParticipant(request, page, limit, mGetFBBattleApiListener);
    }

    @Override
    public void getFBMatchParticipant(String matchId, int page, int limit) {
        FBBattleParticipantRequest request = new FBBattleParticipantRequest();
        request.setMatchid(matchId);
        request.setGroupId("");
        request.setBattleId("");
        request.setIsleaderboard(false);
        mSubscription = mInteractor.getFBMatchParticipant(request, page, limit, mGetFBMatchApiListener);
    }

    private IApiListener<FBParticipantResponse> mGetFBBattleApiListener = new IApiListener<FBParticipantResponse>() {
        @Override
        public void onSuccess(FBParticipantResponse response) {
            mView.onFBBattleParticipantComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onFBBattleParticipantFailure(error);
        }
    };
    private IApiListener<FBParticipantResponse> mGetFBMatchApiListener = new IApiListener<FBParticipantResponse>() {
        @Override
        public void onSuccess(FBParticipantResponse response) {
            mView.onFBMatchParticipantComplete(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onFBMatchParticipantFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}