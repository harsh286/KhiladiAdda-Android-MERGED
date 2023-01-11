package com.khiladiadda.ludo.result.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;
import com.khiladiadda.network.model.request.LudoResultRequest;
import com.khiladiadda.network.model.request.LudoUpdateRequest;

public interface ILudoResultPresenter extends IBasePresenter {

    void cancelLudoContest(String contestId);

    void updateLudoContest(String contestId, LudoUpdateRequest updateRequest);

    void updateLudoResult(String contestId, LudoResultRequest request);

}