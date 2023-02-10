package com.khiladiadda.callbreak;

import com.khiladiadda.callbreak.interfaces.ICallBreakPresenter;
import com.khiladiadda.callbreak.interfaces.ICallBreakView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.CallBreakJoinMainResponse;
import com.khiladiadda.network.model.response.CallBreakResponse;
import com.khiladiadda.rummy.RummyInteractor;
import com.khiladiadda.rummy.interfaces.IRummyPresenter;
import com.khiladiadda.rummy.interfaces.IRummyView;

import rx.Subscription;

public class CallBreakPresenter implements ICallBreakPresenter {

    private ICallBreakView mView;
    private CallBreakInteractor mInteractor;
    private Subscription mSubscription;

    public CallBreakPresenter(ICallBreakView mView) {
        this.mView = mView;
        mInteractor = new CallBreakInteractor();
    }

    @Override
    public void getCallBreak() {
        mSubscription = mInteractor.getCallBreak(mGetLudoApiListener);
    }

    private IApiListener<CallBreakResponse> mGetLudoApiListener = new IApiListener<CallBreakResponse>() {
        @Override
        public void onSuccess(CallBreakResponse response) {
            mView.onGetContestSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onGetContestFailure(error);
        }
    };

    @Override
    public void getCallBreakJoin(String id) {
        mSubscription = mInteractor.getCallBreakJoin(mGetCallBreakJoinApiListener, id);
    }

    private IApiListener<CallBreakJoinMainResponse> mGetCallBreakJoinApiListener = new IApiListener<CallBreakJoinMainResponse>() {
        @Override
        public void onSuccess(CallBreakJoinMainResponse response) {
            mView.onGetContestJoinSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onGetContestJoinFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}