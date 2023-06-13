package com.khiladiadda.rummy;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.request.RummyHelpRequest;
import com.khiladiadda.network.model.response.RummyHelpResponse;
import com.khiladiadda.rummy.interfaces.IRummyHelpRulePresenter;
import com.khiladiadda.rummy.interfaces.IRummyHelpRuleView;
import com.khiladiadda.rummy.interfaces.IRummyHistoryPresenter;
import com.khiladiadda.rummy.interfaces.IRummyHistoryView;
import com.khiladiadda.rummy.interfaces.IRummyView;

import rx.Subscription;

public class RummyHelpRulePresenter implements IRummyHelpRulePresenter {
    private IRummyHelpRuleView mView;
    private RummyInteractor mInteractor;
    private Subscription mSubscription;

    public RummyHelpRulePresenter(IRummyHelpRuleView mView) {
        this.mView = mView;
        mInteractor = new RummyInteractor();
    }

    @Override
    public void getRummyHelpRuleStatus(RummyHelpRequest request) {
        mSubscription = mInteractor.getRummyHelpRule(mGetRummyHelpRuleListener, request);
    }

    private IApiListener<RummyHelpResponse> mGetRummyHelpRuleListener = new IApiListener<RummyHelpResponse>() {
        @Override
        public void onSuccess(RummyHelpResponse response) {
            mView.onGetRummyHelpRuleSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mView.onGetRummyHelpRuleFailure(error);
        }
    };

}
