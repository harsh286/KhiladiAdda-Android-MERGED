package com.khiladiadda.ludoUniverse.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;
import com.khiladiadda.network.model.request.LudoContestRequest;
import com.khiladiadda.network.model.request.OpponentLudoRequest;

public interface ILudoUniversePresenter extends IBasePresenter {

    void getContestList(String date, String contestType, boolean banner, String bannerType, boolean profile, int mode, int entryFees, int fromMode);

    void getAllContestList(int page, int limit, int contestMode);

    void addChallenge(LudoContestRequest request);

    void acceptContest(String contestId, OpponentLudoRequest ludoRequest);

    void cancelContest(String contestId);

    void getStatus(String contestId);

    void getMode(String bannerType);

}