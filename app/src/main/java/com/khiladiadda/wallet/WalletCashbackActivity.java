package com.khiladiadda.wallet;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.main.MainActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.Coins;
import com.khiladiadda.network.model.response.InvoiceResponse;
import com.khiladiadda.network.model.response.ProfileTransactionResponse;
import com.khiladiadda.network.model.response.VersionResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.wallet.interfaces.IWalletCashbackPresenter;
import com.khiladiadda.wallet.interfaces.IWalletCashbackView;

import butterknife.BindView;

public class WalletCashbackActivity extends BaseActivity implements IWalletCashbackView {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.tv_coins)
    TextView mTotalCoinsTV;
    @BindView(R.id.et_amount)
    EditText mAmountET;
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
    @BindView(R.id.btn_pay)
    Button mPayBTN;
    @BindView(R.id.tv_hundred)
    TextView mHundredTV;
    @BindView(R.id.tv_two_hundred)
    TextView mTwoHundredTV;
    @BindView(R.id.tv_five_hundred)
    TextView mFiveHundredTV;
    @BindView(R.id.tv_thousand)
    TextView mThousandTV;
    @BindView(R.id.tv_two_thousand)
    TextView mTwoThousandTV;
    @BindView(R.id.tv_five_thousand)
    TextView mFiveThousandTV;
    @BindView(R.id.tv_apply_coupon)
    TextView mViewCouponTV;
    @BindView(R.id.ll_offers)
    LinearLayout mOffersLL;
    @BindView(R.id.rl_bajaj_wallet)
    RelativeLayout mBajajWalletRL;
    @BindView(R.id.rl_bajaj_upi)
    RelativeLayout mBajajUpiRL;
    @BindView(R.id.img_info_bajaj_pay_upi)
    ImageView mInfoBajajPayUPIIV;
    @BindView(R.id.img_info_bajaj_pay_wallet)
    ImageView mInfoBajajPayWalletIV;
    private String mCouponCode;
    private boolean mIsOfferClicked, mIsGamerCashEnabled;
    private IWalletCashbackPresenter mPresenter;
    private long mRemainingAddLimit = 5000;
    private int mUpiPaymentType;
    private boolean mIsCashfree, mIsEasebuzz, mIsPaytm, mIsPaysharp, mIsPhonepe, mIsBajajWallet, mIsBajajUpi;

    @Override
    protected int getContentView() {
        return R.layout.activity_wallet_cashback;
    }

    @Override
    protected void initViews() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mActivityNameTV.setText(R.string.my_add_money);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mCouponCodeTV.setOnClickListener(this);
        mConfirmCouponBTN.setOnClickListener(this);
        mCouponDeleteTV.setOnClickListener(this);
        mPayBTN.setOnClickListener(this);
        mHundredTV.setOnClickListener(this);
        mTwoHundredTV.setOnClickListener(this);
        mFiveHundredTV.setOnClickListener(this);
        mThousandTV.setOnClickListener(this);
        mTwoThousandTV.setOnClickListener(this);
        mFiveThousandTV.setOnClickListener(this);
        mViewCouponTV.setOnClickListener(this);
        mBajajWalletRL.setOnClickListener(this);
        mBajajUpiRL.setOnClickListener(this);
        mInfoBajajPayWalletIV.setOnClickListener(this);
        mInfoBajajPayUPIIV.setOnClickListener(this);
        mAmountET.setOnClickListener(this);

        mAmountET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    mPayBTN.setText("Add ₹ " + charSequence.toString().trim());
                } else {
                    mPayBTN.setText("Add");
                    setAmountSelection();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mAmountET.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void initVariables() {
        mPresenter = new WalletCashbackPresenter(this);
        if (new NetworkStatus(this).isInternetOn()) {
            setData();
        } else {
            Snackbar.make(mAmountET, getString(R.string.error_internet), Snackbar.LENGTH_SHORT).show();
        }
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
                mPresenter.validateData();
                break;
            case R.id.tv_hundred:
                setAmount(100);
                break;
            case R.id.tv_two_hundred:
                setAmount(200);
                break;
            case R.id.tv_five_hundred:
                setAmount(500);
                break;
            case R.id.tv_thousand:
                setAmount(1000);
                break;
            case R.id.tv_two_thousand:
                setAmount(2000);
                break;
            case R.id.tv_five_thousand:
                setAmount(5000);
                break;
            case R.id.tv_apply_coupon:
                if (mIsOfferClicked) {
                    mIsOfferClicked = false;
                    mOffersLL.setVisibility(View.GONE);
                } else {
                    mIsOfferClicked = true;
                    mOffersLL.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rl_bajaj_wallet:
                mBajajWalletRL.setSelected(true);
                mBajajUpiRL.setSelected(false);
                break;
            case R.id.rl_bajaj_upi:
                mBajajWalletRL.setSelected(false);
                mBajajUpiRL.setSelected(true);
                break;
            case R.id.img_info_bajaj_pay_upi:
                AppDialog.bajajPayUPIDiscountOffer(this, getString(R.string.tooltip_bajajPay_UPI), "BAJAJ PAY UPI OFFER");
                break;
            case R.id.img_info_bajaj_pay_wallet:
                AppDialog.bajajPayUPIDiscountOffer(this, getString(R.string.tooltip_bajajPay_Wallet), "BAJAJ PAY WALLET OFFER");
                break;
            case R.id.et_amount:
                setAmountSelection();
                break;
        }
    }

    private void setAmount(int i) {
        setAmountSelection();
        if (i == 100) {
            mHundredTV.setSelected(true);
        } else if (i == 200) {
            mTwoHundredTV.setSelected(true);
        } else if (i == 500) {
            mFiveHundredTV.setSelected(true);
        } else if (i == 1000) {
            mThousandTV.setSelected(true);
        } else if (i == 2000) {
            mTwoThousandTV.setSelected(true);
        } else if (i == 5000) {
            mFiveThousandTV.setSelected(true);
        }
        mAmountET.setText(String.valueOf(i));
        mPayBTN.setText("Add ₹ " + i);
        mAmountET.setSelection(mAmountET.getText().length());
    }

    private void setAmountSelection() {
        mHundredTV.setSelected(false);
        mTwoHundredTV.setSelected(false);
        mFiveHundredTV.setSelected(false);
        mThousandTV.setSelected(false);
        mTwoThousandTV.setSelected(false);
        mFiveThousandTV.setSelected(false);
    }

    @Override
    public String getAmount() {
        return mAmountET.getText().toString().trim();
    }

    @Override
    public void onValidationComplete() {
        long mCoins = Long.parseLong(mAmountET.getText().toString().trim());
        if (mCoins <= mRemainingAddLimit) {
            if (mCoins > 5000 && mAppPreference.getProfileData().getAadharUpdated() != 3) {
                checkPanStatus();
            } else {
                Intent i = new Intent(this, PaymentActivity.class);
                i.putExtra(AppConstant.TXN_AMOUNT, mAmountET.getText().toString().trim());
                i.putExtra(AppConstant.COUPON, mCouponCode);
                i.putExtra(AppConstant.PAYMENT_MODE, mUpiPaymentType);
                i.putExtra(AppConstant.GAMERCASH, mIsGamerCashEnabled);
                i.putExtra(AppConstant.CASHFREE, mIsCashfree);
                i.putExtra(AppConstant.PAYTM, mIsPaytm);
                i.putExtra(AppConstant.EASEBUZZ, mIsEasebuzz);
                i.putExtra(AppConstant.PAYSHARP, mIsPaysharp);
                i.putExtra(AppConstant.PHONEPE, mIsPhonepe);
                i.putExtra(AppConstant.BAJAJWALLET, mIsBajajWallet);
                i.putExtra(AppConstant.BAJAJUPI, mIsBajajUpi);
                startActivity(i);
            }
        } else {
            AppUtilityMethods.showMsg(this, "You can only add ₹" + mRemainingAddLimit + " as per your daily limit.", false);
        }
    }

    @Override
    public void onValidationFailure(String errorMsg) {
        AppUtilityMethods.showMsg(this, errorMsg, false);
    }

    @Override
    public void onProfileComplete(ProfileTransactionResponse responseModel) {
        mAppPreference.setProfileData(responseModel.getResponse());
        hideProgress();
        setData();
    }

    @Override
    public void onProfileFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onInvoiceComplete(InvoiceResponse responseModel) {

    }

    @Override
    public void onInvoiceFailure(ApiError error) {

    }

    @Override
    public void onApplyCouponComplete(BaseResponse responseModel) {
        if (!responseModel.isStatus()) {
            mCouponCode = "";
            mCouponCodeET.setText("");
            mCouponCodeET.setEnabled(true);
            mConfirmCouponBTN.setEnabled(true);
            mCouponAppliedTV.setVisibility(View.GONE);
            mCouponDeleteTV.setVisibility(View.GONE);
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), true);
        }
        hideProgress();
    }

    @Override
    public void onApplyCouponFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onVersionSuccess(VersionResponse response) {
        if (response.isStatus()) {
            mUpiPaymentType = response.getVersion().getUpiEnable();
            mIsGamerCashEnabled = response.getVersion().isGamerCashEnabled();
            if (response.getVersion().isCashfreeEnable()) {
                mIsCashfree = true;
            }
            if (response.getVersion().isPaytmEnable()) {
                mIsPaytm = true;
            }
            if (response.getVersion().isRazorpayEnable()) {
                mIsPaysharp = true;
            }
            if (response.getVersion().isEasebuzzEnable()) {
                mIsEasebuzz = true;
            }
            if (response.getVersion().isPhonepeEnabled()) {
                mIsPhonepe = true;
            }
            if (response.getVersion().isBajajWallet()) {
                mIsBajajWallet = true;
            }
            if (response.getVersion().isBajajUPI()) {
                mIsBajajUpi = true;
            }
        }
        hideProgress();
    }

    @Override
    public void onVersionFailure(ApiError error) {
        hideProgress();
    }

    private void setData() {
        showProgress(getString(R.string.txt_progress_authentication));
        Coins coins = mAppPreference.getProfileData().getCoins();
        if (coins != null) {
            double total = coins.getDeposit() + coins.getBonus() + coins.getWinning();
            if (String.valueOf(total).contains(".")) {
                mTotalCoinsTV.setText("Total Balance : " + "₹" + String.format("%.2f", total));
            } else {
                mTotalCoinsTV.setText("Total Balance : " + "₹" + total);
            }
            SpannableString totalCoin = new SpannableString(mTotalCoinsTV.getText().toString());
            mTotalCoinsTV.setText(totalCoin);
            mPresenter.getVersionDetails();
        } else {
            mPresenter.getProfile();
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
    public void onBackPressed() {
        if (mAppPreference.getIsDeepLinking()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        if (mAppPreference.getBoolean(AppConstant.FROM_WALLET, false)) {
            mAppPreference.setBoolean(AppConstant.FROM_WALLET, false);
            finish();
        }
        super.onResume();
    }

}