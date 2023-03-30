package com.khiladiadda.wallet;

import com.khiladiadda.network.ApiManager;
import com.khiladiadda.network.ApiService;
import com.khiladiadda.network.IApiListener;
import com.khiladiadda.network.PaySharpApiManager;
import com.khiladiadda.network.PaySharpSubscriberCallback;
import com.khiladiadda.network.SubscriberCallback;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.CashfreeChecksumRequest;
import com.khiladiadda.network.model.request.CashfreeSavePayment;
import com.khiladiadda.network.model.request.ChecksumRequest;
import com.khiladiadda.network.model.request.EaseBuzzHashRequest;
import com.khiladiadda.network.model.request.EaseBuzzSaveRequest;
import com.khiladiadda.network.model.request.PaySharpRequest;
import com.khiladiadda.network.model.request.PaykunRequest;
import com.khiladiadda.network.model.request.PaymentRequest;
import com.khiladiadda.network.model.request.PayuChecksumRequest;
import com.khiladiadda.network.model.request.PayuSavePayment;
import com.khiladiadda.network.model.request.PhonepeCheckPaymentRequest;
import com.khiladiadda.network.model.request.PhonepeRequest;
import com.khiladiadda.network.model.request.RazorpayRequest;
import com.khiladiadda.network.model.response.ApexPayChecksumResponse;
import com.khiladiadda.network.model.response.CashfreeChecksumResponse;
import com.khiladiadda.network.model.response.ChecksumResponse;
import com.khiladiadda.network.model.response.GetGamerCashResponse;
import com.khiladiadda.network.model.response.InvoiceResponse;
import com.khiladiadda.network.model.response.NeokredResponse;
import com.khiladiadda.network.model.response.PaySharpResponse;
import com.khiladiadda.network.model.response.PaykunOrderResponse;
import com.khiladiadda.network.model.response.PaymentStatusRequest;
import com.khiladiadda.network.model.response.PayuChecksumResponse;
import com.khiladiadda.network.model.response.PhonePePaymentResponse;
import com.khiladiadda.network.model.response.PhonepeCheckPaymentResponse;
import com.khiladiadda.network.model.response.ProfileTransactionResponse;
import com.khiladiadda.network.model.response.RazorpayOrderIdResponse;
import com.khiladiadda.network.model.response.VersionResponse;
import com.khiladiadda.network.model.response.ZaakpayChecksumResponse;
import rx.Subscription;

public class WalletInteractor {

    Subscription getPaytmChecksumhash(ChecksumRequest request, IApiListener<ChecksumResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getPaytmChecksumHash(request)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription savePaytmPayment(PaymentRequest request, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.savePaytmPayment(request)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription saveCashfreePayment(CashfreeSavePayment request, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.saveCashfreePayment(request)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getProfile(IApiListener<ProfileTransactionResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getProfile(true)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getCashfreeChechsumhash(CashfreeChecksumRequest request, IApiListener<CashfreeChecksumResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getCashfreeChecksumHash(request)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getPayuChechsumhash(PayuChecksumRequest request, IApiListener<PayuChecksumResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getPayuChecksum(request)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription savePayuPayment(PayuSavePayment request, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.savePayuPayment(request)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getVersionDetails(IApiListener<VersionResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getVersionDetails()).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getRazorpay(CashfreeChecksumRequest request, IApiListener<RazorpayOrderIdResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getRazorpay(request)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription saveRazorpay(RazorpayRequest request, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.saveRazorpay(request)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getPaykunOrderId(CashfreeChecksumRequest request, IApiListener<PaykunOrderResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getPaykunOrderId(request)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription savePaykunPayment(String paymentId, String orderId,String couponCode, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        PaykunRequest request = new PaykunRequest();
        request.setCoupon(couponCode);
        return manager.createObservable(service.savePaykunPayment(request, paymentId, orderId)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getInvoice(IApiListener<InvoiceResponse> listener, String id) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getInvoice(id)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription applyCoupon(IApiListener<BaseResponse> listener, String couponCode) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.applyCoupon(couponCode)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getZaakpayChecksumhash(IApiListener<ZaakpayChecksumResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getZaakpayChecksumHash()).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getApexPayChecksumhash(String amount,String couponCode, IApiListener<ApexPayChecksumResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getApexPayChecksumHash(amount,couponCode)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getApexPayStatus(IApiListener<ApexPayChecksumResponse> listener, PaymentStatusRequest request) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getApexPayStatus(request)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getPayuHash(String hash, String orderId, IApiListener<PayuChecksumResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getPayuHash(hash, orderId)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getPaySharp(PaySharpRequest request, IApiListener<PaySharpResponse> listener,String couponCode) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getPaySharp(String.valueOf(request.getAmount()),couponCode)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getPaySharpStatus(PaymentStatusRequest request, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getPaySharpStatus(request)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getEaseBuzzHash(EaseBuzzHashRequest request, IApiListener<ChecksumResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getEaseBuzzHash(request)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription saveEaseBuzzPayment(EaseBuzzSaveRequest request, IApiListener<BaseResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.saveEaseBuzzPayment(request)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription checkNeokredPG(EaseBuzzSaveRequest request, IApiListener<NeokredResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.checkNeokredPG(request)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getPaymentUrlData(IApiListener<PhonePePaymentResponse> listener, PhonepeRequest phonepeRequest) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getPaymentUrl(phonepeRequest)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getPhonepeCheckData(IApiListener<PhonepeCheckPaymentResponse> listener, PhonepeCheckPaymentRequest phonepeRequest) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getCheckPaymentSuccess(phonepeRequest)).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getGamerData(IApiListener<GetGamerCashResponse> listener) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.payGamerCashData()).subscribe(new SubscriberCallback<>(listener));
    }

    Subscription getCashfreeStatus(IApiListener<BaseResponse> listener, String orderId) {
        ApiManager manager = ApiManager.getInstance();
        ApiService service = manager.createService();
        return manager.createObservable(service.getCashfreeStatus(orderId)).subscribe(new SubscriberCallback<>(listener));
    }

}