package com.khiladiadda.ludo.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;
import com.khiladiadda.network.model.request.LudoContestRequest;
import com.khiladiadda.network.model.request.OpponentLudoRequest;

public interface ILudoChallengePresenter extends IBasePresenter {

    void getContestList(String date, String contestType, boolean banner, String bannerType, boolean profile, int mode, int entryFees);

    void getAllContestList(String contestType, int page, int limit);

    void addChallenge(LudoContestRequest request);

    void acceptContest(String contestId, OpponentLudoRequest ludoRequest);

    void cancelContest(String contestId);

}