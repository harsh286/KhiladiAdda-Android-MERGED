package com.khiladiadda.wallet.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.InvoiceResponse;
import com.khiladiadda.network.model.response.ProfileTransactionResponse;
import com.khiladiadda.network.model.response.VersionResponse;

public interface IWalletCashbackView {

    String getAmount();

    void onValidationComplete();

    void onValidationFailure(String errorMsg);

    void onProfileComplete(ProfileTransactionResponse responseModel);

    void onProfileFailure(ApiError error);

    void onVersionSuccess(VersionResponse response);

    void onVersionFailure(ApiError error);

    void onInvoiceComplete(InvoiceResponse responseModel);

    void onInvoiceFailure(ApiError error);

    void onApplyCouponComplete(BaseResponse responseModel);

    void onApplyCouponFailure(ApiError error);

}