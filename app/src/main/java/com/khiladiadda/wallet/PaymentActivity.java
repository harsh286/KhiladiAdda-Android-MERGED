package com.khiladiadda.wallet;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.cashfree.pg.api.CFPaymentGatewayService;
import com.cashfree.pg.core.api.CFSession;
import com.cashfree.pg.core.api.CFTheme;
import com.cashfree.pg.core.api.callback.CFCheckoutResponseCallback;
import com.cashfree.pg.core.api.exception.CFException;
import com.cashfree.pg.core.api.utils.CFErrorResponse;
import com.cashfree.pg.core.api.webcheckout.CFWebCheckoutPayment;
import com.cashfree.pg.core.api.webcheckout.CFWebCheckoutTheme;
import com.cashfree.pg.ui.api.CFDropCheckoutPayment;
import com.cashfree.pg.ui.api.CFPaymentComponent;
import com.cashfree.pg.ui.api.upi.intent.CFUPIIntentCheckout;
import com.cashfree.pg.ui.api.upi.intent.CFUPIIntentCheckoutPayment;
import com.easebuzz.payment.kit.PWECouponsActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.dialogs.interfaces.IOnPaymentListener;
import com.khiladiadda.dialogs.interfaces.IOnPaymentWaitListener;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.gamercash.GamerCashActivity;
import com.khiladiadda.gamercash.PayActivity;
import com.khiladiadda.interfaces.IBPDownloadListener;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.BajajAuthOtPPaymentRequest;
import com.khiladiadda.network.model.request.BajajPayDeLinkWalletRequest;
import com.khiladiadda.network.model.request.BajajPayEncryptedRequest;
import com.khiladiadda.network.model.request.BajajPayGetBalanceRequest;
import com.khiladiadda.network.model.request.BajajPayGetOtpRequest;
import com.khiladiadda.network.model.request.BajajPayInsuffiencientBalanceRequest;
import com.khiladiadda.network.model.request.LinkBajajWalletRequest;
import com.khiladiadda.network.model.request.PaySharpRequest;
import com.khiladiadda.network.model.request.PaymentRequest;
import com.khiladiadda.network.model.request.PhonepeCheckPaymentRequest;
import com.khiladiadda.network.model.request.PhonepeRequest;
import com.khiladiadda.network.model.request.UpdateBalanceRequest;
import com.khiladiadda.network.model.response.ApexPayChecksumResponse;
import com.khiladiadda.network.model.response.BajajPayBalanceDecryptResponse;
import com.khiladiadda.network.model.response.BajajPayDeLinkWalletDecryptResponse;
import com.khiladiadda.network.model.response.BajajPayDebitTransactionDecryptResponse;
import com.khiladiadda.network.model.response.BajajPayGetBalanceResponse;
import com.khiladiadda.network.model.response.BajajPayInsufficientBalanceDecryptResponse;
import com.khiladiadda.network.model.response.BajajPayResponse;
import com.khiladiadda.network.model.response.BajajPayResponseDecrypt;
import com.khiladiadda.network.model.response.BajajPayVerifyOtpResponse;
import com.khiladiadda.network.model.response.BajajPayVerifyResponseDecrypt;
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
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.utility.NewAESEncrypt;
import com.khiladiadda.wallet.interfaces.IPaymentPresenter;
import com.khiladiadda.wallet.interfaces.IPaymentView;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;
import com.phonepe.intent.sdk.api.B2BPGRequest;
import com.phonepe.intent.sdk.api.B2BPGRequestBuilder;
import com.phonepe.intent.sdk.api.PhonePe;
import com.phonepe.intent.sdk.api.PhonePeInitException;
import com.phonepe.intent.sdk.api.UPIApplicationInfo;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
public class PaymentActivity extends BaseActivity implements IPaymentView, IBPDownloadListener, PaytmPaymentTransactionCallback, CFCheckoutResponseCallback {
    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.cl_upi_phone_pe)
    ConstraintLayout mPhonePeCL;
    @BindView(R.id.cl_bajajpe_upi)
    ConstraintLayout mBajajPayUpiTopCl;
    @BindView(R.id.cl_upi_google_pe)
    ConstraintLayout mGooglePeCL;
    @BindView(R.id.tv_upi_paytm)
    TextView mPaytmUpiTV;
    @BindView(R.id.cl_paytm_wallet)
    ConstraintLayout mPaytmWalletCL;
    @BindView(R.id.cl_bajajpe_wallet_wallet)
    ConstraintLayout mBajajPayLL;
    @BindView(R.id.tv_bajajpe_wallet)
    TextView mBajajPayTV;
    @BindView(R.id.img_bajaj_pay)
    ImageView mBajajPayIV;
    @BindView(R.id.img_bajaj_pay_delink)
    ImageView mBajajPayDeLink;
    @BindView(R.id.tv_gamercash)
    TextView mGamerCashTV;
    @BindView(R.id.tv_gamercash_verified)
    TextView mGamerCashVerifiedTV;
    @BindView(R.id.cl_net_banking)
    ConstraintLayout mNetBankingCL;
    @BindView(R.id.cl_bajajpe_netbanking)
    ConstraintLayout mNetBankingBajajPayCL;
    @BindView(R.id.img_arrow_straight)
    ImageView imgArrowStraight;
    @BindView(R.id.img_arrow_down)
    ImageView imgArrowDown;
    @BindView(R.id.cl_bajajpe_netbanking_wallet)
    ConstraintLayout mNetBankingBajajPayWalletCL;
    @BindView(R.id.tv_bajajpe_wallet_netbanking)
    TextView mBajajPayTVNetBankingWallet;
    @BindView(R.id.img_net_banking_bajaj_pay)
    ImageView imgNetBankingBajajPayLink;
    @BindView(R.id.img_net_banking_bajaj_pay_delink)
    ImageView getMBajajPayDeLinkNetBaking;
    @BindView(R.id.cl_bajajpe_netbanking_upi)
    ConstraintLayout mNetBankingBajajPayUpiCL;
    @BindView(R.id.btn_pay)
    Button mPayBTN;
    @BindView(R.id.tv_other_upi)
    TextView mOtherUpiTV;
    private static final int PAYTM_REQUEST_CODE = 666;
    private static final int B2B_PG_REQUEST_CODE = 777;
    private String mCouponCode, mOrderId, mAmount, mCallbackURL, mPhonepeOrderId, mApexPayOrderId, mPaySharpOrderId, mApiEndPoint = "/pg/v1/pay", bajajpayPaymentAmount, bajajpayStatusMsg;
    private int mPaymentFrom, mUpiPaymentType, getmPaymentFrom = 0, mOtherUpi;
    private boolean mApexPay, mPaySharp, mIsGamerCashEnabled, mIsCashfree, mIsEasebuzz, mIsBajajWallet, mIsBajajPayBanking, mIsPaysharp, mIsPhonepe, mBajajWalletActive, mIsBajajUpi;
    private IPaymentPresenter mPresenter;
    public String mAmountET;
    private long mGamerCash;
    private String userToken = "";
    private String bajajAccessToken, mMobileNumber = "";
    private HashMap<String, Boolean> mRemainingData=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        try {
            PhonePe.init(this);
            CFPaymentGatewayService.getInstance().setCheckoutCallback(this);
        } catch (CFException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected int getContentView() {
        return R.layout.activity_payment;
    }
    @Override
    protected void initViews(){
        mBackIV.setOnClickListener(this);
        mActivityNameTV.setText(R.string.text_payment);
        mNotificationIV.setOnClickListener(this);
        mBajajPayUpiTopCl.setOnClickListener(this);
        mNetBankingBajajPayWalletCL.setOnClickListener(this);
        tvError.setOnClickListener(this);
        mBajajPayLL.setOnClickListener(this);
        mNetBankingBajajPayUpiCL.setOnClickListener(this);
        mBajajPayTV.setOnClickListener(this);
        mBajajPayTVNetBankingWallet.setOnClickListener(this);
        mBajajPayDeLink.setOnClickListener(this);
        getMBajajPayDeLinkNetBaking.setOnClickListener(this);
        mNetBankingBajajPayCL.setOnClickListener(this);
        imgArrowStraight.setOnClickListener(this);
        imgArrowDown.setOnClickListener(this);
        imgArrowDown.setVisibility(View.GONE);
        mNetBankingBajajPayWalletCL.setVisibility(View.GONE);
        mNetBankingBajajPayUpiCL.setVisibility(View.GONE);
        mPayBTN.setOnClickListener(this);
        mPayBTN.setEnabled(false);
        mPhonePeCL.setOnClickListener(this);
        mGooglePeCL.setOnClickListener(this);
        mPaytmUpiTV.setOnClickListener(this);
        mPaytmWalletCL.setOnClickListener(this);
        mNetBankingCL.setOnClickListener(this);
        mGamerCashTV.setOnClickListener(this);
        mGamerCashVerifiedTV.setOnClickListener(this);
        mOtherUpiTV.setOnClickListener(this);
    }
    @Override
    protected void initVariables(){
        mPresenter=new PaymentPresenter(this);
        mAmount=getIntent().getStringExtra(AppConstant.TXN_AMOUNT);
        mCouponCode = getIntent().getStringExtra(AppConstant.COUPON);
        mPayBTN.setText("Add ₹ " + mAmount);
        mUpiPaymentType = getIntent().getIntExtra(AppConstant.PAYMENT_MODE, 0);
        mOtherUpi = getIntent().getIntExtra(AppConstant.OTHER_UPI, 0);
        mIsGamerCashEnabled = getIntent().getBooleanExtra(AppConstant.GAMERCASH, false);
        mIsCashfree = getIntent().getBooleanExtra(AppConstant.CASHFREE, false);
        mIsEasebuzz = getIntent().getBooleanExtra(AppConstant.EASEBUZZ, false);
        mIsPaysharp = getIntent().getBooleanExtra(AppConstant.PAYSHARP, false);
        mIsPhonepe = getIntent().getBooleanExtra(AppConstant.PHONEPE, false);
        boolean mIsPaytm = getIntent().getBooleanExtra(AppConstant.PAYTM, false);
        mBajajWalletActive = getIntent().getBooleanExtra(AppConstant.BAJAJWALLET, false);
        mIsBajajUpi = getIntent().getBooleanExtra(AppConstant.BAJAJUPI, false);
        mMobileNumber = getIntent().getStringExtra("mobile_number");
        mRemainingData = (HashMap<String, Boolean>) getIntent().getSerializableExtra(AppConstant.REMAININGDATA);
        if (!mBajajWalletActive && !mIsBajajUpi) {
            mNetBankingBajajPayCL.setVisibility(View.GONE);
        }
        if (mIsBajajUpi) {
            mBajajPayUpiTopCl.setVisibility(View.VISIBLE);
            mNetBankingBajajPayUpiCL.setVisibility(View.VISIBLE);
        }
        if (mBajajWalletActive) {
            mBajajPayLL.setVisibility(View.VISIBLE);
            mNetBankingBajajPayWalletCL.setVisibility(View.VISIBLE);
        }
        if (mIsPaytm) {
            mPaytmWalletCL.setVisibility(View.VISIBLE);
        }
        if (mIsCashfree || mIsEasebuzz) {
            mNetBankingCL.setVisibility(View.VISIBLE);
        }
        if (mIsGamerCashEnabled) {
            mGamerCashTV.setVisibility(View.VISIBLE);
            mGamerCashVerifiedTV.setVisibility(View.VISIBLE);
            payGamerCashVerifyAPI();
        } else {
            mGamerCashTV.setVisibility(View.GONE);
            mGamerCashVerifiedTV.setVisibility(View.GONE);
        }
        checkBajajpayLinked(mRemainingData);
        mAppPreference.setBoolean(AppConstant.FROM_WALLET, false);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.cl_upi_phone_pe:
                setSelection(1);
                break;
            case R.id.cl_bajajpe_upi:
                setSelection(2);
                break;
            case R.id.cl_upi_google_pe:
                setSelection(3);
                break;
            case R.id.tv_upi_paytm:
                setSelection(4);
                break;
            case R.id.cl_paytm_wallet:
                setSelection(5);
                break;
            case R.id.cl_bajajpe_wallet_wallet:
            case R.id.tv_bajajpe_wallet:
                setSelection(6);
                break;
            case R.id.cl_net_banking:
                setSelection(7);
                break;
            case R.id.cl_bajajpe_netbanking:
                setSelection(8);
                if (mIsBajajPayBanking) {
                    mIsBajajPayBanking = false;
                    imgArrowStraight.setVisibility(View.VISIBLE);
                    imgArrowDown.setVisibility(View.GONE);
                    mNetBankingBajajPayWalletCL.setVisibility(View.GONE);
                    mNetBankingBajajPayUpiCL.setVisibility(View.GONE);
                } else {
                    mIsBajajPayBanking = true;
                    imgArrowStraight.setVisibility(View.GONE);
                    imgArrowDown.setVisibility(View.VISIBLE);
                    if(mBajajWalletActive){
                        mNetBankingBajajPayWalletCL.setVisibility(View.VISIBLE);
                    }
                    if (mIsBajajUpi) {
                        mNetBankingBajajPayUpiCL.setVisibility(View.VISIBLE);
                    }
                }
                mPayBTN.setEnabled(false);
                break;
            case R.id.cl_bajajpe_netbanking_wallet:
            case R.id.tv_bajajpe_wallet_netbanking:
                setSelection(9);
                break;
            case R.id.cl_bajajpe_netbanking_upi:
                setSelection(10);
                break;
            case R.id.img_bajaj_pay_delink:
            case R.id.img_net_banking_bajaj_pay_delink:
                AppDialog.showBajajPayFailureDialog("", getString(R.string.link_status), this, getString(R.string.bajajpay_delink_wallet), this);
                break;
            case R.id.tv_gamercash:
            case R.id.tv_gamercash_verified:
                setSelection(11);
                getmPaymentFrom = 1;
                break;
            case R.id.tv_other_upi:
                setSelection(12);
                break;
            case R.id.btn_pay:
                if(mIsBajajWallet){
                    checkFromServer();
                }else if(getmPaymentFrom == 1){
                    checkGamerCashStatus();
                } else {
                    if (mPaymentFrom == AppConstant.FROM_GPAY_UPI) {
                        setPaymentFromData(1);
                    } else if (mPaymentFrom == AppConstant.FROM_PAYTM_UPI) {
                        setPaymentFromData(2);
                    } else if (mPaymentFrom == AppConstant.FROM_PHONEPAY_UPI) {
                        setPaymentFromData(3);
                    } else if (mPaymentFrom == AppConstant.FROM_BAJAJYPAY_UPI) {
                        setPaymentFromData(4);
                    }
                    callPaymentGateway();
                }
                break;
        }
    }
    private void checkFromServer(){
        showProgress("");
        mPresenter.checkBajajValidation(mAmount,mCouponCode);
    }
    private void setPaymentFromData(int i){
        if (mUpiPaymentType == 1){
            mPaymentFrom = AppConstant.FROM_CASHFREE_UPI;
        } else if (mUpiPaymentType == 2) {
            mPaymentFrom = AppConstant.FROM_PAYTM;
        } else if (mUpiPaymentType == 3) {
            mPaymentFrom = AppConstant.FROM_EASEBUZZ;
        } else if (mUpiPaymentType == 4) {
            mPaymentFrom = AppConstant.FROM_PAYSHARP;
        } else if ((mUpiPaymentType == 5)) {
            if (i == 1) {
                mPaymentFrom = AppConstant.FROM_PHONEPE_GPAY;
            } else if (i == 2) {
                mPaymentFrom = AppConstant.FROM_PHONEPE_PaytmPAY;
            } else if (i == 3) {
                mPaymentFrom = AppConstant.FROM_PHONEPE_PhonePePAY;
            } else {
                if (mIsPhonepe) {
                    mPaymentFrom = AppConstant.FROM_PHONEPE_BAJAJPAY;
                } else if (mIsCashfree) {
                    mPaymentFrom = AppConstant.FROM_CASHFREE_UPI;
                } else if (mIsPaysharp) {
                    mPaymentFrom = AppConstant.FROM_PAYSHARP;
                }
            }
        }
    }

    private void setSelection(int i) {
        mPhonePeCL.setSelected(false);
        mBajajPayUpiTopCl.setSelected(false);
        mGooglePeCL.setSelected(false);
        mPaytmUpiTV.setSelected(false);
        mOtherUpiTV.setSelected(false);
        mPaytmWalletCL.setSelected(false);
        mBajajPayLL.setSelected(false);
        mNetBankingCL.setSelected(false);
        mNetBankingBajajPayCL.setSelected(false);
        mNetBankingBajajPayWalletCL.setSelected(false);
        mNetBankingBajajPayUpiCL.setSelected(false);
        mGamerCashTV.setSelected(false);
        mGamerCashVerifiedTV.setSelected(false);
        mPayBTN.setEnabled(true);
        getmPaymentFrom = 0;
        mIsBajajWallet = false;
        switch (i) {
            case 1:
                mPhonePeCL.setSelected(true);
                mPaymentFrom = AppConstant.FROM_PHONEPAY_UPI;
                break;
            case 2:
                mBajajPayUpiTopCl.setSelected(true);
                mPaymentFrom = AppConstant.FROM_BAJAJYPAY_UPI;
                break;
            case 3:
                mGooglePeCL.setSelected(true);
                mPaymentFrom = AppConstant.FROM_GPAY_UPI;
                break;
            case 4:
                mPaytmUpiTV.setSelected(true);
                mPaymentFrom = AppConstant.FROM_PAYTM_UPI;
                break;
            case 12:
                mOtherUpiTV.setSelected(true);
                setPaymentGateway();
                break;
            case 5:
                mPaytmWalletCL.setSelected(true);
                mPaymentFrom = AppConstant.FROM_PAYTM;
                break;
            case 6:
                mBajajPayLL.setSelected(true);
                mIsBajajWallet = true;
                break;
            case 7:
                mNetBankingCL.setSelected(true);
                if (mIsCashfree) {
                    mPaymentFrom = AppConstant.FROM_CASHFREE;
                } else if (mIsEasebuzz) {
                    mPaymentFrom = AppConstant.FROM_EASEBUZZ;
                }
                break;
            case 8:
                mNetBankingBajajPayCL.setSelected(true);
                break;
            case 9:
                mNetBankingBajajPayWalletCL.setSelected(true);
                mIsBajajWallet = true;
                break;
            case 10:
                mNetBankingBajajPayUpiCL.setSelected(true);
                mPaymentFrom = AppConstant.FROM_BAJAJYPAY_UPI;
                break;
            case 11:
                mGamerCashTV.setSelected(true);
                mGamerCashVerifiedTV.setSelected(true);
                mPaymentFrom = AppConstant.FROM_GAMECASE;
                break;
        }
    }

    private void setPaymentGateway() {
        switch (mOtherUpi) {
            case 1:
                mPaymentFrom = AppConstant.FROM_CASHFREE_UPI;
                break;
            case 2:
                mPaymentFrom = AppConstant.FROM_PAYTM;
                break;
            case 3:
                mPaymentFrom = AppConstant.FROM_EASEBUZZ;
                break;
            case 4:
                mPaymentFrom = AppConstant.FROM_PAYSHARP;
                break;
            case 5:
                mPaymentFrom = AppConstant.FROM_PHONEPE;
                break;
        }
    }

    private void validationBajajPay() {
        if (mAppPreference.getUserTokenBP() != null) {
            if (mAppPreference.getInSufficientBalanceBP() != null) {
                if (Double.parseDouble(mAmount) > Double.parseDouble(mAppPreference.getInSufficientBalanceBP())) {
                    Double addBalance = (Double.parseDouble(mAmount) - Double.parseDouble(mAppPreference.getInSufficientBalanceBP()));
                    String finalBalance = String.valueOf(addBalance);
                    AppDialog.showBajajPaySuccessDialog(this, finalBalance, this);
                } else {
                    BajajAuthOtPPaymentRequest bajajAuthOtPPaymentRequest = new BajajAuthOtPPaymentRequest(AppConstant.merchantId, mAppPreference.getMobileNumberBP(), mAppPreference.getUserTokenBP(), mAmount, AppConstant.BAJAJPAY_subMerchantId, AppConstant.subMerchantName);
                    String otpAuthBP = new Gson().toJson(bajajAuthOtPPaymentRequest);
                    String otpAuthEncryptRequest = NewAESEncrypt.encrypt(otpAuthBP.trim());
                    BajajPayEncryptedRequest authOtpEncRequest = new BajajPayEncryptedRequest(otpAuthEncryptRequest);
                    authSendFromPayOTPPP(authOtpEncRequest);
                }
            } else {
                mBajajPayIV.setVisibility(View.INVISIBLE);
                imgNetBankingBajajPayLink.setVisibility(View.INVISIBLE);
                mBajajPayTV.setVisibility(View.VISIBLE);
                mBajajPayTVNetBankingWallet.setVisibility(View.VISIBLE);
                AppDialog.showBajajPayFailureDialog(getString(R.string.bajaj_create_wallet), getString(R.string.bajaj_pay_balance_status), this, getString(R.string.mobile_not_bajajpay), this);
            }
        } else {
            if (mMobileNumber.isEmpty())
                AppDialog.linkWalletBajajPay(this, this, mAppPreference.getMobile());
            else
                AppDialog.linkWalletBajajPay(this, this, mMobileNumber);

        }
    }

    /**
     * Get BajajPay balance From BajajPay
     */
    private void getBajajPayBalance() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            /**  Request to send data to BajajPay for Getting Balance  */
            BajajPayGetBalanceRequest bajajPayGetBalanceRequest = new BajajPayGetBalanceRequest(AppConstant.merchantId, mAppPreference.getMobileNumberBP(), mAppPreference.getUserTokenBP(), "", "");
            String jsonData = new Gson().toJson(bajajPayGetBalanceRequest);
            String encryptData = NewAESEncrypt.encrypt(jsonData.trim());
            BajajPayEncryptedRequest bajajPayEncryptedRequest = new BajajPayEncryptedRequest(encryptData);
            mPresenter.getBajajPayBalance(bajajPayEncryptedRequest);
        } else {
            Snackbar.make(tvError, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaytmChecksumComplete(ChecksumResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            startPaytmNew(responseModel.getResponse());
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override
    public void onPaytmChecksumFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onPaytmPaymentComplete(BaseResponse responseModel) {
        onAllPaymentCompleteCheck(responseModel.isStatus(), responseModel.getMessage(), responseModel.getPayment_via());
    }

    @Override
    public void onPaytmPaymentFailure(ApiError error) {
        onAllPaymentFailed();
    }

    @Override
    public void onCashfreeChecksumComplete(CashfreeChecksumResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            startCashfreeNewSDK(responseModel);
            //startCashfreeWebCheckout(responseModel);
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override
    public void onCashfreeChecksumFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onCashfreePaymentComplete(BaseResponse responseModel) {
        onAllPaymentCompleteCheck(responseModel.isStatus(), responseModel.getMessage(), responseModel.getPayment_via());
    }

    @Override
    public void onCashfreePaymentFailure(ApiError error) {
        onAllPaymentFailed();
    }

    @Override
    public void onPayuChecksumComplete(PayuChecksumResponse responseModel) {
    }

    @Override
    public void onPayuChecksumFailure(ApiError error) {
    }

    @Override
    public void onPayuPaymentComplete(BaseResponse responseModel) {
        onAllPaymentCompleteCheck(responseModel.isStatus(), responseModel.getMessage(), responseModel.getPayment_via());
    }

    @Override
    public void onPayuPaymentFailure(ApiError error) {
        onAllPaymentFailed();
    }

    @Override
    public void onRazorpayOrderIdComplete(RazorpayOrderIdResponse responseModel) {

    }

    @Override
    public void onRazorpayOrderIdFailure(ApiError error) {

    }

    @Override
    public void onRazorpayPaymentComplete(BaseResponse responseModel) {

    }

    @Override
    public void onRazorpayPaymentFailure(ApiError error) {

    }

    @Override
    public void onPaykunOrderIdComplete(PaykunOrderResponse responseModel) {

    }

    @Override
    public void onPaykunOrderIdFailure(ApiError error) {

    }

    @Override
    public void onPaykunPaymentComplete(BaseResponse responseModel) {
        onAllPaymentCompleteCheck(responseModel.isStatus(), responseModel.getMessage(), responseModel.getPayment_via());
    }

    @Override
    public void onPaykunPaymentFailure(ApiError error) {
        onAllPaymentFailed();
    }

    @Override
    public void onPayUHashComplete(PayuChecksumResponse responseModel) {

    }

    @Override
    public void onPayUHashFailure(ApiError error) {

    }

    @Override
    public void onZaakpayChecksumComplete(ZaakpayChecksumResponse responseModel) {

    }

    @Override
    public void onZaakpayChecksumFailure(ApiError error) {

    }

    @Override
    public void onApexPayChecksumComplete(ApexPayChecksumResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            mApexPayOrderId = responseModel.getOrderId();
            Intent intent = new Intent(this, WebPaymentActivity.class);
            intent.putExtra("URL", responseModel.getResponse());
            startActivityForResult(intent, AppConstant.PaymentDone);
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override
    public void onApexPayChecksumFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onApexPayComplete(ApexPayChecksumResponse responseModel) {
        hideProgress();
        mApexPay = false;
        if (responseModel.isStatus()) {
            onPaymentSuccess(responseModel.getPayment_via());
        } else {
            onPaymentFailed(2, responseModel.getMessage(), responseModel.getPayment_via());
        }
    }

    @Override
    public void onApexPayFailure(ApiError error) {
        hideProgress();
        mApexPay = false;
        onPaymentFailed(3, "It is pending please check the status after sometime.", "");
    }

    @Override
    public void onPaySharpComplete(PaySharpResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            mPaySharpOrderId = responseModel.getResponse().getResponse().getOrderId();
            mPaySharp = true;
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(responseModel.getResponse().getResponse().getIntentUrl()));
            Intent chooser = Intent.createChooser(intent, "Pay with...");
            startActivity(chooser);
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override
    public void onPaySharpFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onPaySharpStatusComplete(BaseResponse responseModel) {
        hideProgress();
        mPaySharp = false;
        if (responseModel.isStatus()) {
            onPaymentSuccess(responseModel.getPayment_via());
        } else {
            onPaymentFailed(2, responseModel.getMessage(), responseModel.getPayment_via());
        }
    }

    @Override
    public void onPaySharpStatusFailure(ApiError error) {
        hideProgress();
        mPaySharp = false;
        AppUtilityMethods.showMsg(this, error.getMessage(), false);
    }

    @Override
    public void onEaseBuzzChecksumComplete(ChecksumResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            Intent intentProceed = new Intent(this, PWECouponsActivity.class);
            intentProceed.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);     // This is mandatory flag
            intentProceed.putExtra(AppConstant.TEXT_ACCESS_KEY_EASEBUZZ, responseModel.getResponse());
            intentProceed.putExtra(AppConstant.TEXT_PAY_MODE, AppConstant.TEXT_PAY_MODE_VALUE);
            easebuzzResultLauncher.launch(intentProceed);
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override
    public void onEaseBuzzChecksumFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onEaseBuzzPaymentComplete(BaseResponse responseModel) {
        onAllPaymentCompleteCheck(responseModel.isStatus(), responseModel.getMessage(), responseModel.getPayment_via());
    }

    @Override
    public void onEaseBuzzPaymentFailure(ApiError error) {
        onAllPaymentFailed();
    }

    @Override
    public void onNeokredComplete(NeokredResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            String UPI = "upi://pay?pa=" + responseModel.getResponse().trim() + "&pn=" + mAppPreference.getName().trim()
                    + "&am=" + mAmount + "&tn=" + "pay_" + responseModel.getTid().trim() + "&tid=" + responseModel.getTid().trim();
            intent.setData(Uri.parse(UPI));
            Intent chooser = Intent.createChooser(intent, "Pay with...");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(chooser, 1, null);
            } else {
                Toast.makeText(this, "No application available to handle this request!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onNeokredFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onPhonepePaymentComplete(PhonePePaymentResponse response) {
        hideProgress();
        if (response.isStatus()) {
            phonePePayment(response);
        } else {
            AppUtilityMethods.showMsg(this, response.getMessage(), false);
        }
    }

    @Override
    public void onPhonepePaymentFailure(ApiError errorMsg) {
        hideProgress();
    }

    @Override
    public void onPhonePePaymentCheckComplete(PhonepeCheckPaymentResponse response) {
        onAllPaymentCompleteCheck(response.isStatus(), response.getMessage(), response.getPayment_via());
    }

    @Override
    public void onPhonePePaymentCheckFailure(ApiError errorMsg) {
        hideProgress();
    }

    @Override
    public void onGetGamerCashSuccess(GetGamerCashResponse response) {
        if (response.isStatus()) {
            mAppPreference.setIsGamerCashLinked(response.getAlreadyLinked() || response.getLinked());
            if (mIsGamerCashEnabled) {
                if (response.getAlreadyLinked() || response.getLinked()) {
                    mGamerCash = response.getResponse().getCoins();
                    mGamerCashTV.setVisibility(View.GONE);
                    if (mAppPreference.getBoolean(AppConstant.IS_GAMERCASH_ENABLED, false)) {
                        mGamerCashVerifiedTV.setVisibility(View.VISIBLE);
                    }
                    mGamerCashVerifiedTV.setText(getString(R.string.gamer_cash_coins) + mGamerCash + " GC");
                } else {
                    if (mAppPreference.getBoolean(AppConstant.IS_GAMERCASH_ENABLED, false)) {
                        mGamerCashTV.setVisibility(View.VISIBLE);
                    }
                    mGamerCashVerifiedTV.setVisibility(View.GONE);
                    mGamerCashTV.setText(getString(R.string.gamercash));
                }
            }
        }
        hideProgress();
    }

    @Override
    public void onGetGamerCashFailure(ApiError errorMsg) {
        hideProgress();
    }

    @Override
    public void onCashfreeStatusSuccess(BaseResponse response) {
        onAllPaymentCompleteCheck(response.isStatus(), response.getMessage(), response.getPayment_via());
    }

    @Override
    public void onCashfreeStatusFailure(ApiError errorMsg) {
        onAllPaymentFailed();
    }

    /**
     * Bajaj Pay balance success and failure
     */
    @Override
    public void onBajajPayGetBalanceSuccess(BajajPayGetBalanceResponse response) {
        if (response.getStatusCode().equals("202")) {
            if (response.getStatusCode().equals("202")) {
                mBajajPayDeLink.setVisibility(View.VISIBLE);
                String decryptData = NewAESEncrypt.decrypt(response.encResponse);
                BajajPayBalanceDecryptResponse bajajPayBalanceDecryptResponse = new Gson().fromJson(decryptData, BajajPayBalanceDecryptResponse.class);
                if (mAppPreference.getUserTokenBP() != null) {
                    mBajajPayIV.setVisibility(View.INVISIBLE);
                    imgNetBankingBajajPayLink.setVisibility(View.INVISIBLE);
                    mBajajPayTV.setVisibility(View.VISIBLE);
                    mBajajPayTVNetBankingWallet.setVisibility(View.VISIBLE);
                    String balance = bajajPayBalanceDecryptResponse.getWalletBalance();
                    mAppPreference.setInSufficientBalanceBP(balance);
                    mBajajPayTV.setText(getString(R.string.bajaj_pay_balance) + balance);
                    mBajajPayTVNetBankingWallet.setText(getString(R.string.bajaj_pay_balance) + balance);
                }
            } else {
                mBajajPayIV.setVisibility(View.INVISIBLE);
                imgNetBankingBajajPayLink.setVisibility(View.INVISIBLE);
                mBajajPayTV.setVisibility(View.VISIBLE);
                mBajajPayTVNetBankingWallet.setVisibility(View.VISIBLE);
                mBajajPayDeLink.setVisibility(View.INVISIBLE);
                getMBajajPayDeLinkNetBaking.setVisibility(View.INVISIBLE);
                mBajajPayTV.setText(getString(R.string.bajaj_pay));
                mBajajPayTVNetBankingWallet.setText(getString(R.string.bajaj_pay));
            }
        } else if (response.statusCode.equals("E1145")) {
            mBajajPayIV.setVisibility(View.INVISIBLE);
            imgNetBankingBajajPayLink.setVisibility(View.INVISIBLE);
            mBajajPayTV.setVisibility(View.VISIBLE);
            mBajajPayDeLink.setVisibility(View.VISIBLE);
            mBajajPayTVNetBankingWallet.setVisibility(View.VISIBLE);
            getMBajajPayDeLinkNetBaking.setVisibility(View.VISIBLE);
            if (response.getStatusMsg().equals("Mobile Number doesn't have BajajPay wallet")) {
                String cutLine = "Mobile Number doesn't \nhave BajajPay wallet";
                mBajajPayTV.setText(cutLine);
                mBajajPayTVNetBankingWallet.setText(cutLine);
            }
        }
        hideProgress();
    }

    @Override
    public void onBajajPayGetBalanceFailure(ApiError error) {
        hideProgress();
    }

    /**
     * Bajaj Pay payment success and failure
     */
    @Override
    public void onDoBajajPaymentSuccess(BajajPayResponse response) {
        hideProgress();
        if (response.statusCode.equals("202")) {
            AppDialog.payBajajPay(mAmount, this, this, response, getString(R.string.enter_the_otp_complete_payment));
        } else {
            response.statusCode.equals("E1133");
            hideProgress();
            AppDialog.showBajajPayFailureDialog("", getString(R.string.bajaj_payment_status), this, getString(R.string.otp_limit_exceed_bajajpay), this);
        }
    }

    @Override
    public void onDoBajajPaymentFailure(ApiError error) {
        hideProgress();
    }

    /**
     * Bajaj Pay get Otp
     */
    @Override
    public void onBajajPayGetOTPSuccess(BajajPayResponse response) {
        hideProgress();
        if (response != null) {
            if (response.statusCode.equals("202")) {
                String decryptData = NewAESEncrypt.decrypt(response.encResponse);
                BajajPayResponseDecrypt bajajPayResponseDecrypt = new Gson().fromJson(decryptData, BajajPayResponseDecrypt.class);
                bajajAccessToken = bajajPayResponseDecrypt.getAccessToken();
//                AppDialog.verifyOTPBajajPay(bajajPayResponseDecrypt, this, this, getString(R.string.enter_otp_to_complete_verification));
                AppDialog.verifyOTPBajajPay(bajajPayResponseDecrypt, this, this, "Enter OTP to complete your verification\nOTP sent on " + bajajPayResponseDecrypt.getMobileNumber());
            } else {
                response.statusCode.equals("E1133");
                AppDialog.showBajajPayFailureDialog("", getString(R.string.bajaj_pay_verification_status), this, getString(R.string.otp_limit_exceed_bajajpay), this);
            }
        }
    }

    @Override
    public void onBajajPayGetOTPFailure(ApiError error) {
        hideProgress();
    }

    /**
     * Bajaj Pay Verify Otp success and failure.
     */
    @Override
    public void onBajajPayVerifyOTPSuccess(BajajPayVerifyOtpResponse response) {
        hideProgress();
        if (response.statusCode.equals("202")) {
            String decryptData = NewAESEncrypt.decrypt(response.encResponse);
            BajajPayVerifyResponseDecrypt bajajPayResponseDecrypt = new Gson().fromJson(decryptData, BajajPayVerifyResponseDecrypt.class);
            /**Saving Token for First time Users on verify OTP Response */
            /**Saving Mobile Number to SharedPreferences */
            userToken = bajajPayResponseDecrypt.getUserToken();
            mAppPreference.setMobileNumberBP(bajajPayResponseDecrypt.getMobileNumber());
            mAppPreference.setUserTokenBP(userToken);
            /*Call Api Link bajaj wallet*/
            if (new NetworkStatus(this).isInternetOn()) {
                showProgress(getString(R.string.txt_progress_authentication));
                mPresenter.getLinkBajajWallet(new LinkBajajWalletRequest(bajajPayResponseDecrypt.getMobileNumber(), false, bajajAccessToken, userToken));
            } else {
                Snackbar.make(tvError, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
            }
        } else if (response.statusCode.equals("E1136")) {
            AppDialog.showBajajPayFailureDialog("", getString(R.string.bajaj_pay_verification_status), this, getString(R.string.otp_is_invalid), this);
        }
    }

    @Override
    public void onBajajPayVerifyOTPFailure(ApiError error) {
        hideProgress();
        AppDialog.showStatusFailureDialog(this, getString(R.string.number_linking_process_failed));
    }

    @Override
    public void resendOTPDialog(BajajPayEncryptedRequest bajajPayEncryptedRequest, Context activity, IBPDownloadListener listener) {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getResendOtp(bajajPayEncryptedRequest);
        } else {
            Snackbar.make(tvError, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * Resend otp bajaj pay success and failure
     */
    @Override
    public void onBajajPayResendOTPSuccess(BajajPayResponse response) {
        hideProgress();
        if (response != null) {
            if (response.statusCode.equals("202")) {
                Toast.makeText(this, getString(R.string.otp_send_successfully), Toast.LENGTH_SHORT).show();
            } else {
                response.statusCode.equals("E1133");
                AppDialog.showBajajPayFailureDialog("", getString(R.string.verification_otp_status), this, getString(R.string.otp_limit_exceed_bajajpay), this);
            }
        }
    }

    @Override
    public void onBajajPayResendOTPFailure(ApiError error) {
        hideProgress();
///        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void callDebit(BajajPayEncryptedRequest bajajPayEncryptedDebitTransactionRequest) {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.callDeBitTransaction(bajajPayEncryptedDebitTransactionRequest);
        } else {
            Snackbar.make(tvError, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * Debit BajajPay Success and Failure.
     */
    @Override
    public void onBajajPayDebitSuccess(BajajPayResponse bajajPaymentResponse) {
        if (bajajPaymentResponse.statusCode.equals("202")) {
            String decryptData = NewAESEncrypt.decrypt(bajajPaymentResponse.encResponse);
            BajajPayDebitTransactionDecryptResponse bajajPayDebitTransactionDecryptResponse = new Gson().fromJson(decryptData, BajajPayDebitTransactionDecryptResponse.class);
            if (bajajPayDebitTransactionDecryptResponse.getStatusCode().equals("202")) {
                bajajpayStatusMsg = bajajPayDebitTransactionDecryptResponse.getStatusMsg();
                bajajpayPaymentAmount = bajajPayDebitTransactionDecryptResponse.getPaymentAmount();
                /**Call Khiladi Adda Api for update the balance */
                UpdateBalanceRequest updateBalanceRequest = new UpdateBalanceRequest();
                int amountAdded = Integer.parseInt(bajajpayPaymentAmount);
                updateBalanceRequest.setApp_version(AppUtilityMethods.getVersion());
                updateBalanceRequest.setAmount(amountAdded);
                updateBalanceRequest.setTxnId(bajajPayDebitTransactionDecryptResponse.getMerchantTxnId());
                mPresenter.updateBalance(updateBalanceRequest);
            }
        } else if (bajajPaymentResponse.statusCode.equals("E1136")) {
            hideProgress();
            AppDialog.showBajajPayFailureDialog("", getString(R.string.bajaj_payment_status), this, getString(R.string.otp_is_invalid), this);
        } else {
            hideProgress();
            Snackbar.make(tvError, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBajajPayDebitFailure(ApiError error) {
        hideProgress();
        AppDialog.showBajajPayFailureDialog("", getString(R.string.bajaj_payment_status), this, getString(R.string.payment_bajajpay_failed), this);
    }

    /**
     * Update Balance success and failureafter adding money from bajajPay
     */
    @Override
    public void onUpdateBalanceKhiladiAdda(UpdateBalanceResponse response) {
        hideProgress();
        mAppPreference.setBoolean(AppConstant.FROM_WALLET, true);
        AppDialog.showStatusSuccessDialog(this, bajajpayStatusMsg + "\n " + " of  Rs." + bajajpayPaymentAmount);
    }

    @Override
    public void onUpdateBalanceKhiladiAddaFailure(ApiError error) {
        hideProgress();
        Toast.makeText(this, getString(R.string.payment_bajajpay_failed), Toast.LENGTH_SHORT).show();
        /**Balance Update nhi ho rha h because HTTP 404 Found in this APi.*/
    }


    @Override
    public void getOTPBajajPa(String number) {
        bajajAccessToken = number;
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            BajajPayGetOtpRequest bajajPayGetOtpRequest = new BajajPayGetOtpRequest(number, AppConstant.merchantId, AppConstant.BAJAJPAY_subMerchantId, AppConstant.subMerchantName);
            String jsonData = new Gson().toJson(bajajPayGetOtpRequest);
            String encryptData = NewAESEncrypt.encrypt(jsonData.trim());
            BajajPayEncryptedRequest bajajPayEncryptedRequest = new BajajPayEncryptedRequest(encryptData);
            mPresenter.getBajajPayData(bajajPayEncryptedRequest);
        } else {
            Snackbar.make(tvError, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void verifyOTPDialog(BajajPayEncryptedRequest bajajPayEncryptedRequest, Context activity, IBPDownloadListener listener) {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.verifyOTPData(bajajPayEncryptedRequest);
        } else {
            Snackbar.make(tvError, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void authSendFromPayOTPPP(BajajPayEncryptedRequest authOtpEncRequest) {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.doPayBajajPayAuthOTP(authOtpEncRequest);
        } else {
            Snackbar.make(tvError, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void balanceBajajPay() {
        getBajajPayBalance();
    }

    @Override
    public void deLinkWallet() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            BajajPayDeLinkWalletRequest bajajPayDeLinkWalletRequest = new BajajPayDeLinkWalletRequest(AppConstant.merchantId, mAppPreference.getMobileNumberBP());
            String jsonData = new Gson().toJson(bajajPayDeLinkWalletRequest);
            String encryptDataResendOtp = NewAESEncrypt.encrypt(jsonData.trim());
            BajajPayEncryptedRequest bajajPayDeLinkWallet = new BajajPayEncryptedRequest(encryptDataResendOtp);
            mPresenter.delinkWallet(bajajPayDeLinkWallet);
        } else {
            Snackbar.make(tvError, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * Delink BajajPay Wallet Success and Failure
     */
    @Override
    public void onDeLinkWalletSuccess(BajajPayResponse response) {
        hideProgress();
        if (response.statusCode.equals("202")) {
            hideProgress();
            String decryptData = NewAESEncrypt.decrypt(response.encResponse);
            BajajPayDeLinkWalletDecryptResponse bajajPayDeLinkWalletDecryptResponse = new Gson().fromJson(decryptData, BajajPayDeLinkWalletDecryptResponse.class);
            mBajajPayDeLink.setVisibility(View.GONE);
            getMBajajPayDeLinkNetBaking.setVisibility(View.GONE);
            mBajajPayIV.setVisibility(View.VISIBLE);
            imgNetBankingBajajPayLink.setVisibility(View.VISIBLE);
            mBajajPayTV.setVisibility(View.VISIBLE);
            mBajajPayTV.setText(getString(R.string.bajaj_pay));
            mBajajPayTVNetBankingWallet.setVisibility(View.VISIBLE);
            mBajajPayTVNetBankingWallet.setText(getString(R.string.bajaj_pay));
            mAppPreference.getEditor().remove("mobileNumberBP").commit();
            mAppPreference.setMobileNumberBP(bajajPayDeLinkWalletDecryptResponse.getMobileNumber());
            mAppPreference.getEditor().remove("acTokenBP").commit();
            /*Call Api Link wallet*/
            if (new NetworkStatus(this).isInternetOn()) {
                showProgress(getString(R.string.txt_progress_authentication));
                mPresenter.getLinkBajajWallet(new LinkBajajWalletRequest(bajajPayDeLinkWalletDecryptResponse.getMobileNumber(), true, mAppPreference.getUserTokenBP(), userToken));
            } else {
                Snackbar.make(tvError, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
            }
        }
        Toast.makeText(this, getString(R.string.succesfully_delink_bajajpay), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeLinkWalletFailure(ApiError error) {
        hideProgress();
//        Toast.makeText(this, getString(R.string.delink_dailed_bajajpay), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBajajValidationSuccess(BaseResponse response) {
        hideProgress();
        if (response.isStatus()) {
            validationBajajPay();
        } else {
            AppUtilityMethods.showMsg(this, response.getMessage(), false);
        }
    }

    @Override
    public void onBajajValidationFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onLinkBajajSuccess(BaseResponse response) {
        AppDialog.showStatusSuccessBajajPayDialog(this, getString(R.string.linked_to_khiladiadda_successfully), this);
        if (response.isStatus()) {
            mBajajPayDeLink.setVisibility(View.VISIBLE);
        }
        hideProgress();
    }

    @Override
    public void onLinkBajajFailure(ApiError error) {
        mBajajPayDeLink.setVisibility(View.INVISIBLE);
        hideProgress();
    }

    @Override
    public void insufficientBalance(String balance) {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            /**Random Number should be generated for merchantTxnId and it should be greater than 10*/
            long merchantTxnId = AppUtilityMethods.randomNumber();
            BajajPayInsuffiencientBalanceRequest bajajPayInsuffiencientBalanceRequest = new BajajPayInsuffiencientBalanceRequest(AppConstant.merchantId, mAppPreference.getMobileNumberBP(), merchantTxnId, mAppPreference.getUserTokenBP(), balance, AppConstant.callBackURl, AppConstant.requestNumber, AppConstant.BAJAJPAY_subMerchantId);
            String jsonData = new Gson().toJson(bajajPayInsuffiencientBalanceRequest);
            String encryptDataResendOtp = NewAESEncrypt.encrypt(jsonData.trim());
            BajajPayEncryptedRequest bajajPayInSufficientBalanceEncryptedRequest = new BajajPayEncryptedRequest(encryptDataResendOtp);
            mPresenter.inSufficientBalance(bajajPayInSufficientBalanceEncryptedRequest);
        } else {
            Snackbar.make(tvError, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * Insufficient Balance success and failure
     */
    @Override
    public void onInsufficientBalanceBajajPaySuccess(BajajPayResponse response) {
        try {
            if (response.statusCode.equals("202")) {
                String decryptData = NewAESEncrypt.decrypt(response.encResponse);
                BajajPayInsufficientBalanceDecryptResponse responseData = new Gson().fromJson(decryptData, BajajPayInsufficientBalanceDecryptResponse.class);
                if (responseData.getMerchantId() != null) {
                    String url = responseData.getRedirectUrl();
                    Intent intent = new Intent(this, ActivityPayBP.class);
                    intent.putExtra(AppConstant.webUrl, url);
                    startActivity(intent);
                    hideProgress();
                    finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(mPayBTN, "There is some issue. Please contact support", Snackbar.LENGTH_LONG).show();
        }
        hideProgress();
    }

    @Override
    public void onInsufficientBalanceBajajPayFailure(ApiError error) {
        hideProgress();
        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /**calling get getBajajPayBalance when user return from ceate wallet*/
        if (mBajajWalletActive) {
            getBajajPayBalance();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((mAppPreference.getUserTokenBP() != null && mBajajWalletActive) || (mBajajWalletActive && mIsBajajWallet)) {
            getBajajPayBalance();
        }
        if (mPaySharp) {
            PaymentWaitDialog dialog = new PaymentWaitDialog(this, mOnPaymentWaitListener);
        }
    }

    ActivityResultLauncher<Intent> easebuzzResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getData() != null) {
                    String payment_response = result.getData().getStringExtra("payment_response");
                    try {
                        JSONObject jsonObject = new JSONObject(payment_response);
                        String status = String.valueOf(jsonObject.get("status"));
                        if (status.equalsIgnoreCase("userCancelled")) {
                            onPaymentFailed(2, "You have cancelled this transaction.", "");
                        } else {
                            showProgress(getString(R.string.txt_progress_authentication));
                            double amount = Double.parseDouble(String.valueOf(jsonObject.get("amount")));
                            mPresenter.saveEasebuzzPayment(amount, mCouponCode, String.valueOf(jsonObject.get("txnid")), String.valueOf(jsonObject.get("status")));
                        }
                    } catch (Exception ignored) {
                    }
                }
            });

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYTM_REQUEST_CODE && data != null) {
            try {
                PaymentRequest request = new PaymentRequest();
                request.setCustomerID(mAppPreference.getProfileData().getId());
                request.setType(AppConstant.PATYM);
                request.setCoupon(mCouponCode);
                request.setOrderId(mOrderId);
                request.setAmount(mAmount);
                showProgress(getString(R.string.txt_progress_authentication));
                mPresenter.savePaytmPayment(request);
            } catch (Exception e) {
//                Snackbar.make(mPayBTN, "Please try again.", Snackbar.LENGTH_LONG).show();
            }
        } else if (requestCode == B2B_PG_REQUEST_CODE) {  //phonepe
            if (resultCode == RESULT_OK) {
                showProgress("");
                PhonepeCheckPaymentRequest phonepeCheckPaymentRequest = new PhonepeCheckPaymentRequest();
                phonepeCheckPaymentRequest.setCoupon(mCouponCode);
                phonepeCheckPaymentRequest.setOrderId(mPhonepeOrderId);
                mPresenter.getPaymentCheckData(phonepeCheckPaymentRequest);
            } else {
                if (mPaymentFrom == AppConstant.FROM_PHONEPE_BAJAJPAY) {
                    showProgress("");
                    PhonepeCheckPaymentRequest phonepeCheckPaymentRequest = new PhonepeCheckPaymentRequest();
                    phonepeCheckPaymentRequest.setCoupon(mCouponCode);
                    phonepeCheckPaymentRequest.setOrderId(mPhonepeOrderId);
                    mPresenter.getPaymentCheckData(phonepeCheckPaymentRequest);
                } else {
                    AppDialog.showPaymentConfirmation(this, onPaymentListener, getString(R.string.text_payment_failed), "Payment Failed!", false);
                }
            }
        } else if (requestCode == AppConstant.PaymentDone && resultCode == RESULT_OK) {
            showProgress("");
            mPresenter.getApexPayStatus(mApexPayOrderId, mCouponCode);
        }
    }
    //Cashfree New SDK Callback
    @Override
    public void onPaymentVerify(String orderID){
        showProgress("Please wait...");
        mPresenter.getCashfreeStatus(orderID);
    }
    @Override
    public void onPaymentFailure(CFErrorResponse cfErrorResponse,String orderID){
        showProgress("Please wait...");
        mPresenter.getCashfreeStatus(orderID);
    }
    @Override
    public void onTransactionResponse(@Nullable Bundle inResponse){
        try {
            PaymentRequest request=new PaymentRequest();
            request.setCustomerID(mAppPreference.getProfileData().getId());
            request.setType(AppConstant.PATYM);
            request.setStatus(inResponse.getString(AppConstant.STATUS));
            request.setOrderId(mOrderId);
            request.setBankName(inResponse.getString(AppConstant.BANKNAME));
            request.setAmount(inResponse.getString(AppConstant.TXN_AMOUNT));
            request.setDate(inResponse.getString(AppConstant.TXN_DATE));
            request.setTxnId(inResponse.getString(AppConstant.TXN_ID));
            request.setPaymentMode(inResponse.getString(AppConstant.PAYMENT_MODE));
            request.setBankTxnId(inResponse.getString(AppConstant.BANK_TXN_ID));
            request.setCurrency(inResponse.getString(AppConstant.CURRENCY));
            request.setGatewayName(inResponse.getString(AppConstant.GATEWAY_NAME));
            request.setCoupon(mCouponCode);
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.savePaytmPayment(request);
        } catch (Exception e) {
            Snackbar.make(mPayBTN, "Please try again.", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void networkNotAvailable() {
        Snackbar.make(mPayBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorProceed(String s) {

    }

    @Override
    public void clientAuthenticationFailed(String inErrorMessage) {
    }

    @Override
    public void someUIErrorOccurred(String inErrorMessage) {
    }

    @Override
    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
    }

    @Override
    public void onBackPressedCancelTransaction() {
        Snackbar.make(mPayBTN, getString(R.string.text_transaction_cancelled_back_pressed), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
        Snackbar.make(mPayBTN, getString(R.string.text_transaction_cancelled), Snackbar.LENGTH_LONG).show();
    }

    private void callPaymentGateway() {
        showProgress(getString(R.string.txt_progress_authentication));
        if (mPaymentFrom == AppConstant.FROM_PAYTM) {
            String newAmount = mAmount + ".00";
            mOrderId = UUID.randomUUID().toString();
            mOrderId = mOrderId.replaceAll("-", "");
            mCallbackURL = AppConstant.PaytmProductionCallbackURL + mOrderId;
            mPresenter.getPaytmChecksum(mOrderId, newAmount, mCallbackURL, mCouponCode);
        } else if (mPaymentFrom == AppConstant.FROM_CASHFREE || mPaymentFrom == AppConstant.FROM_CASHFREE_GPAY || mPaymentFrom == AppConstant.FROM_CASHFREE_PPAY || mPaymentFrom == AppConstant.FROM_CASHFREE_APAY || mPaymentFrom == AppConstant.FROM_CASHFREE_UPI) {
            mPresenter.getCashfreeChecksum(mAmount,mCouponCode);
        } else if (mPaymentFrom == AppConstant.FROM_PAYU) {
            mPresenter.getPayuChecksum(mAmount, mCouponCode);
        } else if (mPaymentFrom == AppConstant.FROM_PAYKUN) {
            mPresenter.getApexPayChecksum(mAmount, mCouponCode);
        } else if (mPaymentFrom == AppConstant.FROM_PAYSHARP) {
            getPaySharp(mCouponCode);
        } else if (mPaymentFrom == AppConstant.FROM_EASEBUZZ) {
            double mEaseBuzzAmount = Double.parseDouble(mAmount.trim());
            mPresenter.getEasebuzzHash(mEaseBuzzAmount, mCouponCode);
        } else if (mPaymentFrom == AppConstant.FROM_NEOKRED) {
            double mEaseBuzzAmount = Double.parseDouble(mAmount.trim());
            mPresenter.checkNeokredPG(mEaseBuzzAmount, mCouponCode);
        } else if (mPaymentFrom == AppConstant.FROM_PHONEPE || mPaymentFrom == AppConstant.FROM_PHONEPE_PhonePePAY || mPaymentFrom == AppConstant.FROM_PHONEPE_GPAY || mPaymentFrom == AppConstant.FROM_PHONEPE_PaytmPAY || mPaymentFrom == AppConstant.FROM_PHONEPE_BAJAJPAY) {
            phonePePaymentIntegration();
        }
    }

    private void getPaySharp(String couponCode) {
        showProgress(getString(R.string.txt_progress_authentication));
        final int random = new Random().nextInt(61) + 20;
        PaySharpRequest request = new PaySharpRequest();
        request.setAmount(Long.parseLong(mAmount));
        request.setCustomerEmail(mAppPreference.getEmail());
        request.setCustomerId(mAppPreference.getProfileData().getId());
        request.setCustomerMobileNo(mAppPreference.getMobile());
        request.setCustomerName(mAppPreference.getName());
        request.setOrderId(String.valueOf(random));
        request.setRemarks(String.valueOf(random));
        mPresenter.getPaySharp(request, couponCode);
    }

    //PhonePe Payment Integration
    private void phonePePaymentIntegration() {
        if (new NetworkStatus(this).isInternetOn()) {
            PhonepeRequest phonepeRequest = new PhonepeRequest();
            if (!mAmount.equals("")) {
                if (Integer.parseInt(mAmount) != 0) {
                    phonepeRequest.setAmount(Integer.parseInt(mAmount));
                    if (mPaymentFrom == AppConstant.FROM_PHONEPE_PhonePePAY || mPaymentFrom == AppConstant.FROM_PHONEPE) {
                        phonepeRequest.setTargetApp(1);
                    } else if (mPaymentFrom == AppConstant.FROM_PHONEPE_GPAY) {
                        phonepeRequest.setTargetApp(2);
                    } else if (mPaymentFrom == AppConstant.FROM_PHONEPE_BAJAJPAY) {
                        phonepeRequest.setTargetApp(4);  //phonepeRequest.setTargetApp(4);
                    } else {
                        phonepeRequest.setTargetApp(3);
                    }
                    phonepeRequest.setCoupon(mCouponCode);
                    mPresenter.getPaymentUrlData(phonepeRequest);
                } else Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show();
        } else {
            Snackbar.make(mPayBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void startCashfreeNewSDK(CashfreeChecksumResponse cashfreeChecksumData) {
        try {
            CFSession cfSession = new CFSession.CFSessionBuilder()
                    .setEnvironment(CFSession.Environment.PRODUCTION)
                    .setPaymentSessionID(cashfreeChecksumData.getChecksum())
                    .setOrderId(cashfreeChecksumData.getOrderId())
                    .build();
            //setting theme in pg screen
            CFTheme cfTheme = new CFTheme.CFThemeBuilder()
                    .setNavigationBarBackgroundColor("#ed213a")
                    .setNavigationBarTextColor("#FFFFFF")
                    .setButtonBackgroundColor("#ed213a")
                    .setButtonTextColor("#FFFFFF")
                    .setPrimaryTextColor("#000000")
                    .setSecondaryTextColor("#000000")
                    .build();
            if (mPaymentFrom == AppConstant.FROM_CASHFREE) {
                //For payment modes
                CFPaymentComponent cfPaymentComponent = new CFPaymentComponent.CFPaymentComponentBuilder()
                        .add(CFPaymentComponent.CFPaymentModes.CARD)
                        .add(CFPaymentComponent.CFPaymentModes.UPI)
                        .add(CFPaymentComponent.CFPaymentModes.WALLET)
                        .add(CFPaymentComponent.CFPaymentModes.NB)
                        .build();
                //checkout payment
                CFDropCheckoutPayment cfDropCheckoutPayment = new CFDropCheckoutPayment.CFDropCheckoutPaymentBuilder()
                        .setSession(cfSession)
                        .setCFUIPaymentModes(cfPaymentComponent)
                        .setCFNativeCheckoutUITheme(cfTheme)
                        .build();
                CFPaymentGatewayService gatewayService = CFPaymentGatewayService.getInstance();
                gatewayService.doPayment(PaymentActivity.this, cfDropCheckoutPayment);
            } else {
                CFUPIIntentCheckout cfupiIntentCheckout = new CFUPIIntentCheckout.CFUPIIntentBuilder()
                        // Use either the enum or the application package names to order the UPI apps as per your needed
                        // Remove both if you want to use the default order which cashfree provides based on the popularity
                        // NOTE - only one is needed setOrder or setOrderUsingPackageName
//                        .setOrder(Arrays.asList(CFUPIIntentCheckout.CFUPIApps.BHIM, CFUPIIntentCheckout.CFUPIApps.PHONEPE, CFUPIIntentCheckout.CFUPIApps.GOOGLE_PAY, CFUPIIntentCheckout.CFUPIApps.PAYTM))
                        .build();
                CFUPIIntentCheckoutPayment cfDropCheckoutPayment = new CFUPIIntentCheckoutPayment.CFUPIIntentPaymentBuilder()
                        .setSession(cfSession)
                        .setCfUPIIntentCheckout(cfupiIntentCheckout)
                        .build();
                CFPaymentGatewayService gatewayService = CFPaymentGatewayService.getInstance();
                gatewayService.doPayment(PaymentActivity.this, cfDropCheckoutPayment);
            }
        } catch (CFException exception) {
            exception.printStackTrace();
        }
    }
    /*Below code WebCheckout*/
    private void startCashfreeWebCheckout(CashfreeChecksumResponse cashfreeChecksumData) {
        try {
            CFSession cfSession = new CFSession.CFSessionBuilder()
                    .setEnvironment(CFSession.Environment.PRODUCTION)
                    .setPaymentSessionID(cashfreeChecksumData.getChecksum())
                    .setOrderId(cashfreeChecksumData.getOrderId())
                    .build();
            //setting theme in pg screen
            // Replace with your application's theme colors
            CFWebCheckoutTheme cfTheme = new CFWebCheckoutTheme.CFWebCheckoutThemeBuilder()
                    .setNavigationBarBackgroundColor("#fc2678")
                    .setNavigationBarTextColor("#ffffff")
                    .build();
            CFWebCheckoutPayment cfWebCheckoutPayment=new CFWebCheckoutPayment.CFWebCheckoutPaymentBuilder()
                    .setSession(cfSession)
                    .setCFWebCheckoutUITheme(cfTheme)
                    .build();
            CFPaymentGatewayService gatewayService = CFPaymentGatewayService.getInstance();
            gatewayService.doPayment(PaymentActivity.this,cfWebCheckoutPayment);

        } catch (CFException exception) {
            exception.printStackTrace();
        }
    }

    private void onAllPaymentCompleteCheck(boolean status, String message, String payment_via) {
        hideProgress();
        if (status) {
            onPaymentSuccess(payment_via);
        } else {
            onPaymentFailed(1, message, payment_via);
        }
    }

    private void onAllPaymentFailed() {
        hideProgress();
        onPaymentFailed(2, "", "");
    }

    private void onPaymentSuccess(String payment_via) {
        mAppPreference.setIsProfile(false);
        String mHeading = getString(R.string.head_payment_success);
        String mDisplayMsg = getString(R.string.text_congrats) + mAmount + getString(R.string.text_coins_added_success);
        AppDialog.showPaymentConfirmation(this, onPaymentListener, mHeading, mDisplayMsg, true);
        Map<String, Object> eventParameters5 = new HashMap<>();
        eventParameters5.put(AFInAppEventParameterName.REVENUE, mAmount); // Amount of the top-up
        eventParameters5.put(AFInAppEventParameterName.CURRENCY, AppConstant.INR);
        AppsFlyerLib.getInstance().logEvent(getApplicationContext(), "top_up_success", eventParameters5);
        //Mo Engage
        Properties properties = new Properties();
        properties.addAttribute("Transaction Date & Time", new Date())
                .addAttribute("Amount", mAmount)
                .addAttribute("currency", "INR")
                .addAttribute("pay via", payment_via)
                .addAttribute("coupon code", mCouponCode);
        MoEAnalyticsHelper.INSTANCE.trackEvent(this, "Add Wallet Success", properties);
    }

    private void onPaymentFailed(int from, String message, String payment_via) {
        if (from == 2) {
            String mHeading = getString(R.string.text_payment_failed);
            String mDisplayMsg = getString(R.string.text_payment_failed_message);
            AppDialog.showPaymentConfirmation(this, onPaymentListener, mHeading, mDisplayMsg, false);
        } else if (from == 3) {
            AppDialog.showPaymentConfirmation(this, onPaymentListener, "", message, false);
        } else {
            AppDialog.showPaymentConfirmation(this, onPaymentListener, "", message, false);
        }
        Map<String, Object> eventParameters6 = new HashMap<>();
        eventParameters6.put("error_reason", getString(R.string.text_payment_failed_message)); // Reason for the top-up failure
        AppsFlyerLib.getInstance().logEvent(getApplicationContext(), "top_up_unsuccessful", eventParameters6);
        // Mo Engage
        Properties properties = new Properties();
        properties.addAttribute("Transaction Date & Time", new Date())
                .addAttribute("Amount", mAmount)
                .addAttribute("Currency", "INR")
                .addAttribute("pay via", payment_via)
                .addAttribute("coupon code", mCouponCode);
        MoEAnalyticsHelper.INSTANCE.trackEvent(this, "Add Wallet Failed", properties);
    }

    private final IOnPaymentListener onPaymentListener = result -> {
        if (result) {
            Intent intent = new Intent();
            mAppPreference.setBoolean(AppConstant.FROM_WALLET, true);
            setResult(AppConstant.REQUEST_ADD_WALLET, intent);
            finish();
        }
    };

    private final IOnPaymentWaitListener mOnPaymentWaitListener = new IOnPaymentWaitListener() {
        @Override
        public void onPaymentWaitComplete() {
            if (mApexPay) {
                mPresenter.getApexPayStatus(mApexPayOrderId, mCouponCode);
            } else if (mPaySharp) {
                mPresenter.getPaySharpStatus(mPaySharpOrderId, mCouponCode);
            }
        }
    };

    private void checkGamerCashStatus() {
        long mCoins = Long.parseLong(mAmount);
        if (mAppPreference.getIsGamerCashLinked()) {
            if (mCoins > mGamerCash) {
                AppDialog.showInsufficientGCDialog(this);
            } else {
                Intent intent = new Intent(this, PayActivity.class);
                intent.putExtra("coins", mGamerCash);
                intent.putExtra("coupon", mCouponCode);
                intent.putExtra("enter_amount", mAmount);
                startActivity(intent);
                finish();
            }
        } else {
            Intent intent = new Intent(this, GamerCashActivity.class);
            intent.putExtra("coins", mGamerCash);
            intent.putExtra("enter_amount", mAmount);
            startActivity(intent);
        }
    }

    private void startPaytmNew(String checksumHash) {
        PaytmOrder paytmOrder = new PaytmOrder(mOrderId, AppUtilityMethods.getURL(AppConstant.PaytmProductionMID), checksumHash, mAmount, mCallbackURL);
        TransactionManager transactionManager = new TransactionManager(paytmOrder, this);
        transactionManager.startTransaction(this, PAYTM_REQUEST_CODE);
    }

    private void phonePePayment(PhonePePaymentResponse response) {
        String upiPackage = "";
        if(mPaymentFrom==AppConstant.FROM_PHONEPE_GPAY){
            upiPackage = "com.google.android.apps.nbu.paisa.user";
        } else if (mPaymentFrom == AppConstant.FROM_PHONEPE_PaytmPAY) {
            upiPackage = "net.one97.paytm";
        } else if (mPaymentFrom == AppConstant.FROM_PHONEPE_BAJAJPAY) {
            upiPackage = "org.altruist.BajajExperia";
        } else {
            upiPackage = "com.phonepe.app";
        }
        try {
            List<UPIApplicationInfo> upiApps = PhonePe.getUpiApps();
            if (upiApps.size() > 0) {
                for (int i = 0; i < upiApps.size(); i++) {
                    if (upiApps.get(i).getPackageName().equalsIgnoreCase(upiPackage)) {
                        //prod //String string_signature = PhonePe.getPackageSignature();
                        mPhonepeOrderId = response.getTransactionId();
                        B2BPGRequest b2BPGRequest = new B2BPGRequestBuilder()
                                .setData(response.getBase64())
                                .setChecksum(response.getChecksum())
                                .setUrl(mApiEndPoint)
                                .build();
                        //Package name should be dynamic //UAT = com.phonepe.app.preprod //PROD = com.phonepe.app
                        try {
                            if (isPackageInstalled(upiPackage)) {
                                startActivityForResult(PhonePe.getImplicitIntent(
                                        this, b2BPGRequest, upiPackage), B2B_PG_REQUEST_CODE);
                                break;
                            } else {
                                showAppNotInstalledMsg();
                            }
                        } catch (PhonePeInitException e) {
                            showAppNotInstalledMsg();
                        }
                    } else if ((upiApps.size() - 1) == i) {
                        showAppNotInstalledMsg();
                    }
                }
            } else {
                showAppNotInstalledMsg();
            }
        } catch (PhonePeInitException exception) {
            showAppNotInstalledMsg();
        }
    }

    private void showAppNotInstalledMsg() {
        if (mPaymentFrom == AppConstant.FROM_PHONEPE_BAJAJPAY) {
            AppUtilityMethods.openAppLinkToDownload(this, "Bajaj Pay UPI App is not installed on your device or you haven't setup your UPI yet.\n" +
                    "Please click on continue to download the app.");
        } else {
            AppUtilityMethods.showMsg(this, "This UPI App is not installed on your device or you haven't setup your UPI yet.\n" +
                    "Please retry With another UPI App", false);
        }
    }

    private boolean isPackageInstalled(String packageName) {
        PackageManager packageManager = getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void payGamerCashVerifyAPI() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress("");
            mPresenter.getGamerCashUserData();
        } else {
            Snackbar.make(mPayBTN,R.string.error_internet,Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }
    private void checkBajajpayLinked(HashMap<String, Boolean> data) {
        if (data.get(AppConstant.IS_LINKED) == true && data.get(AppConstant.IS_DELINK) == false) {
//            getBajajPayBalance();
        } else if (data.get(AppConstant.IS_LINKED) == false && data.get(AppConstant.IS_DELINK) == true) {
            deLinkWallet();
        }
    }
}