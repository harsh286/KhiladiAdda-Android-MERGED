package com.khiladiadda.transaction.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.InvoiceResponse;
import com.khiladiadda.network.model.response.PaymentHistoryDetails;
import com.khiladiadda.network.model.response.PaymentHistoryResponse;
import com.khiladiadda.network.model.response.WalletTransactionResponse;
import com.khiladiadda.network.model.response.WithdrawResponse;

public interface ITransactionView {

    void onAllTransactionComplete(WalletTransactionResponse responseModel);

    void onAllTransactionFailure(ApiError error);

    void onWithdrawHistoryComplete(WithdrawResponse responseModel);

    void onWithdrawHistoryFailure(ApiError error);

    void onPaymentHistoryComplete(PaymentHistoryResponse responseModel);

    void onPaymentHistoryFailure(ApiError error);

    void onPaymentStatusComplete(BaseResponse responseModel);

    void onPaymentStatusFailure(ApiError error);

    void onInvoiceComplete(InvoiceResponse responseModel);

    void onInvoiceFailure(ApiError error);


}