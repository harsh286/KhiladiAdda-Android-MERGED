package com.khiladiadda.ludo.buddy;

import com.khiladiadda.ludo.buddy.interfaces.ILudoBuddyPresenter;
import com.khiladiadda.ludo.buddy.interfaces.ILudoBuddyView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.LudoBuddyChallengeRequest;
import com.khiladiadda.network.model.response.BuddyResponse;

import rx.Subscription;

public class LudoBuddyPresenter implements ILudoBuddyPresenter {

    private ILudoBuddyView mView;
    private LudoBuddyInteractor mInteractor;
    private Subscription mSubscription;

    public LudoBuddyPresenter(ILudoBuddyView mView) {
        this.mView = mView;
        mInteractor = new LudoBuddyInteractor();
    }

    @Override public void getBuddyList(String contestType) {
        mSubscription = mInteractor.getBuddyList(mBuddyListener, contestType);
    }

    @Override public void sendChallengeRequest(LudoBuddyChallengeRequest request) {
        mSubscription = mInteractor.sendChallengeRequest(request, mChallengeListener);
    }

    @Override
    public void getBuddyListUniverse() {
        mSubscription=mInteractor.getBuddyListUniverse(mLUBuddyListener);


    }
    private IApiListener<BuddyResponse> mLUBuddyListener = new IApiListener<BuddyResponse>() {
        @Override
        public void onSuccess(BuddyResponse response) {
            mView.onLUBuddyListSuccess(response);

        }

        @Override
        public void onError(ApiError error) {
            mView.onLUBuddyListFailure(error);

        }
    };


    private IApiListener<BuddyResponse> mBuddyListener = new IApiListener<BuddyResponse>() {
        @Override public void onSuccess(BuddyResponse response) {
            mView.onBuddyListSuccess(response);
        }

        @Override public void onError(ApiError error) {
            mView.onBuddyListFailure(error);
        }
    };

    private IApiListener<BaseResponse> mChallengeListener = new IApiListener<BaseResponse>() {
        @Override public void onSuccess(BaseResponse response) {
            mView.onChallengeSuccess(response);
        }

        @Override public void onError(ApiError error) {
            mView.onChallengeFailure(error);
        }
    };

    @Override public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}