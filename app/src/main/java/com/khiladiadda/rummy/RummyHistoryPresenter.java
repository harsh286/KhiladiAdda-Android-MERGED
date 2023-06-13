package com.khiladiadda.rummy;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.request.RummyHelpRequest;
import com.khiladiadda.network.model.response.RummyCheckGameResponse;
import com.khiladiadda.network.model.response.RummyHelpResponse;
import com.khiladiadda.network.model.response.RummyHistoryMainResponse;
import com.khiladiadda.rummy.interfaces.IRummyHistoryPresenter;
import com.khiladiadda.rummy.interfaces.IRummyHistoryView;
import com.khiladiadda.rummy.interfaces.IRummyView;

import rx.Subscription;

public class RummyHistoryPresenter implements IRummyHistoryPresenter {
    private IRummyHistoryView mView;
    private RummyInteractor mInteractor;
    private Subscription mSubscription;

    public RummyHistoryPresenter(IRummyHistoryView mView) {
        this.mView = mView;
        mInteractor = new RummyInteractor();
    }

    @Override
    public void getRummyHistoryStatus() {
        mSubscription = mInteractor.getRummyHistory(mGetRummyStatusListener);
    }

    private IApiListener<RummyHistoryMainResponse> mGetRummyStatusListener = new IApiListener<RummyHistoryMainResponse>() {
        @Override
        public void onSuccess(RummyHistoryMainResponse response) {
            mView.onGetRummyHistorySuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onGetRummyHistoryFailure(error);
        }
    };

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
