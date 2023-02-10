package com.khiladiadda.wallet;

import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_APP_ID;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_CURRENCY;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_ID;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_NOTE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.easebuzz.payment.kit.PWECouponsActivity;
import com.gocashfree.cashfreesdk.CFPaymentService;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.dialogs.interfaces.IOnPaymentListener;
import com.khiladiadda.dialogs.interfaces.IOnPaymentWaitListener;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.gamercash.GamerCashActivity;
import com.khiladiadda.gamercash.PayActivity;
import com.khiladiadda.main.MainActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.CashfreeSavePayment;
import com.khiladiadda.network.model.request.PaySharpRequest;
import com.khiladiadda.network.model.request.PaymentRequest;
import com.khiladiadda.network.model.request.PhonepeCheckPaymentRequest;
import com.khiladiadda.network.model.request.PhonepeRequest;
import com.khiladiadda.network.model.response.ApexPayChecksumResponse;
import com.khiladiadda.network.model.response.CashfreeChecksumResponse;
import com.khiladiadda.network.model.response.ChecksumResponse;
import com.khiladiadda.network.model.response.Coins;
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
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.wallet.interfaces.IWalletPresenter;
import com.khiladiadda.wallet.interfaces.IWalletView;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;
import com.moengage.inapp.MoEInAppHelper;
import com.moengage.widgets.NudgeView;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
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
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import butterknife.BindView;

public class AddWalletActivity extends BaseActivity implements IWalletView, PaytmPaymentTransactionCallback {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1001;

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.et_amount)
    EditText mAmountET;
    @BindView(R.id.tv_coins)
    TextView mTotalCoinsTV;
    @BindView(R.id.tv_coupon_code)
    TextView mCouponCodeTV;
    @BindView(R.id.ll_coupon)
    LinearLayout mCouponLL;
    @BindView(R.id.et_coupon_code)
    EditText mCouponCodeET;
    @BindView(R.id.btn_confirm_coupon)
    TextView mConfirmCouponBTN;
    @BindView(R.id.tv_coupon_applied)
    TextView mCouponAppliedTV;
    @BindView(R.id.tv_coupon_delete)
    TextView mCouponDeleteTV;
    @BindView(R.id.iv_gpay)
    ImageView mGPayIV;
    @BindView(R.id.iv_apay)
    ImageView mAPayIV;
    @BindView(R.id.iv_phonepe)
    ImageView mPhonePayIV;
    @BindView(R.id.tv_paytm)
    TextView mPaytmTV;
    @BindView(R.id.tv_cashfree)
    TextView mCashfreeTV;
    @BindView(R.id.tv_paysharp)
    TextView mPaysharpTV;
    @BindView(R.id.tv_apexpay)
    TextView mApexPayTV;
    @BindView(R.id.tv_easebuzz)
    TextView mEasebuzzTV;
    @BindView(R.id.btn_pay)
    Button mPayBTN;
    @BindView(R.id.tv_pg)
    TextView mPGTV;
    @BindView(R.id.tv_upi)
    TextView mUPITV;
    @BindView(R.id.tv_neokred)
    TextView mNeokredTV;
    //GamerCash
    @BindView(R.id.tv_gamercash)
    TextView mGamerCashTV;
    @BindView(R.id.tv_gamercash_verified)
    TextView mGamerCashVerifiedTV;
    @BindView(R.id.nudge)
    NudgeView mNV;
    @BindView(R.id.tv_phonepe)
    TextView mPhonepeTV;

    private String mCouponCode, mOrderId, mAmount, mCallbackURL, mPhonepeOrderId, mApexPayOrderId, mPaySharpOrderId;
    private IWalletPresenter mPresenter;
    private long mCoins;
    private int mPaymentFrom, mUpiPaymentType, coins, getmPaymentFrom = 0;
    private boolean mApexPay, mPaySharp;
    private final String mApiEndPoint = "/pg/v1/pay";
    private static final int B2B_PG_REQUEST_CODE = 777;
    private boolean mIsPhoneUPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PhonePe.init(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_add_wallet;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mNV.initialiseNudgeView(this);
        MoEInAppHelper.getInstance().showInApp(this);
    }

    @Override
    protected void initViews() {
        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            String redirect = intent.getString(AppConstant.PushFrom);
            if (redirect.equalsIgnoreCase(AppConstant.MoEngage)) {
                mAppPreference.setIsDeepLinking(true);
            }
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mActivityNameTV.setText(R.string.my_add_money);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mCouponCodeTV.setOnClickListener(this);
        mConfirmCouponBTN.setOnClickListener(this);
        mCouponDeleteTV.setOnClickListener(this);
        mPaytmTV.setOnClickListener(this);
        mCashfreeTV.setOnClickListener(this);
        mPaysharpTV.setOnClickListener(this);
        mApexPayTV.setOnClickListener(this);
        mEasebuzzTV.setOnClickListener(this);
        mGPayIV.setOnClickListener(this);
        mAPayIV.setOnClickListener(this);
        mPhonePayIV.setOnClickListener(this);
        mPayBTN.setOnClickListener(this);
        mNeokredTV.setOnClickListener(this);
        mPhonepeTV.setOnClickListener(this);
        mGamerCashTV.setOnClickListener(this);
        mGamerCashVerifiedTV.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mPresenter = new WalletPresenter(this);
        setData();
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getVersionDetails();
        } else {
            Snackbar.make(mAmountET, getString(R.string.error_internet), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        Coins coins = mAppPreference.getProfileData().getCoins();
        double total = coins.getDeposit() + coins.getBonus() + coins.getWinning();
        if (String.valueOf(total).contains(".")) {
            mTotalCoinsTV.setText("Total Balance\n" + "₹" + String.format("%.2f", total));
        } else {
            mTotalCoinsTV.setText("Total Balance\n" + "₹" + total);
        }
        SpannableString totalCoin = new SpannableString(mTotalCoinsTV.getText().toString());
        totalCoin.setSpan(new RelativeSizeSpan(1.5f), 13, totalCoin.length(), 0); // set size
        mTotalCoinsTV.setText(totalCoin);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                if (mAppPreference.getIsDeepLinking()) {
                    finish();
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    finish();
                }
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.tv_phonepe:
                setPaymentGateway(11);
                break;
            case R.id.iv_gpay:
                setPaymentGateway(1);
                break;
            case R.id.iv_apay:
                setPaymentGateway(2);
                break;
            case R.id.iv_phonepe:
                setPaymentGateway(3);
                break;
            case R.id.tv_cashfree:
                setPaymentGateway(4);
                break;
            case R.id.tv_paytm:
                setPaymentGateway(5);
                break;
            case R.id.tv_paysharp:
                setPaymentGateway(6);
                break;
            case R.id.tv_apexpay:  //apexpay
                setPaymentGateway(7);
                break;
            case R.id.tv_easebuzz:
                setPaymentGateway(8);
                break;
            case R.id.tv_neokred:
                setPaymentGateway(9);
                break;
            case R.id.tv_coupon_code:
                if (TextUtils.isEmpty(mAmountET.getText().toString().trim())) {
                    AppUtilityMethods.showMsg(this, "Please enter the amount first and then apply coupon code.", true);
                } else {
                    double coins = Double.parseDouble(mAmountET.getText().toString().trim());
                    if (coins < 10) {
                        AppUtilityMethods.showMsg(this, "Amount cannot be less than Rs.10", true);
                    } else {
                        mCouponCodeTV.setVisibility(View.GONE);
                        mCouponLL.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.btn_confirm_coupon:
                if (TextUtils.isEmpty(mCouponCodeET.getText().toString().trim())) {
                    AppUtilityMethods.showMsg(this, "Coupon code cannot be empty.", true);
                } else {
                    mCouponCode = mCouponCodeET.getText().toString().trim();
                    mCouponCodeET.setEnabled(false);
                    mCouponAppliedTV.setVisibility(View.VISIBLE);
                    mCouponDeleteTV.setVisibility(View.VISIBLE);
                    mConfirmCouponBTN.setEnabled(false);
                    showProgress(getString(R.string.txt_progress_authentication));
                    mPresenter.applyCoupon(mCouponCode);
                }
                break;
            case R.id.tv_coupon_delete:
                mCouponCode = "";
                mCouponCodeET.setText("");
                mCouponCodeET.setEnabled(true);
                mCouponAppliedTV.setVisibility(View.GONE);
                mCouponDeleteTV.setVisibility(View.GONE);
                mConfirmCouponBTN.setEnabled(true);
                break;
            case R.id.btn_pay:
                if (getmPaymentFrom == 1) {
                    checkGamerCashStatus();
                } else {
                    validateAmount();
                }
                break;
            case R.id.tv_gamercash:
            case R.id.tv_gamercash_verified:
                setPaymentGateway(10);
                getmPaymentFrom = 1;
                break;
        }
    }

    private void setPaymentGateway(int from) {
        mGPayIV.setSelected(false);
        mAPayIV.setSelected(false);
        mPhonePayIV.setSelected(false);
        mPaytmTV.setSelected(false);
        mCashfreeTV.setSelected(false);
        mPaysharpTV.setSelected(false);
        mApexPayTV.setSelected(false);
        mEasebuzzTV.setSelected(false);
        mNeokredTV.setSelected(false);
        mPhonepeTV.setSelected(false);
        mGamerCashTV.setSelected(false);
        switch (from) {
            case 1:
                mGPayIV.setSelected(true);
                mPaymentFrom = AppConstant.FROM_GPAY_UPI;
                break;
            case 2:
                mAPayIV.setSelected(true);
                mPaymentFrom = AppConstant.FROM_PAYTM_UPI;
                break;
            case 3:
                mPhonePayIV.setSelected(true);
                mPaymentFrom = AppConstant.FROM_PHONEPAY_UPI;
                break;
            case 4:
                mCashfreeTV.setSelected(true);
                mPaymentFrom = AppConstant.FROM_CASHFREE;
                break;
            case 5:
                mPaytmTV.setSelected(true);
                mPaymentFrom = AppConstant.FROM_PAYTM;
                if (ContextCompat.checkSelfPermission(AddWalletActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddWalletActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                }
                break;
            case 6:
                mPaysharpTV.setSelected(true);
                mPaymentFrom = AppConstant.FROM_PAYSHARP;
                break;
            case 7:
                mApexPayTV.setSelected(true);
                mPaymentFrom = AppConstant.FROM_PAYKUN;
                break;
            case 8:
                mEasebuzzTV.setSelected(true);
                mPaymentFrom = AppConstant.FROM_EASEBUZZ;
                break;
            case 9:
                mNeokredTV.setSelected(true);
                mPaymentFrom = AppConstant.FROM_NEOKRED;
                break;
            case 10:
                mGamerCashTV.setSelected(true);
                mGamerCashVerifiedTV.setSelected(true);
                mPaymentFrom = AppConstant.FROM_GAMECASE;
                break;
            case 11:
                mPhonepeTV.setSelected(true);
                mPaymentFrom = AppConstant.FROM_PHONEPE;
                break;
        }
        mPayBTN.setVisibility(View.VISIBLE);
    }

    private void validateAmount() {
        if (new NetworkStatus(this).isInternetOn()) {
            mPresenter.validateData();
        } else {
            Snackbar.make(mAmountET, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void checkPanStatus() {
        switch (mAppPreference.getProfileData().getAadharUpdated()) {
            case 0:
                AppUtilityMethods.showProfileUpdateMsg(this, getString(R.string.text_pan_verify), 2, false);
                break;
            case 1:
                AppUtilityMethods.showProfileUpdateMsg(this, getString(R.string.text_pan_verify), 2, false);
                break;
            case 2:
                AppUtilityMethods.showProfileUpdateMsg(this, getString(R.string.text_pan_pending), 3, false);
                break;
            case 4:
                AppUtilityMethods.showProfileUpdateMsg(this, getString(R.string.text_pan_rejected), 4, false);
                break;
        }
    }

    @Override
    public String getAmount() {
        return mAmountET.getText().toString().trim();
    }

    @Override
    public void onValidationComplete() {
        mCoins = Long.parseLong(mAmountET.getText().toString().trim());
        double mEaseBuzzAmount = Double.parseDouble(mAmountET.getText().toString().trim());
        mAmount = mAmountET.getText().toString().trim() + ".00";
        if (mCoins > 5000 && mAppPreference.getProfileData().getAadharUpdated() != 3) {
            checkPanStatus();
        } else {
            showProgress(getString(R.string.txt_progress_authentication));
            if(mPaymentFrom == AppConstant.FROM_GPAY_UPI){
                if(mUpiPaymentType == 1){
                    mPaymentFrom = AppConstant.FROM_CASHFREE_UPI;
                } else  if(mUpiPaymentType == 2){
                    mPaymentFrom = AppConstant.FROM_PHONEPE_GPAY;
                }
            }
            else if(mPaymentFrom == AppConstant.FROM_PAYTM_UPI){
                if(mUpiPaymentType == 1){
                    mPaymentFrom = AppConstant.FROM_CASHFREE_UPI;
                } else  if(mUpiPaymentType == 2){
                    mPaymentFrom = AppConstant.FROM_PHONEPE_PaytmPAY;
                }
            }
            else if(mPaymentFrom == AppConstant.FROM_PHONEPAY_UPI){
                if(mUpiPaymentType == 1){
                    mPaymentFrom = AppConstant.FROM_CASHFREE_UPI;
                } else  if(mUpiPaymentType == 2){
                    mPaymentFrom = AppConstant.FROM_PHONEPE_PhonePePAY;
                }
            }
            if (mPaymentFrom == AppConstant.FROM_PAYTM) {
                mOrderId = UUID.randomUUID().toString();
                mOrderId = mOrderId.replaceAll("-", "");
                mCallbackURL = AppConstant.PaytmProductionCallbackURL + mOrderId;
                mPresenter.getPaytmChecksum(mOrderId, mAmount, mCallbackURL, mCouponCode);
            } else if (mPaymentFrom == AppConstant.FROM_CASHFREE || mPaymentFrom == AppConstant.FROM_CASHFREE_GPAY || mPaymentFrom == AppConstant.FROM_CASHFREE_PPAY || mPaymentFrom == AppConstant.FROM_CASHFREE_APAY || mPaymentFrom == AppConstant.FROM_CASHFREE_UPI) {
                mPresenter.getCashfreeChecksum(mAmountET.getText().toString().trim(), mCouponCode);
            } else if (mPaymentFrom == AppConstant.FROM_PAYU) {
                mPresenter.getPayuChecksum(mAmountET.getText().toString().trim(), mCouponCode);
            } else if (mPaymentFrom == AppConstant.FROM_PAYKUN) {
                mPresenter.getApexPayChecksum(String.valueOf(mCoins), mCouponCode);
            } else if (mPaymentFrom == AppConstant.FROM_PAYSHARP) {
                getPaySharp(mCouponCode);
            } else if (mPaymentFrom == AppConstant.FROM_EASEBUZZ) {
                mPresenter.getEasebuzzHash(mEaseBuzzAmount, mCouponCode);
            } else if (mPaymentFrom == AppConstant.FROM_NEOKRED) {
                mPresenter.checkNeokredPG(mEaseBuzzAmount, mCouponCode);
            } else if (mPaymentFrom == AppConstant.FROM_PHONEPE || mPaymentFrom == AppConstant.FROM_PHONEPE_PhonePePAY || mPaymentFrom == AppConstant.FROM_PHONEPE_GPAY || mPaymentFrom == AppConstant.FROM_PHONEPE_PaytmPAY) {
                paymentIntegration();
            }
        }
    }

    private void checkGamerCashStatus() {
        mCoins = Long.parseLong(mAmountET.getText().toString().trim());
        if (mAmountET.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please add amount to proceed.\nMinimum amount to be added is Rs.10", Toast.LENGTH_SHORT).show();
        } else {
            if (mAppPreference.getIsGamerCashLinked()) {
                if (mCoins > coins) {
                    AppDialog.showInsufficientGCDialog(this);
                } else {
                    if (mCoins > 5000 && mAppPreference.getProfileData().getAadharUpdated() != 3) {
                        checkPanStatus();
                    } else {
                        Intent intent = new Intent(this, PayActivity.class);
                        intent.putExtra("coins", coins);
                        intent.putExtra("coupon", mCouponCode);
                        intent.putExtra("enter_amount", mAmountET.getText().toString().trim());
                        startActivity(intent);
                        finish();
                    }
                }
            } else {
                Intent intent = new Intent(this, GamerCashActivity.class);
                intent.putExtra("coins", coins);
                intent.putExtra("enter_amount", mAmountET.getText().toString().trim());
                startActivity(intent);
            }
        }
    }

    @Override
    public void onValidationFailure(String errorMsg) {
        AppUtilityMethods.showMsg(this, errorMsg, false);
    }

    @Override
    public void onProfileComplete(ProfileTransactionResponse responseModel) {
    }

    @Override
    public void onProfileFailure(ApiError error) {
    }

    @Override
    public void onVersionSuccess(VersionResponse response) {
        if (response.isStatus()) {
            mUpiPaymentType = response.getVersion().getUpiEnable();
//            mIsPhoneUPI = response.getVersion().isPhonepeUpi();
            if (response.getVersion().isGamerCashEnabled()) {
                mGamerCashTV.setVisibility(View.VISIBLE);
            }
            if (response.getVersion().isCashfreeEnable()) {
                mCashfreeTV.setVisibility(View.VISIBLE);
                enableUPI();
            }
            if (response.getVersion().isPaytmEnable()) {
                mPaytmTV.setVisibility(View.VISIBLE);
            }
            if (response.getVersion().isRazorpayEnable()) {
                mPaysharpTV.setVisibility(View.VISIBLE);
                enableUPI();
            }
            if (response.getVersion().isPaykunEnable()) {
                mApexPayTV.setVisibility(View.VISIBLE);
            }
            if (response.getVersion().isEasebuzzEnable()) {
                mEasebuzzTV.setVisibility(View.VISIBLE);
            }
            if (response.getVersion().isNeokredEnable()) {
                mNeokredTV.setVisibility(View.VISIBLE);
            }
            if (response.getVersion().isPhonepeEnabled()) {
                enableUPI();
                mPhonepeTV.setVisibility(View.VISIBLE);
            }
            if (response.getVersion().isCashfreeEnable() || response.getVersion().isPaytmEnable() || response.getVersion().isRazorpayEnable() || response.getVersion().isPaykunEnable() || response.getVersion().isEasebuzzEnable()) {
                mPGTV.setVisibility(View.VISIBLE);
            }
        }
        payGamerCashVerifyAPI();
    }

    @Override
    public void onVersionFailure(ApiError error) {
        hideProgress();
    }

    //Paytm
    @Override
    public void onPaytmChecksumComplete(ChecksumResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            startPaytm(responseModel.getResponse());
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

    //Cashfree
    @Override
    public void onCashfreeChecksumComplete(CashfreeChecksumResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            startCashfree(responseModel);
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

    //Payu
    @Override
    public void onPayuChecksumComplete(PayuChecksumResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            startPayu(responseModel);
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override
    public void onPayuChecksumFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onPayuPaymentComplete(BaseResponse responseModel) {
        onAllPaymentCompleteCheck(responseModel.isStatus(), responseModel.getMessage(), responseModel.getPayment_via());
    }

    @Override
    public void onPayuPaymentFailure(ApiError error) {
        onAllPaymentFailed();
    }

    //Razorpay
    @Override
    public void onRazorpayOrderIdComplete(RazorpayOrderIdResponse responseModel) {
    }

    @Override
    public void onRazorpayOrderIdFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onRazorpayPaymentComplete(BaseResponse responseModel) {
    }

    @Override
    public void onRazorpayPaymentFailure(ApiError error) {
        onAllPaymentFailed();
    }

    //Paykun
    @Override
    public void onPaykunOrderIdComplete(PaykunOrderResponse responseModel) {
    }

    @Override
    public void onPaykunOrderIdFailure(ApiError error) {
        hideProgress();
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
    public void onInvoiceComplete(InvoiceResponse responseModel) {

    }

    @Override
    public void onInvoiceFailure(ApiError error) {

    }

    @Override
    public void onApplyCouponComplete(BaseResponse responseModel) {
        hideProgress();
        if (!responseModel.isStatus()) {
            mCouponCode = "";
            mCouponCodeET.setText("");
            mCouponCodeET.setEnabled(true);
            mConfirmCouponBTN.setEnabled(true);
            mCouponAppliedTV.setVisibility(View.GONE);
            mCouponDeleteTV.setVisibility(View.GONE);
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), true);
        }
    }

    @Override
    public void onApplyCouponFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onZaakpayChecksumComplete(ZaakpayChecksumResponse responseModel) {
        hideProgress();
    }

    @Override
    public void onZaakpayChecksumFailure(ApiError error) {
        hideProgress();
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
    public void onPayUHashComplete(PayuChecksumResponse responseModel) {
        hideProgress();
    }

    @Override
    public void onPayUHashFailure(ApiError error) {
        hideProgress();
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
    public void onGetGamerCashSuccess(GetGamerCashResponse response) {
        hideProgress();
        if (response.isStatus()) {
            AppSharedPreference.getInstance().setIsGamerCashLinked(response.getAlreadyLinked() || response.getLinked());
            if (response.getAlreadyLinked() || response.getLinked()) {
                coins = response.getResponse().getCoins();
                mGamerCashTV.setVisibility(View.GONE);
                mGamerCashVerifiedTV.setVisibility(View.VISIBLE);
                mGamerCashVerifiedTV.setText(getString(R.string.gamer_cash_coins) + coins + " GC");
            } else {
                mGamerCashTV.setVisibility(View.VISIBLE);
                mGamerCashVerifiedTV.setVisibility(View.GONE);
                mGamerCashTV.setText(getString(R.string.gamercash));
            }
        } else {
            Snackbar.make(mAmountET, response.getMessage(), Snackbar.LENGTH_LONG);
        }
    }

    @Override
    public void onGetGamerCashFailure(ApiError error) {
        hideProgress();
        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
    }

    private void startPaytm(String checksumHash) {
        String PARAM_MID = "MID", PARAM_OrderId = "ORDER_ID", PARAM_CustomerId = "CUST_ID", PARAM_MobileNo = "MOBILE_NO", PARAM_EmailId = "EMAIL", PARAM_ChannelId = "CHANNEL_ID", PARAM_WAP = "WAP", PARAM_Amount = "TXN_AMOUNT", PARAM_Website = "WEBSITE", PARAM_IndustryType = "INDUSTRY_TYPE_ID", PARAM_CallbackURL = "CALLBACK_URL", PARAM_ChecksumHash = "CHECKSUMHASH", PARAM_VALUE_Retail = "Retail", PARAM_VALUE_Default = "DEFAULT";
        PaytmPGService Service = PaytmPGService.getProductionService();
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put(PARAM_MID, AppUtilityMethods.getURL(AppConstant.PaytmProductionMID));
        paramMap.put(PARAM_OrderId, mOrderId);
        paramMap.put(PARAM_CustomerId, mAppPreference.getProfileData().getId());
        paramMap.put(PARAM_MobileNo, mAppPreference.getMobile());
        paramMap.put(PARAM_EmailId, mAppPreference.getEmail());
        paramMap.put(PARAM_ChannelId, PARAM_WAP);
        paramMap.put(PARAM_Amount, mAmount);
        paramMap.put(PARAM_Website, PARAM_VALUE_Default);
        paramMap.put(PARAM_IndustryType, PARAM_VALUE_Retail);
        paramMap.put(PARAM_CallbackURL, mCallbackURL);
        paramMap.put(PARAM_ChecksumHash, checksumHash);
        PaytmOrder Order = new PaytmOrder(paramMap);
        Service.initialize(Order, null);
        Service.startPaymentTransaction(this, true, true, this);
    }

    //Paytm Transaction Response
    @Override
    public void onTransactionResponse(Bundle inResponse) {
        PaymentRequest request = new PaymentRequest();
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
    }

    @Override
    public void networkNotAvailable() {
        Snackbar.make(mAmountET, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
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
        Snackbar.make(mAmountET, getString(R.string.text_transaction_cancelled_back_pressed), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
        Snackbar.make(mAmountET, getString(R.string.text_transaction_cancelled), Snackbar.LENGTH_LONG).show();
    }

    private void startCashfree(CashfreeChecksumResponse cashfreeChecksumData) {
        String mStage = AppConstant.CASHFREE_PROD;
        String token = cashfreeChecksumData.getChecksum();
        Map<String, String> params = new HashMap<>();
        params.put(PARAM_APP_ID, AppUtilityMethods.getURL(AppConstant.CASHFREE_PRODUCTION_APP_ID));
        params.put(PARAM_ORDER_ID, cashfreeChecksumData.getDetails().getOrderId());
        params.put(PARAM_ORDER_CURRENCY, cashfreeChecksumData.getDetails().getOrderCurrency());
        params.put(PARAM_ORDER_AMOUNT, mAmountET.getText().toString());
        params.put(PARAM_ORDER_NOTE, cashfreeChecksumData.getDetails().getOrderNote());
        params.put(PARAM_CUSTOMER_NAME, cashfreeChecksumData.getDetails().getCustomerName());
        params.put(PARAM_CUSTOMER_PHONE, String.valueOf(cashfreeChecksumData.getDetails().getCustomerPhone()));
        params.put(PARAM_CUSTOMER_EMAIL, cashfreeChecksumData.getDetails().getCustomerEmail());
        CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
        cfPaymentService.setOrientation(0);
        if (mPaymentFrom == AppConstant.FROM_CASHFREE) {
            cfPaymentService.doPayment(this, params, token, mStage, "#ed213a", "#FFFFFF", false);
        } else if (mPaymentFrom == AppConstant.FROM_CASHFREE_GPAY) {
            cfPaymentService.gPayPayment(this, params, token, mStage);
        } else if (mPaymentFrom == AppConstant.FROM_CASHFREE_PPAY) {
            cfPaymentService.phonePePayment(this, params, token, mStage);
        } else if (mPaymentFrom == AppConstant.FROM_CASHFREE_APAY) {
            cfPaymentService.doAmazonPayment(this, params, token, mStage);
        } else if (mPaymentFrom == AppConstant.FROM_CASHFREE_UPI) {
            cfPaymentService.upiPayment(this, params, token, mStage);
        }
    }

    private void startPayu(PayuChecksumResponse response) {
    }

    @Override
    public void onPaymentComplete(PhonePePaymentResponse response) {
        if (response.isStatus()) {
            phonePePayment(response);
        } else {
            hideProgress();
            AppUtilityMethods.showMsg(this, response.getMessage(), false);
        }
    }

    @Override
    public void onPaymentFailure(ApiError errorMsg) {
        hideProgress();
    }

    @Override
    public void onPaymentCheckComplete(PhonepeCheckPaymentResponse response) {
        if (response.isStatus()) {
            onPaymentSuccess(response.getPayment_via());
        } else {
            onPaymentFailed(2, "", response.getPayment_via());
        }
    }

    @Override
    public void onPaymentCheckFailure(ApiError errorMsg) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CFPaymentService.REQ_CODE && resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) for (String key : bundle.keySet()) {
                if (bundle.getString(key) != null) {
                    if (key.equals("txStatus") && (Objects.requireNonNull(bundle.getString(key)).equalsIgnoreCase(AppConstant.TXN_SUCCESS) || bundle.getString(key).equalsIgnoreCase(AppConstant.TXN_PENDING) || bundle.getString(key).equalsIgnoreCase(AppConstant.TXN_FAILED))) {
                        CashfreeSavePayment request = new CashfreeSavePayment();
                        request.setReferenceId(Long.parseLong(Objects.requireNonNull(bundle.getString(getString(R.string.text_refer_payment)))));
                        request.setOrderId(bundle.getString(getString(R.string.text_order_id)));
                        request.setOrderAmount(mCoins);
                        request.setPaymentMode(bundle.getString(getString(R.string.text_payment_mode)));
                        request.setTxStatus(bundle.getString("txStatus"));
                        request.setTxMsg(bundle.getString("txMsg"));
                        request.setTxTime(bundle.getString(getString(R.string.text_time_payment)));
                        request.setSignature(bundle.getString(getString(R.string.text_sign)));
                        request.setCoupon(mCouponCode);
                        showProgress(getString(R.string.txt_progress_authentication));
                        mPresenter.saveCashfreePayment(request);
                    }
                }
            }
        } else if (requestCode == B2B_PG_REQUEST_CODE) {  //phonepe
            showProgress("");
            if (resultCode == RESULT_OK) {
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(() -> {
                    hideProgress();
                    PhonepeCheckPaymentRequest phonepeCheckPaymentRequest = new PhonepeCheckPaymentRequest();
                    phonepeCheckPaymentRequest.setCoupon(mCouponCodeET.getText().toString());
                    phonepeCheckPaymentRequest.setOrderId(mPhonepeOrderId);
                    mPresenter.getPaymentCheckData(phonepeCheckPaymentRequest);
                }, 10000);
            } else {
                hideProgress();
                AppDialog.showPaymentConfirmation(this, onPaymentListener, getString(R.string.text_payment_failed), "Payment Failed!", false);
            }
        } else if (requestCode == AppConstant.PaymentDone && resultCode == RESULT_OK) {
            showProgress("");
            mPresenter.getApexPayStatus(mApexPayOrderId, mCouponCode);
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
        mCouponLL.setVisibility(View.GONE);
        mCouponAppliedTV.setVisibility(View.GONE);
        String mHeading = getString(R.string.head_payment_success);
        String mDisplayMsg = getString(R.string.text_congrats) + mCoins + getString(R.string.text_coins_added_success);
        AppDialog.showPaymentConfirmation(this, onPaymentListener, mHeading, mDisplayMsg, true);
        Map<String, Object> eventParameters5 = new HashMap<>();
        eventParameters5.put(AFInAppEventParameterName.REVENUE, mCoins); // Amount of the top-up
        eventParameters5.put(AFInAppEventParameterName.CURRENCY, AppConstant.INR);
        AppsFlyerLib.getInstance().logEvent(getApplicationContext(), "top_up_success", eventParameters5);
        //Mo Engage
        Properties properties = new Properties();
        properties.addAttribute("Transaction Date & Time", new Date())
                .addAttribute("Amount", mCoins)
                .addAttribute("currency", "INR")
                .addAttribute("pay via", payment_via)
                .addAttribute("coupon code", mCouponCode);
        MoEAnalyticsHelper.INSTANCE.trackEvent(this, "Add Wallet Success", properties);
    }

    private void onPaymentFailed(int from, String message, String payment_via) {
        mCouponLL.setVisibility(View.GONE);
        mCouponAppliedTV.setVisibility(View.GONE);
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
                .addAttribute("Amount", mCoins)
                .addAttribute("Currency", "INR")
                .addAttribute("pay via", payment_via)
                .addAttribute("coupon code", mCouponCode);
        MoEAnalyticsHelper.INSTANCE.trackEvent(this, "Add Wallet Failed", properties);
    }

    private void enableUPI() {
        mUPITV.setVisibility(View.VISIBLE);
        mGPayIV.setVisibility(View.VISIBLE);
        mAPayIV.setVisibility(View.VISIBLE);
        mPhonePayIV.setVisibility(View.VISIBLE);
    }

    private void getPaySharp(String couponCode) {
        showProgress(getString(R.string.txt_progress_authentication));
        final int random = new Random().nextInt(61) + 20;
        PaySharpRequest request = new PaySharpRequest();
        request.setAmount(Long.parseLong(mAmountET.getText().toString()));
        request.setCustomerEmail(mAppPreference.getEmail());
        request.setCustomerId(mAppPreference.getProfileData().getId());
        request.setCustomerMobileNo(mAppPreference.getMobile());
        request.setCustomerName(mAppPreference.getName());
        request.setOrderId(String.valueOf(random));
        request.setRemarks(String.valueOf(random));
        mPresenter.getPaySharp(request, couponCode);
    }

    private final IOnPaymentListener onPaymentListener = result -> {
        if (result) {
            Intent intent = new Intent();
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

    @Override
    public void onBackPressed() {
        if (mAppPreference.getIsDeepLinking()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPaySharp) {
            PaymentWaitDialog dialog = new PaymentWaitDialog(this, mOnPaymentWaitListener);
        }
//        payGamerCashVerifyAPI();
    }

    private void payGamerCashVerifyAPI() {
        if (new NetworkStatus(this).isInternetOn()) {
            mPresenter.getGamerCashUserData();
        } else {
            Snackbar.make(mAmountET, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }

    //PhonePe Payment Integration
    private void paymentIntegration() {
        if (new NetworkStatus(this).isInternetOn()) {
            PhonepeRequest phonepeRequest = new PhonepeRequest();
            if (!mAmountET.getText().toString().equals("")) {
                if (Integer.parseInt(mAmountET.getText().toString()) != 0) {
                    phonepeRequest.setAmount(Integer.parseInt(mAmountET.getText().toString()));
                    if (mPaymentFrom == AppConstant.FROM_PHONEPE_PhonePePAY || mPaymentFrom == AppConstant.FROM_PHONEPE) {
                        phonepeRequest.setTargetApp(1);
                    } else if (mPaymentFrom == AppConstant.FROM_PHONEPE_GPAY) {
                        phonepeRequest.setTargetApp(2);
                    } else {
                        phonepeRequest.setTargetApp(3);
                    }
                    phonepeRequest.setCoupon(mCouponCode);
                    phonepeRequest.setTargetApp(mPaymentFrom);
                    mPresenter.getPaymentUrlData(phonepeRequest);
                } else Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show();
        } else {
            Snackbar.make(mAmountET, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void phonePePayment(PhonePePaymentResponse response) {
        String mPaymentPackage = "";
        try {
            List<UPIApplicationInfo> upiApps = PhonePe.getUpiApps();
            mPaymentPackage = upiApps.get(0).getPackageName();
        } catch (PhonePeInitException exception) {
            exception.printStackTrace();
        }
        if (mPaymentPackage.isEmpty()) {
            Snackbar.make(mPayBTN, "No UPI option on your device.", Snackbar.LENGTH_LONG).show();
        } else {
            //prod //String string_signature = PhonePe.getPackageSignature();
            mPhonepeOrderId = response.getTransactionId();
            B2BPGRequest b2BPGRequest = new B2BPGRequestBuilder()
                    .setData(response.getBase64())
                    .setChecksum(response.getChecksum())
                    .setUrl(mApiEndPoint)
                    .build();
            //Package name should be dynamic //UAT = com.phonepe.app.preprod //PROD = com.phonepe.app
            String upiPackage = "com.phonepe.app";
            try {
                if (mPaymentFrom == AppConstant.FROM_PHONEPE_GPAY) {
                    upiPackage = "com.google.android.apps.nbu.paisa.user";
                } else if (mPaymentFrom == AppConstant.FROM_PHONEPE_PaytmPAY) {
                    upiPackage = "net.one97.paytm";
                }
                startActivityForResult(PhonePe.getImplicitIntent(
                        this, b2BPGRequest, upiPackage), B2B_PG_REQUEST_CODE);
            } catch (PhonePeInitException e) {
                Log.e("TAG", "onPaymentComplete: " + e.getMessage());
            }
        }
    }

}