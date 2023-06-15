package com.khiladiadda.wallet.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.ApexPayChecksumResponse;
import com.khiladiadda.network.model.response.BajajPayGetBalanceResponse;
import com.khiladiadda.network.model.response.BajajPayResponse;
import com.khiladiadda.network.model.response.BajajPayVerifyOtpResponse;
import com.khiladiadda.network.model.response.CashfreeChecksumResponse;
import com.khiladiadda.network.model.response.ChecksumResponse;
import com.khiladiadda.network.model.response.GetGamerCashResponse;
import com.khiladiadda.network.model.response.NeokredResponse;
import com.khiladiadda.network.model.response.PaySharpResponse;
import com.khiladiadda.network.model.response.PaykunOrderResponse;
import com.khiladiadda.network.model.response.PayuChecksumResponse;
import com.khiladiadda.network.model.response.PhonePePaymentResponse;
import com.khiladiadda.network.model.response.PhonepeCheckPaymentResponse;
import com.khiladiadda.network.model.response.RazorpayOrderIdResponse;
import com.khiladiadda.network.model.response.UpdateBalanceResponse;
import com.khiladiadda.network.model.response.ZaakpayChecksumResponse;

public interface IPaymentView {

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

    void onPhonepePaymentComplete(PhonePePaymentResponse response);

    void onPhonepePaymentFailure(ApiError errorMsg);

    void onPhonePePaymentCheckComplete(PhonepeCheckPaymentResponse response);

    void onPhonePePaymentCheckFailure(ApiError errorMsg);

    void onGetGamerCashSuccess(GetGamerCashResponse response);

    void onGetGamerCashFailure(ApiError errorMsg);

    void onCashfreeStatusSuccess(BaseResponse response);

    void onCashfreeStatusFailure(ApiError errorMsg);

    void onBajajPayGetBalanceSuccess(BajajPayGetBalanceResponse response);

    void onBajajPayGetBalanceFailure(ApiError error);

    void onDoBajajPaymentSuccess(BajajPayResponse response);

    void onDoBajajPaymentFailure(ApiError error);

    void onBajajPayGetOTPSuccess(BajajPayResponse response);

    void onBajajPayGetOTPFailure(ApiError error);

    void onBajajPayVerifyOTPSuccess(BajajPayVerifyOtpResponse response);

    void onBajajPayVerifyOTPFailure(ApiError error);

    void onBajajPayResendOTPSuccess(BajajPayResponse response);

    void onBajajPayResendOTPFailure(ApiError error);

    void onBajajPayDebitSuccess(BajajPayResponse response);

    void onBajajPayDebitFailure(ApiError error);

    void onInsufficientBalanceBajajPaySuccess(BajajPayResponse response);

    void onInsufficientBalanceBajajPayFailure(ApiError error);

    void onUpdateBalanceKhiladiAdda(UpdateBalanceResponse response);

    void onUpdateBalanceKhiladiAddaFailure(ApiError error);

    void onDeLinkWalletSuccess(BajajPayResponse response);

    void onDeLinkWalletFailure(ApiError error);

    void onBajajValidationSuccess(BaseResponse response);

    void onBajajValidationFailure(ApiError error);
    void onLinkBajajSuccess(BaseResponse response);

    void onLinkBajajFailure(ApiError error);


}