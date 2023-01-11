package com.khiladiadda.gameleague.ip;

import com.khiladiadda.gameleague.interfaces.IDridoPresenter;
import com.khiladiadda.gameleague.interfaces.IDridoView;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.droid_doresponse.ItemGamesMainResponse;

import rx.Subscription;

public class DridoPresenter implements IDridoPresenter {
    private IDridoView mDridoView;
    private DridoInteractor mDridoInteractor;
    private Subscription mSubscription;

    public DridoPresenter(IDridoView mDridoView) {
        this.mDridoView = mDridoView;
        mDridoInteractor = new DridoInteractor();
    }

    @Override
    public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
//get gamesList
    @Override
    public void getGameData() {
        mSubscription = mDridoInteractor.getGameList(mGameListListener);
    }


    private final IApiListener<ItemGamesMainResponse> mGameListListener = new IApiListener<ItemGamesMainResponse>() {
        @Override
        public void onSuccess(ItemGamesMainResponse response) {
            mDridoView.onGameListSuccess(response);
        }

        @Override
        public void onError(ApiError error) {
            mDridoView.onGameListFailure(error);
        }
    };


}
