package com.khiladiadda.withdrawcoins.interfaces;

import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.AddBeneficiaryResponse;
import com.khiladiadda.network.model.response.BeneficiaryResponse;
import com.khiladiadda.network.model.response.BeneficiaryVerifyResponse;
import com.khiladiadda.network.model.response.ManualWithdrawResponse;
import com.khiladiadda.network.model.response.OtpResponse;
import com.khiladiadda.network.model.response.PayoutResponse;
import com.khiladiadda.network.model.response.TdsResponse;
import com.khiladiadda.network.model.response.WIthdrawLimitResponse;

public interface IWithdrawView {

    String getAmount();

    void onValidationComplete();

    void onValidationFailure(String errorMsg);

    void onGetBeneficiaryListComplete(BeneficiaryResponse responseModel);

    void onGetBeneficiaryListFailure(ApiError error);

    void onAddCashfreeBeneficiaryComplete(AddBeneficiaryResponse response);

    void onAddCashfreeBeneficiaryFailed(ApiError error);

    void onCashfreeTransferComplete(PayoutResponse response);

    void onCashfreeTransferFailed(ApiError error);

    void onDeleteComplete(BaseResponse response);

    void onDeleteFailed(ApiError error);

    void onManualWithdrawComplete(BaseResponse responseModel);

    void onManualWithdrawFailure(ApiError error);

    void onGetManualWithdrawComplete(ManualWithdrawResponse responseModel);

    void onGetManualWithdrawFailure(ApiError error);

    void onResendOtpComplete(OtpResponse responseModel);

    void onResendOtpFailure(ApiError error);

    void onSendOtpComplete(BaseResponse responseModel);

    void onSendOtpFailure(ApiError error);

    void onAddRazorpayBeneficiaryComplete(AddBeneficiaryResponse response);

    void onAddRazorpayBeneficiaryFailed(ApiError error);

    void onRazorpayTransferComplete(PayoutResponse response);

    void onRazorpayTransferFailed(ApiError error);

    void onWithdrawLimitComplete(WIthdrawLimitResponse response);

    void onWithdrawLimitFailed(ApiError error);

    void onVerifyBeneficiaryComplete(BeneficiaryVerifyResponse responseModel);

    void onVerifyBeneficiaryFailure(ApiError error);

    void onApexPayTransferComplete(PayoutResponse response);

    void onApexPayTransferFailed(ApiError error);

    void onPaySharpTransferComplete(PayoutResponse response);

    void onPaySharpTransferFailed(ApiError error);

    void onAddBeneficiaryComplete(AddBeneficiaryResponse response);

    void onAddBeneficiaryFailed(ApiError error);

    void onRaceConditionComplete(PayoutResponse response);

    void onRaceConditionFailed(ApiError error);

    void onIPayComplete(PayoutResponse response);
    void onIPayFailed(ApiError error);

    void onCheckTDSComplete(TdsResponse response);
    void onCheckTDSFailed(ApiError error);

}