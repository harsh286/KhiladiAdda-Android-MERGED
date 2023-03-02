package com.khiladiadda.network.model.response.droid_doresponse;

import com.khiladiadda.gameleague.interfaces.IDridoHistoryPresenter;
import com.khiladiadda.gameleague.interfaces.IDridoHistoryView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;

import rx.Subscription;

public class DridoHistoryPresenter implements IDridoHistoryPresenter {
    private IDridoHistoryView mDridoHistoryView;
    private DridoHistoryInteractor mDridoHistoryInteractor;
    private Subscription mSubscription;

    public DridoHistoryPresenter(IDridoHistoryView mDridoHistoryView) {
        this.mDridoHistoryView = mDridoHistoryView;
        mDridoHistoryInteractor = new DridoHistoryInteractor();
    }

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    //get DroidoHistoryGamesList
    @Override
    public void getDroidoHistoryData() {
        mSubscription = mDridoHistoryInteractor.getDroidoHistoryGameList(mDroidoHistoryGameList);

    }


    private IApiListener<DroidoHistoryGameList> mDroidoHistoryGameList = new IApiListener<DroidoHistoryGameList>() {
        @Override
        public void onSuccess(DroidoHistoryGameList response) {
            mDridoHistoryView.onDroidoHistoryListSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mDridoHistoryView.onDroidoHistoryListFailure(error);
        }
    };


}
