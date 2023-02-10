package com.khiladiadda.wallet.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.ApexPayChecksumResponse;
import com.khiladiadda.network.model.response.CashfreeChecksumResponse;
import com.khiladiadda.network.model.response.ChecksumResponse;
import com.khiladiadda.network.model.response.GetGamerCashResponse;
import com.khiladiadda.network.model.response.InvoiceResponse;
import com.khiladiadda.network.model.response.NeokredResponse;
import com.khiladiadda.network.model.response.PaySharpResponse;
import com.khiladiadda.network.model.response.PaykunOrderResponse;
import com.khiladiadda.network.model.response.PayuChecksumResponse;
import com.khiladiadda.network.model.response.PhonePePaymentResponse;
import com.khiladiadda.network.model.response.PhonepeCheckPaymentResponse;
import com.khiladiadda.network.model.response.ProfileTransactionResponse;
import com.khiladiadda.network.model.response.RazorpayOrderIdResponse;
import com.khiladiadda.network.model.response.VersionResponse;
import com.khiladiadda.network.model.response.ZaakpayChecksumResponse;

public interface IWalletView {

    String getAmount();

    void onValidationComplete();

    void onValidationFailure(String errorMsg);

    void onProfileComplete(ProfileTransactionResponse responseModel);

    void onProfileFailure(ApiError error);

    void onVersionSuccess(VersionResponse response);

    void onVersionFailure(ApiError error);

    void onPaytmChecksumComplete(ChecksumResponse responseModel);

    void onPaytmChecksumFailure(ApiError error);

    void onPaytmPaymentComplete(BaseResponse responseModel);

    void onPaytmPaymentFailure(ApiError error);

    void onCashfreeChecksumComplete(CashfreeChecksumResponse responseModel);

    void onCashfreeChecksumFailure(ApiError error);

    void onCashfreePaymentComplete(BaseResponse responseModel);

    void onCashfreePaymentFailure(ApiError error);

    void onPayuChecksumComplete(PayuChecksumResponse responseModel);

    void onPayuChecksumFailure(ApiError error);

    void onPayuPaymentComplete(BaseResponse responseModel);

    void onPayuPaymentFailure(ApiError error);

    void onRazorpayOrderIdComplete(RazorpayOrderIdResponse responseModel);

    void onRazorpayOrderIdFailure(ApiError error);

    void onRazorpayPaymentComplete(BaseResponse responseModel);

    void onRazorpayPaymentFailure(ApiError error);

    void onPaykunOrderIdComplete(PaykunOrderResponse responseModel);

    void onPaykunOrderIdFailure(ApiError error);

    void onPaykunPaymentComplete(BaseResponse responseModel);

    void onPaykunPaymentFailure(ApiError error);

    void onInvoiceComplete(InvoiceResponse responseModel);

    void onInvoiceFailure(ApiError error);

    void onApplyCouponComplete(BaseResponse responseModel);

    void onApplyCouponFailure(ApiError error);


    void onPayUHashComplete(PayuChecksumResponse responseModel);

    void onPayUHashFailure(ApiError error);


    void onZaakpayChecksumComplete(ZaakpayChecksumResponse responseModel);

    void onZaakpayChecksumFailure(ApiError error);

    void onApexPayChecksumComplete(ApexPayChecksumResponse responseModel);

    void onApexPayChecksumFailure(ApiError error);

    void onApexPayComplete(ApexPayChecksumResponse responseModel);

    void onApexPayFailure(ApiError error);

    void onPaySharpComplete(PaySharpResponse responseModel);

    void onPaySharpFailure(ApiError error);

    void onPaySharpStatusComplete(BaseResponse responseModel);

    void onPaySharpStatusFailure(ApiError error);

    void onEaseBuzzChecksumComplete(ChecksumResponse responseModel);

    void onEaseBuzzChecksumFailure(ApiError error);

    void onEaseBuzzPaymentComplete(BaseResponse responseModel);

    void onEaseBuzzPaymentFailure(ApiError error);

    void onNeokredComplete(NeokredResponse responseModel);

    void onNeokredFailure(ApiError error);

    void onPaymentComplete(PhonePePaymentResponse response);

    void onPaymentFailure(ApiError errorMsg);

    void onPaymentCheckComplete(PhonepeCheckPaymentResponse response);

    void onPaymentCheckFailure(ApiError errorMsg);

    void onGetGamerCashSuccess(GetGamerCashResponse response);

    void onGetGamerCashFailure(ApiError errorMsg);

}