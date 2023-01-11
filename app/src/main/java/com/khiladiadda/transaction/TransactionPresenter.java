package com.khiladiadda.transaction;

import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.PaymentHistoryRequest;
import com.khiladiadda.network.model.response.InvoiceResponse;
import com.khiladiadda.network.model.response.PaymentHistoryDetails;
import com.khiladiadda.network.model.response.PaymentHistoryResponse;
import com.khiladiadda.network.model.response.WalletTransactionResponse;
import com.khiladiadda.network.model.response.WithdrawResponse;
import com.khiladiadda.transaction.interfaces.ITransactionPresenter;
import com.khiladiadda.transaction.interfaces.ITransactionView;

import rx.Subscription;

public class TransactionPresenter implements ITransactionPresenter {

    private ITransactionView mView;
    private TransactionInteractor mInteractor;
    private Subscription mSubscription;

    public TransactionPresenter(ITransactionView view) {
        mView = view;
        mInteractor = new TransactionInteractor();
    }

    @Override public void getAllTransaction(int page, int limit) {
        mSubscription = mInteractor.getAllTransaction(mAllTranactionApiListener, page, limit);
    }

    @Override public void getWithdrawHistory(int page, int limit) {
        mSubscription = mInteractor.getWithdrawHistory(mWithdrawHistoryApiListener, page, limit);
    }

    @Override public void getPaymentHistory(PaymentHistoryRequest request) {
        mSubscription = mInteractor.getPaymentHistory(mPaymentHistoryApiListener, request);
    }

    private IApiListener<WalletTransactionResponse> mAllTranactionApiListener = new IApiListener<WalletTransactionResponse>() {
        @Override public void onSuccess(WalletTransactionResponse response) {
            mView.onAllTransactionComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onAllTransactionFailure(error);
        }
    };
    private IApiListener<WithdrawResponse> mWithdrawHistoryApiListener = new IApiListener<WithdrawResponse>() {
        @Override public void onSuccess(WithdrawResponse response) {
            mView.onWithdrawHistoryComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onWithdrawHistoryFailure(error);
        }
    };
    private IApiListener<PaymentHistoryResponse> mPaymentHistoryApiListener = new IApiListener<PaymentHistoryResponse>() {
        @Override public void onSuccess(PaymentHistoryResponse response) {
            mView.onPaymentHistoryComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onPaymentHistoryFailure(error);
        }
    };

    @Override public void getPaymentStatus(String id) {
        mSubscription = mInteractor.getPaymentStatus(mPaymentStatusApiListener, id);
    }

    private IApiListener<BaseResponse> mPaymentStatusApiListener = new IApiListener<BaseResponse>() {
        @Override public void onSuccess(BaseResponse response) {
            mView.onPaymentStatusComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onPaymentStatusFailure(error);
        }
    };

    @Override public void getInvoice(String id) {
        mSubscription = mInteractor.getInvoice(mInvoiceApiListener, id);
    }
    private IApiListener<InvoiceResponse> mInvoiceApiListener = new IApiListener<InvoiceResponse>() {
        @Override public void onSuccess(InvoiceResponse response) {
            mView.onInvoiceComplete(response);
        }

        @Override public void onError(ApiError error) {
            mView.onInvoiceFailure(error);
        }
    };

    @Override public void destroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}