package com.khiladiadda.ludo.buddy.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;
import com.khiladiadda.network.model.request.LudoBuddyChallengeRequest;

public interface ILudoBuddyPresenter extends IBasePresenter {

    void getBuddyList(String contestType);

    void sendChallengeRequest(LudoBuddyChallengeRequest ludoRequest);

    void getBuddyListUniverse(int mode);
}