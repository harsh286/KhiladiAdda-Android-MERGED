package com.khiladiadda.depositelimit;

import com.khiladiadda.base.interfaces.IBasePresenter;
import com.khiladiadda.network.model.request.deposite.DepositLimitRequest;

public interface IDepositeLimitPresenter extends IBasePresenter {

    void getAddDepositPresenter(DepositLimitRequest depositLimitRequest);
    void getFetchDepositPresenter();

}