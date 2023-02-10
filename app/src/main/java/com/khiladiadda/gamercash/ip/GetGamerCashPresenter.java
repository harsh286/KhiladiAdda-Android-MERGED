package com.khiladiadda.gamercash.ip;

import com.khiladiadda.gamercash.interfaces.IGetGamerCashPresenter;
import com.khiladiadda.gamercash.interfaces.IGetGamerCashView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.GetGamerCashResponse;

import rx.Subscription;

public class GetGamerCashPresenter implements IGetGamerCashPresenter {
    private IGetGamerCashView mGetGamerCashView;
    private GetGamerCashUserInteractor mGetGamerCashUserInteractor;
    private Subscription mSubscription;

    public GetGamerCashPresenter(IGetGamerCashView mGetGamerCashView) {
        this.mGetGamerCashView = mGetGamerCashView;
        mGetGamerCashUserInteractor = new GetGamerCashUserInteractor();
    }

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void getGamerCashUserData() {
        mSubscription = mGetGamerCashUserInteractor.getGamerData(mPayGamerCashListener);
    }

    private IApiListener<GetGamerCashResponse> mPayGamerCashListener = new IApiListener<GetGamerCashResponse>() {
        @Override
        public void onSuccess(GetGamerCashResponse response) {
            mGetGamerCashView.onGetGamerCashSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mGetGamerCashView.onGetGamerCashFailure(error);
        }
    };


}
