package com.khiladiadda.ludoUniverse;

import com.khiladiadda.ludoUniverse.interfaces.ILudoUniversePresenter;
import com.khiladiadda.ludoUniverse.interfaces.ILudoUniverseView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.LudoContestRequest;
import com.khiladiadda.network.model.request.OpponentLudoRequest;
import com.khiladiadda.network.model.response.LudoContestResponse;
import com.khiladiadda.network.model.response.ModeResponse;

import rx.Subscription;

public class LudoUniversePresenter implements ILudoUniversePresenter {

    private ILudoUniverseView mView;
    private LudoUniverseInteractor mInteractor;
    private Subscription mSubscription;

    public LudoUniversePresenter(ILudoUniverseView mView) {
        this.mView = mView;
        mInteractor = new LudoUniverseInteractor();
    }

    @Override
    public void getContestList(String date, String contestType, boolean banner, String bannerType, boolean profile, int mode, int entryFees, int fromMode) {
        int from = 0, to = 0;
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
        mSubscription = mInteractor.getLudoContestList(mGetLudoApiListener, date, contestType, banner, bannerType, profile, mode, entry_fees_filter, from, to, fromMode);
    }

    @Override
    public void getAllContestList(int page, int limit, int contestMode) {
        mSubscription = mInteractor.getAllLudoContestList(mGetLudoApiListener, page, limit, contestMode);
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

    @Override
    public void getStatus(String contestId) {
        mSubscription = mInteractor.getStatusLudoContest(contestId, mGetLudoStatusApiListener);
    }

    private IApiListener<BaseResponse> mGetLudoStatusApiListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.JoinedContestStatusSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.JoinedContestStatusFailure(error);

        }
    };

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
    public void getMode(String bannerType) {
        mSubscription = mInteractor.getMode(mGetModeApiListener, bannerType);
    }

    private IApiListener<ModeResponse> mGetModeApiListener = new IApiListener<ModeResponse>() {
        @Override
        public void onSuccess(ModeResponse response) {
            mView.onGetModeSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onGetModeFailure(error);
        }
    };


    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}