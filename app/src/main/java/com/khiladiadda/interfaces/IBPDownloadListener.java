package com.khiladiadda.interfaces;

import android.content.Context;

import com.khiladiadda.network.model.request.BajajPayEncryptedRequest;

public interface IBPDownloadListener {

    void getOTPBajajPa(String number);

    void verifyOTPDialog(BajajPayEncryptedRequest bajajPayVerifyOtpRequest, final Context activity, final IBPDownloadListener listener);

    void resendOTPDialog(BajajPayEncryptedRequest otpRequest, final Context activity, final IBPDownloadListener listener);

    void callDebit(BajajPayEncryptedRequest bajajPayEncryptedDebitTransactionRequest);

    void insufficientBalance(String balance);

    void balanceBajajPay();

    void deLinkWallet();

}