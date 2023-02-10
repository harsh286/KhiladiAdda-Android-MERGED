package com.khiladiadda.wallet.interfaces;

import com.khiladiadda.base.interfaces.IBasePresenter;
import com.khiladiadda.network.model.request.CashfreeSavePayment;
import com.khiladiadda.network.model.request.PaySharpRequest;
import com.khiladiadda.network.model.request.PaymentRequest;
import com.khiladiadda.network.model.request.PayuSavePayment;
import com.khiladiadda.network.model.request.PhonepeCheckPaymentRequest;
import com.khiladiadda.network.model.request.PhonepeRequest;
import com.khiladiadda.network.model.request.RazorpayRequest;

public interface IWalletPresenter extends IBasePresenter {

    void validateData();

    void getProfile();

    void getVersionDetails();

    void getPaytmChecksum(String orderId, String amount, String callbackURL, String couponCode);

    void savePaytmPayment(PaymentRequest request);

    void saveCashfreePayment(CashfreeSavePayment cashfreeSavePayment);

    void getCashfreeChecksum(String amount, String couponCode);

    void savePayuPayment(PayuSavePayment payuSavePayment);

    void getRazorPayOrderId(String amount, String couponCode);

    void saveRazorPayment(RazorpayRequest payment);

    void getPaykunOrderId(String amount, String couponCode);

    void savePaykunPayment(String paymentId, String orderId, String couponCode);

    void getInvoice(String id);

    void applyCoupon(String couponCode);

    void getZaakpayChecksum();

    void getApexPayChecksum(String amount, String couponCode);

    void getApexPayStatus(String orderId, String couponCode);

    void getPayuChecksum(String amount, String couponCode);

    void getPayuHash(String hash, String orderId);

    void getPaySharp(PaySharpRequest request, String couponCode);

    void getPaySharpStatus(String orderId, String couponCode);

    void getPayuCheckum(String trans, long amnt);

    void getEasebuzzHash(double amount, String couponCode);

    void saveEasebuzzPayment(double amount, String coupon, String orderId, String status);

    void checkNeokredPG(double amount, String couponCode);

    void getPaymentUrlData(PhonepeRequest phonepeRequest);

    void getPaymentCheckData(PhonepeCheckPaymentRequest phonepeRequest);

    void getGamerCashUserData();

}