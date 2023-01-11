package com.khiladiadda.depositelimit;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.deposite.DepositLimitMainResponse;
import com.khiladiadda.network.model.response.deposite.FetchDepositLimitMainResponse;

public interface IDepositView {
    void onDepositLimitComplete(DepositLimitMainResponse response);

    void onDepositLimitFailure(ApiError errorMsg);

    void onFetchDepositLimitComplete(FetchDepositLimitMainResponse response);

    void onFetchDepositLimitFailure(ApiError errorMsg);


}
