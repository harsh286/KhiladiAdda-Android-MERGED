package com.khiladiadda.ludo.result;

import com.khiladiadda.ludo.result.interfaces.ILudoResultPresenter;
import com.khiladiadda.ludo.result.interfaces.ILudoResultView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.LudoResultRequest;
import com.khiladiadda.network.model.request.LudoUpdateRequest;

import rx.Subscription;

public class LudoResultPresenter implements ILudoResultPresenter {

    private ILudoResultView mView;
    private LudoResultInteractor mInteractor;
    private Subscription mSubscription;

    public LudoResultPresenter(ILudoResultView mView) {
        this.mView = mView;
        mInteractor = new LudoResultInteractor();
    }

    @Override
    public void cancelLudoContest(String contestId) {
        mSubscription = mInteractor.cancelLudoContest(contestId, mLudoCancelResponseIApiListener);
    }

    @Override
    public void updateLudoContest(String contestId, LudoUpdateRequest updateRequest) {
        mSubscription = mInteractor.updateLudoContest(contestId, updateRequest, mUpdateLudoApiListener);
    }

    @Override
    public void updateLudoResult(String contestId, LudoResultRequest resultRequest) {
        mSubscription = mInteractor.ludoResult(contestId, resultRequest, mLudoResultAPIListener);
    }

    private IApiListener<BaseResponse> mUpdateLudoApiListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onLudoUpdateSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onLudoUpdateFailure(error);
        }
    };

    private IApiListener<BaseResponse> mLudoResultAPIListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.onLudoResultSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onLudoResultFailure(error);
        }
    };

    private IApiListener<BaseResponse> mLudoCancelResponseIApiListener = new IApiListener<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse response) {
            mView.cancelLudoContestSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.cancelLudoContestFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}