package com.khiladiadda.transaction.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;
import com.khiladiadda.network.model.request.PaymentHistoryRequest;

public interface ITransactionPresenter extends IBasePresenter {

    void getAllTransaction(int page, int limit);

    void getWithdrawHistory(int page, int limit);

    void getPaymentHistory(PaymentHistoryRequest request);

    void getPaymentStatus(String id);

    void getInvoice(String id);

}