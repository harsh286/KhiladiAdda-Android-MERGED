package com.khiladiadda.ludo;

import com.khiladiadda.ludo.interfaces.ILudoChallengePresenter;
import com.khiladiadda.ludo.interfaces.ILudoChallengeView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.LudoContestRequest;
import com.khiladiadda.network.model.request.OpponentLudoRequest;
import com.khiladiadda.network.model.response.LudoContestResponse;

import rx.Subscription;

public class LudoChallengePresenter implements ILudoChallengePresenter {

    private ILudoChallengeView mView;
    private LudoChallengeInteractor mInteractor;
    private Subscription mSubscription;

    public LudoChallengePresenter(ILudoChallengeView mView) {
        this.mView = mView;
        mInteractor = new LudoChallengeInteractor();
    }

    @Override
    public void getContestList(String date, String contestType, boolean banner, String bannerType, boolean profile, int mode, int entryFees) {
        int from = 0, to= 0;
        int entry_fees_filter = 0;
        if (entryFees == 1) {
            from = 10;
            to = 100;
        } else if (entryFees == 2) {
            from = 101;
            to = 500;
        } else if (entryFees == 3) {
            from = 501;
            to = 1000;
        } else if (entryFees == 4) {
            from = 1001;
            to = 5000;
        } else if (entryFees == 5) {  //low to high
            entry_fees_filter = 1;
            from = 0;
            to = 0;
        } else if (entryFees == 6) {  //high to low
            entry_fees_filter = -1;
            from = 0;
            to = 0;
        }
        mSubscription = mInteractor.getLudoContestList(mGetLudoApiListener, date, contestType, banner, bannerType, profile, mode, entry_fees_filter, from, to);
    }

    @Override
    public void getAllContestList(String contestType, int page, int limit) {
        mSubscription = mInteractor.getAllLudoContestList(mGetLudoApiListener, contestType, page, limit);
    }

    @Override
    public void addChallenge(LudoContestRequest request) {
        mSubscription = mInteractor.addLudoChallenge(request, mAddChallengeResponseIListener);
    }

    @Override
    public void acceptContest(String contestId, OpponentLudoRequest ludoRequest) {
        mSubscription = mInteractor.joinLudoContest(contestId, ludoRequest, mJoinLudoContestResponseIApiListener);
    }

    @Override
    public void cancelContest(String contestId) {
        mSubscription = mInteractor.cancelLudoContest(contestId, mLudoCancelResponseIApiListener);
    }

    private IApiListener<LudoContestResponse> mGetLudoApiListener = new IApiListener<LudoContestResponse>() {
        @Override
        public void onSuccess(LudoContestResponse response) {
            mView.onGetContestSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onGetContestFailure(error);
        }
    };

    private IApiListener<BaseResponse> mAddChallengeResponseIListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.addChallengeSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.addChallengeFailure(error);
        }
    };

    private IApiListener<BaseResponse> mJoinLudoContestResponseIApiListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.acceptContestSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.acceptContestFailure(error);
        }
    };

    private IApiListener<BaseResponse> mLudoCancelResponseIApiListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.cancelContestSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.cancelContestFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}