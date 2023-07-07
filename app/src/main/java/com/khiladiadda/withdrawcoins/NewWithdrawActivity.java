package com.khiladiadda.withdrawcoins;
import static android.view.View.GONE;
import static com.khiladiadda.utility.AppConstant.PATYM;
import static com.khiladiadda.utility.AppConstant.PATYMUPI;
import static com.khiladiadda.utility.AppConstant.PATYMWALLTERUPI;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.dialogs.interfaces.IOnWithdrawOtpListener;
import com.khiladiadda.dialogs.interfaces.IOnWithdrawVerifyListener;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.interfaces.IOnItemClickListener;
import com.khiladiadda.main.MainActivity;
import com.khiladiadda.main.adapter.BannerPagerAdapter;
import com.khiladiadda.main.fragment.BannerFragment;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.AddBeneficieryRazorpay;
import com.khiladiadda.network.model.request.RaceConditionPayoutRequest;
import com.khiladiadda.network.model.response.AddBeneficiaryResponse;
import com.khiladiadda.network.model.response.BannerDetails;
import com.khiladiadda.network.model.response.BeneficiaryDetails;
import com.khiladiadda.network.model.response.BeneficiaryResponse;
import com.khiladiadda.network.model.response.BeneficiaryVerifyResponse;
import com.khiladiadda.network.model.response.ManualWithdrawResponse;
import com.khiladiadda.network.model.response.OtpResponse;
import com.khiladiadda.network.model.response.PayoutResponse;
import com.khiladiadda.network.model.response.ProfileDetails;
import com.khiladiadda.network.model.response.TdsResponse;
import com.khiladiadda.network.model.response.WIthdrawLimitResponse;
import com.khiladiadda.network.model.response.WithdrawComissionDetails;
import com.khiladiadda.network.model.response.WithdrawLimitDetails;
import com.khiladiadda.profile.update.AadharActivity;
import com.khiladiadda.terms.TermsActivity;
import com.khiladiadda.transaction.TransactionActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.LocationCheckUtils;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.withdrawcoins.adapter.NewBeneficiaryAdapter;
import com.khiladiadda.withdrawcoins.interfaces.IWithdrawPresenter;
import com.khiladiadda.withdrawcoins.interfaces.IWithdrawView;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;
import com.moengage.inapp.MoEInAppHelper;
import com.moengage.widgets.NudgeView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
public class NewWithdrawActivity extends BaseActivity implements IWithdrawView, IOnItemClickListener, NewBeneficiaryAdapter.IOnItemChildClickListener,
        LocationCheckUtils.IOnAdressPassed {
    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.tv_wining_cash)
    TextView mWinningCashTV;
    @BindView(R.id.rl_paytm)
    RelativeLayout mPaytmRV;
    @BindView(R.id.rl_upi)
    RelativeLayout mUpiRL;
    @BindView(R.id.rl_bank)
    RelativeLayout mBankTransferRL;
    @BindView(R.id.et_payment_address)
    EditText mPaymentAddressET;
    @BindView(R.id.ll_bank)
    LinearLayout mBankLL;
    @BindView(R.id.et_account_number)
    EditText mBankAccountNumberET;
    @BindView(R.id.et_ifsc_code)
    EditText mIFSCCodeET;
    @BindView(R.id.et_bank_name)
    EditText mBankNameEt;
    @BindView(R.id.et_address)
    EditText mAddressET;
    @BindView(R.id.et_amount)
    EditText mAmountET;
    @BindView(R.id.tv_total_coins)
    TextView mTotalCoinsTV;
    @BindView(R.id.btn_add_beneficiary)
    Button mAddBeneficiaryBTN;
    @BindView(R.id.btn_submit)
    Button mSubmitBTN;
    @BindView(R.id.rv_withdraw)
    RecyclerView mWithdrawRV;
    @BindView(R.id.ll_link_details)
    LinearLayout mLinkDetailsLL;
    @BindView(R.id.ll_amount_details)
    LinearLayout mAmountDetailsLL;
    @BindView(R.id.et_upa_name)
    EditText mUpiNameET;
    @BindView(R.id.et_name)
    EditText mBankAccountNameET;
    @BindView(R.id.tv_policies_update)
    TextView mPoliciesUpdateTV;
    @BindView(R.id.cv_paytmtick)
    CardView mPaytmTickCV;
    @BindView(R.id.cv_upitick)
    CardView mUPITickCV;
    @BindView(R.id.cv_banktick)
    CardView mBankTickCV;
    @BindView(R.id.rl_paytm_wallet)
    RelativeLayout mPaytmWalletRV;
    @BindView(R.id.nudge)
    NudgeView mNV;
    private IWithdrawPresenter mPresenter;
    private NewBeneficiaryAdapter mAdapter;
    private ArrayList<BeneficiaryDetails> mList = null;
    private String mName, mBank, mIfsc, mPayoutGatewayName, mPaymentMode = AppConstant.UPI, mBeneficiaryId, mFundAccountId, mBID;
    private double mWinningCoins, mTDSAmount;
    private int mAmount, mPayoutGateway, selectedPos = RecyclerView.NO_POSITION, mPayoutSelect = 0;
    private List<WithdrawComissionDetails> mWithdrawCommission = null;
    private boolean mKYCVerified,mIsDataRefresh,mIsWithdrawVerified;
    @BindView(R.id.tv_payment_history)
    TextView mPaymentHistoryTV;
    @BindView(R.id.vp_advertisement)
    ViewPager mBannerVP;
    private List<BannerDetails> mAdvertisementsList = new ArrayList<>();
    private Handler mHandler;
    @BindView(R.id.rl_image)
    RelativeLayout mBannerRL;

    @Override
    protected int getContentView() {
        return R.layout.activity_new_withdraw;
    }
    @Override
    protected void onStart(){
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
        LocationCheckUtils.initialize(this, this, this);
        mActivityNameTV.setText(R.string.my_withdraw_wallet);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mPaytmRV.setOnClickListener(this);
        mUpiRL.setOnClickListener(this);
        mBankTransferRL.setOnClickListener(this);
        mAddBeneficiaryBTN.setOnClickListener(this);
        mPoliciesUpdateTV.setOnClickListener(this);
        mSubmitBTN.setOnClickListener(this);
        mPaytmWalletRV.setOnClickListener(this);
        mPaymentHistoryTV.setOnClickListener(this);
        mPaymentHistoryTV.setVisibility(View.VISIBLE);
        mPaymentHistoryTV.setText(R.string.withdraw_history);
        if (mAppPreference.getBoolean(AppConstant.IS_PAYTMWALLET_ENABLED, false)) {
            mPoliciesUpdateTV.setVisibility(View.GONE);
            mPaytmWalletRV.setVisibility(View.VISIBLE);
            mPaytmRV.setVisibility(View.GONE);
        } else {
            mPaytmWalletRV.setVisibility(View.GONE);
            mPoliciesUpdateTV.setVisibility(View.VISIBLE);
            mPaytmRV.setVisibility(View.VISIBLE);
            SpannableString string = new SpannableString(mPoliciesUpdateTV.getText().toString());
            string.setSpan(new UnderlineSpan(), 0, string.length(), 0);
            mPoliciesUpdateTV.setText(string);
        }
        if (LocationCheckUtils.getInstance().hasLocationPermission()) {
            LocationCheckUtils.getInstance().statusCheck();
            LocationCheckUtils.getInstance().requestNewLocationData();
        }
    }

    @Override
    protected void initVariables() {
        mPresenter = new WithdrawPresenter(this);
        mList = new ArrayList<>();
        mAdapter = new NewBeneficiaryAdapter(this, mList);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mWithdrawRV.setLayoutManager(manager);
        mWithdrawRV.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        setWalletBenefeciaryData();
        mAmountET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    mAmount = Integer.parseInt(mAmountET.getText().toString().trim());
                    mTotalCoinsTV.setText(getString(R.string.text_total_coins_space) + mAmount);
                } else {
                    mTotalCoinsTV.setText(getString(R.string.text_zero));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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
            case R.id.rl_paytm:
                setData(AppConstant.PATYMUPI);
                break;
            case R.id.rl_paytm_wallet:
                setData(AppConstant.PATYM);
                break;
            case R.id.rl_upi:
                setData(AppConstant.UPI);
                break;
            case R.id.tv_payment_history:
                setData(AppConstant.FROM_WITHDRAW);
                break;
            case R.id.rl_bank:
                setData(AppConstant.BANK_TRANSFER);
                break;
            case R.id.btn_add_beneficiary:
                if ((mPayoutSelect == 1) && (TextUtils.isEmpty(mPaymentAddressET.getText().toString().trim()))) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_paytm_mobilenumber_required), false);
                } else if ((mPayoutSelect == 1) && (mPaymentAddressET.getText().toString().trim().length() < 10)) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_paytm_mobilenumber_length), false);
                } else if ((mPayoutSelect == 1 || mPayoutSelect == 2 || mPayoutSelect == 3) && (mUpiNameET.getText().toString().trim().isEmpty())) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_beneficiary_name_required), false);
                } else if (TextUtils.isEmpty(mPaymentAddressET.getText().toString().trim()) && ((mPayoutSelect == 2) || (mPayoutSelect == 3))) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_upi_address_required), false);
                } else if ((mPayoutSelect == 4) && (TextUtils.isEmpty(mBankAccountNumberET.getText().toString().trim()) || TextUtils.isEmpty(mIFSCCodeET.getText().toString().trim()) || TextUtils.isEmpty(mAddressET.getText().toString().trim()))) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_bank_address), false);
                } else if ((mPayoutSelect == 4) && mBankAccountNumberET.getText().toString().length() < 9) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_bank_incorrect), false);
                } else {
                    showProgress(getString(R.string.txt_progress_authentication));
                    if (mPayoutGateway == 1 && mPayoutSelect == 1) {   //cashfree
                        mPresenter.onCashfreeAddBeneficiary(mBankAccountNumberET.getText().toString().trim(), mIFSCCodeET.getText().toString().trim().toUpperCase(), mAddressET.getText().toString().trim(), mPaymentMode, mPaymentAddressET.getText().toString() + PATYMWALLTERUPI, mUpiNameET.getText().toString().trim(), mPayoutSelect);
                    } else if (mPayoutGateway == 1) {
                        mPresenter.onCashfreeAddBeneficiary(mBankAccountNumberET.getText().toString().trim(), mIFSCCodeET.getText().toString().trim().toUpperCase(), mAddressET.getText().toString().trim(), mPaymentMode, mPaymentAddressET.getText().toString().trim(), mUpiNameET.getText().toString().trim(), mPayoutSelect);
                    } else if (mPayoutGateway == 3 && mPayoutSelect == 1) { //apexpay
                        mPresenter.onAddBeneficiaryEasebuzz(mBankAccountNumberET.getText().toString().trim(), mIFSCCodeET.getText().toString().trim().toUpperCase(), mPaymentMode, mPaymentAddressET.getText().toString().trim() + PATYMWALLTERUPI, mPayoutSelect, mPayoutGateway + 3);
                    } else if (mPayoutGateway == 3) {
                        mPresenter.onAddBeneficiaryEasebuzz(mBankAccountNumberET.getText().toString().trim(), mIFSCCodeET.getText().toString().trim().toUpperCase(), mPaymentMode, mPaymentAddressET.getText().toString().trim(), mPayoutSelect, mPayoutGateway + 3);
                    } else if (mPayoutGateway == 4 && mPayoutSelect == 1) { //paysharp
                        mPresenter.onAddBeneficiaryEasebuzz(mBankAccountNumberET.getText().toString().trim(), mIFSCCodeET.getText().toString().trim().toUpperCase(), mPaymentMode, mPaymentAddressET.getText().toString().trim() + PATYMWALLTERUPI, mPayoutSelect, mPayoutGateway + 2);
                    } else if (mPayoutGateway == 4) {
                        mPresenter.onAddBeneficiaryEasebuzz(mBankAccountNumberET.getText().toString().trim(), mIFSCCodeET.getText().toString().trim().toUpperCase(), mPaymentMode, mPaymentAddressET.getText().toString().trim(), mPayoutSelect, mPayoutGateway + 2);
                    } else if (mPayoutGateway == 5 || mPayoutGateway == 6) { //5- Easebuzz, 6-Neokred
                        mPresenter.onAddBeneficiaryEasebuzz(mBankAccountNumberET.getText().toString().trim(), mIFSCCodeET.getText().toString().trim().toUpperCase(), mPaymentMode, mPaymentAddressET.getText().toString().trim(), mPayoutSelect, mPayoutGateway);
                    } else if (mPayoutGateway == 7 && mPayoutSelect == 1) {  //Race-condition
                        mPresenter.onRaceConditionTransfer(new RaceConditionPayoutRequest(mBankAccountNumberET.getText().toString().trim(), mIFSCCodeET.getText().toString().trim().toUpperCase(), mPaymentAddressET.getText().toString() + PATYMWALLTERUPI, mAddressET.getText().toString().trim(), mPaymentMode, mUpiNameET.getText().toString().trim(), mPayoutSelect));
                    } else if (mPayoutGateway == 7) {
                        mPresenter.onRaceConditionTransfer(new RaceConditionPayoutRequest(mBankAccountNumberET.getText().toString().trim(), mIFSCCodeET.getText().toString().trim().toUpperCase(), mPaymentAddressET.getText().toString().trim(), mAddressET.getText().toString().trim(), mPaymentMode, mUpiNameET.getText().toString().trim(), mPayoutSelect));
                    } else if (mPayoutGateway == 8 && mPayoutSelect == 1) { //instantpay
                        mPresenter.onAddBeneficiaryEasebuzz(mBankAccountNumberET.getText().toString().trim(), mIFSCCodeET.getText().toString().trim().toUpperCase(), mPaymentMode, mPaymentAddressET.getText().toString().trim() + PATYMWALLTERUPI, mPayoutSelect, mPayoutGateway);
                    } else if (mPayoutGateway == 8) {
                        mPresenter.onAddBeneficiaryEasebuzz(mBankAccountNumberET.getText().toString().trim(), mIFSCCodeET.getText().toString().trim().toUpperCase(), mPaymentMode, mPaymentAddressET.getText().toString().trim(), mPayoutSelect, mPayoutGateway);
                    } else {  //razorpay
                        AddBeneficieryRazorpay addBeneficieryRazorpay = new AddBeneficieryRazorpay();
                        addBeneficieryRazorpay.setAccountNumber(mBankAccountNumberET.getText().toString().trim());
                        addBeneficieryRazorpay.setAccountType(mPaymentMode);
                        addBeneficieryRazorpay.setAddress(mPaymentAddressET.getText().toString().trim());
                        addBeneficieryRazorpay.setIfsc(mIFSCCodeET.getText().toString().trim().toUpperCase());
                        addBeneficieryRazorpay.setIfsc(mIFSCCodeET.getText().toString().trim().toUpperCase());
                        addBeneficieryRazorpay.setName(mAppPreference.getName());
                        addBeneficieryRazorpay.setTransferType(mPayoutSelect);
                        mPresenter.onRazorpayAddBeneficiary(addBeneficieryRazorpay);
                    }
                }
                break;
            case R.id.btn_submit:
                mPresenter.validateData();
                break;
            case R.id.tv_policies_update:
                AppDialog.showPaytmUpi(this);
                break;
        }
    }

    private void setData(String from) {
        mLinkDetailsLL.setVisibility(View.VISIBLE);
        setPaymentSelected();
        mPaymentAddressET.setVisibility(View.VISIBLE);
        mBankLL.setVisibility(View.GONE);
        mPaymentAddressET.setText("");
        mUpiNameET.setText("");
        if (mPayoutGateway == 6) {
            mPaymentMode = AppConstant.BANK_TRANSFER;
        } else {
            if (from.equalsIgnoreCase(PATYMUPI) || from.equalsIgnoreCase(PATYM)) {
                mPaymentMode = AppConstant.UPI;
            } else if (from.equalsIgnoreCase(AppConstant.BANK_TRANSFER)) {
                mPaymentMode = AppConstant.BANK_TRANSFER;
            } else {
                mPaymentMode = from;
            }
        }
        if (mList.size() > 0) {
            mAdapter.setSelectedPos(selectedPos);
            mAdapter.notifyDataSetChanged();
            mAmountET.setText("");
            mAmountDetailsLL.setVisibility(View.GONE);
        }
        switch (from) {
            case AppConstant.PATYMUPI:
                mPaytmRV.setSelected(true);
                if (!mAppPreference.getIsPaytmUpi()) {
                    AppDialog.showPaytmUpi(this);
                    mAppPreference.setIsPaytmUpi(true);
                }
                mUpiNameET.setVisibility(View.VISIBLE);
                mUpiNameET.setHint(getString(R.string.text_paytm_name));
                mPaytmTickCV.setVisibility(View.VISIBLE);
                mPaymentAddressET.setHint(getString(R.string.paytm_upi));
                mPayoutSelect = 2;
                break;
            case AppConstant.UPI:
                mUpiRL.setSelected(true);
                mUPITickCV.setVisibility(View.VISIBLE);
                mUpiNameET.setVisibility(View.VISIBLE);
                mUpiNameET.setHint(getString(R.string.text_account_holder_name));
                mPaymentAddressET.setHint(getString(R.string.text_upi_address));
                mPayoutSelect = 3;
                mPaymentAddressET.setInputType(InputType.TYPE_CLASS_TEXT);
                InputFilter[] filters1 = new InputFilter[1];
                filters1[0] = new InputFilter.LengthFilter(50);
                mPaymentAddressET.setFilters(filters1);
                break;
            case AppConstant.BANK_TRANSFER:
                mBankTransferRL.setSelected(true);
                mBankTickCV.setVisibility(View.VISIBLE);
                mPaymentAddressET.setVisibility(View.GONE);
                mUpiNameET.setVisibility(View.GONE);
                mBankLL.setVisibility(View.VISIBLE);
                mPayoutSelect = 4;
                break;
            case AppConstant.FROM_WITHDRAW:
                mLinkDetailsLL.setVisibility(View.GONE);
                Intent i = new Intent(this, TransactionActivity.class);
                i.putExtra(AppConstant.FROM, AppConstant.FROM_WITHDRAW);
                startActivity(i);
                break;
            case AppConstant.PATYM:
                mPaytmWalletRV.setSelected(true);
                mPayoutSelect = 1;
                mPaytmTickCV.setVisibility(View.VISIBLE);
                mUpiNameET.setVisibility(View.VISIBLE);
                mUpiNameET.setHint(getString(R.string.text_paytm_name));
                mPaymentAddressET.setHint(getString(R.string.text_paytm_number));
                mPaymentAddressET.setInputType(InputType.TYPE_CLASS_NUMBER);
                InputFilter[] filters = new InputFilter[1];
                filters[0] = new InputFilter.LengthFilter(10);
                mPaymentAddressET.setFilters(filters);
                break;
        }
    }

    private void setPaymentSelected() {
        mPaytmRV.setSelected(false);
        mUpiRL.setSelected(false);
        mBankTransferRL.setSelected(false);
        mPaytmWalletRV.setSelected(false);
        mUPITickCV.setVisibility(View.GONE);
        mPaytmTickCV.setVisibility(View.GONE);
        mBankTickCV.setVisibility(View.GONE);
    }

    @Override
    public String getAmount() {
        return mAmountET.getText().toString().trim();
    }

    @Override
    public void onValidationComplete() {
        if (mAmount < 20) {
            AppUtilityMethods.showMsg(this, getString(R.string.text_amount_less_ten), false);
        } else {
            showProgress(getString(R.string.txt_progress_authentication));
            mSubmitBTN.setEnabled(false);
            mPresenter.checkTDS(mAmount);
        }
    }

    @Override
    public void onValidationFailure(String errorMsg) {
        mSubmitBTN.setEnabled(true);
        AppUtilityMethods.showMsg(this, errorMsg, false);
    }

    @Override
    public void onGetBeneficiaryListComplete(BeneficiaryResponse responseModel) {  //cashfree = 1, razorpay = 2
        mList.clear();
        mAppPreference.setProfileData(responseModel.getProfileDetails());
        mPayoutGateway = responseModel.getPayoutEnable();
//        mParallelPayoutGateway = responseModel.getParallelPayout();
        mWithdrawCommission = responseModel.getWithdrawCommission();
        mIsWithdrawVerified = responseModel.isWithdrawVerified();
        if (responseModel.isManualWithdraw()) {
            showWithdrawConfirmation(getString(R.string.text_manual_withdraw), responseModel.getMessage(), false, responseModel.isManualWithdraw());
        } else {
            if (responseModel.isStatus()) {
                if (responseModel.getResponse().size() > 0) {
                    mList.addAll(responseModel.getResponse());
                    mWithdrawRV.setVisibility(View.VISIBLE);
                } else {
                    Snackbar.make(mSubmitBTN, getString(R.string.text_link_details_to_new_withdraw), Snackbar.LENGTH_LONG).show();
                }
            }
        }
        mAdapter.notifyDataSetChanged();
        if (mPayoutGateway == 3) {  //|| mPayoutGateway == 4
            mUpiRL.setVisibility(View.GONE);
            mPaytmRV.setVisibility(View.GONE);
            mPaytmWalletRV.setVisibility(View.GONE);
            mPoliciesUpdateTV.setVisibility(View.GONE);
            mLinkDetailsLL.setVisibility(View.GONE);
        } else if (mPayoutGateway == 6) {
            mUpiRL.setVisibility(View.GONE);
            mPaytmRV.setVisibility(View.GONE);
            mPaytmWalletRV.setVisibility(View.GONE);
        }
        List<BannerDetails> bannerData = responseModel.getBanner();
        if (bannerData != null && bannerData.size() > 0) {
            mBannerRL.setVisibility(View.VISIBLE);
            setUpAdvertisementViewPager(bannerData);
        } else {
            mBannerRL.setVisibility(GONE);
        }
        hideProgress();
    }

    @Override
    public void onGetBeneficiaryListFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onAddCashfreeBeneficiaryComplete(AddBeneficiaryResponse response) {
        showAddBeneficiaryStatus(response);
    }

    private void showAddBeneficiaryStatus(AddBeneficiaryResponse response) {
        hideProgress();
        mLinkDetailsLL.setVisibility(View.GONE);
        if (response.isStatus()) {
            showMsg(this, response.getMessage(), false, 1);
        } else if (response.isManual_withdraw()) {
            showWithdrawConfirmation(getString(R.string.text_msg_manual_wiithdraw), response.getMessage(), false, true);
        } else {
            showMsg(this, response.getMessage(), false, 1);
        }
    }

    @Override
    public void onAddCashfreeBeneficiaryFailed(ApiError error) {
        hideProgress();
        Snackbar.make(mSubmitBTN, getString(R.string.text_valid_withdraw_details), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onCashfreeTransferComplete(PayoutResponse response) {
        onAllTransferDone(response);
    }

    private void onAllTransferDone(PayoutResponse response) {
        hideProgress();
        if (response.isStatus()) {
            showWithdrawConfirmation(getString(R.string.text_transfer_success), response.getMessage(), true, false);
        } else {
            if (response.isManual_withdraw()) {
                showWithdrawConfirmation("", response.getMessage(), false, true);
            } else {
                showWithdrawConfirmation(getString(R.string.text_wiithdraw_failed), response.getMessage(), false, false);
            }
        }
    }

    @Override
    public void onCashfreeTransferFailed(ApiError error) {
        hideProgress();
    }

    @Override
    public void onDeleteComplete(BaseResponse response) {
        hideProgress();
        if (response.isStatus()) {
            showMsg(this, getString(R.string.text_withdraw_details_deleted), false, 1);
        } else {
            Snackbar.make(mSubmitBTN, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDeleteFailed(ApiError error) {
        hideProgress();
    }

    @Override
    public void onManualWithdrawComplete(BaseResponse responseModel) {

    }

    @Override
    public void onManualWithdrawFailure(ApiError error) {

    }

    @Override
    public void onGetManualWithdrawComplete(ManualWithdrawResponse responseModel) {

    }
    @Override
    public void onGetManualWithdrawFailure(ApiError error){

    }
    @Override
    public void onResendOtpComplete(OtpResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            Snackbar.make(mSubmitBTN, R.string.text_otp_send_successfully, Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(mSubmitBTN, responseModel.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResendOtpFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onSendOtpComplete(BaseResponse responseModel) {
        hideProgress();
        new WithdrawOTPDialog(this, onWithdrawOtpListener);
    }

    @Override
    public void onSendOtpFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onAddRazorpayBeneficiaryComplete(AddBeneficiaryResponse response) {
        showAddBeneficiaryStatus(response);
    }

    @Override
    public void onAddRazorpayBeneficiaryFailed(ApiError error) {
        hideProgress();
        Snackbar.make(mSubmitBTN, getString(R.string.text_valid_withdraw_details), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onRazorpayTransferComplete(PayoutResponse response) {
        onAllTransferDone(response);
    }

    @Override
    public void onRazorpayTransferFailed(ApiError error) {
        hideProgress();
    }
    @Override
    public void onWithdrawLimitComplete(@NonNull WIthdrawLimitResponse response) {
        if (response.isStatus()) {
            WithdrawLimitDetails res = response.getResponse();
            if (res.isKycEnabled()){
                //addahr not verified then check limit amount
                //aadhar verified and withdraw limit
                if (!res.isAadharVerified() && (mAmount > res.getLimitAmount())){
                    hideProgress();
                    new WithdrawVerificationDialog(NewWithdrawActivity.this, response.getMessage(), mName, mBank, mIfsc, mBID);
                } else if (!res.isAadharVerified() && ((mAmount + res.getAmountData()) > res.getLimitAmount())) {
                    hideProgress();
                    new WithdrawVerificationDialog(NewWithdrawActivity.this, response.getMessage(), mName, mBank, mIfsc, mBID);
                } else if (mIsWithdrawVerified && res.isAadharVerified() && (mAmount > res.getnWithdrawalLimit())) {
                    mPresenter.verifyBeneficiary(mBID);
                } else {
                    hideProgress();
                    showConfirmWithdrawDialog();
                }
            } else {
                hideProgress();
                showConfirmWithdrawDialog();
            }
        } else {
            hideProgress();
            showConfirmWithdrawDialog();
        }
        mSubmitBTN.setEnabled(true);
    }

    @Override
    public void onWithdrawLimitFailed(ApiError error) {
        hideProgress();
    }
    @Override
    public void onVerifyBeneficiaryComplete(BeneficiaryVerifyResponse responseModel){
         hideProgress();
        if(responseModel.isStatus()){
            mKYCVerified = true;
            AppUtilityMethods.showMsg(this, "!!!Thank You!!!\nYour bank details and aadhar card details has matched. Now you can withdraw amount easily.", false);
            getBeneficiaryList();
        } else {
            new WithdrawReVerifyDialog(this, onWithdrawListener, 1, mBID, mAppPreference.getProfileData().getName(), responseModel.getResponse());
        }
    }

    @Override
    public void onVerifyBeneficiaryFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onApexPayTransferComplete(PayoutResponse response) {
        onAllTransferDone(response);
    }

    @Override
    public void onApexPayTransferFailed(ApiError error) {
        hideProgress();
    }

    @Override
    public void onPaySharpTransferComplete(PayoutResponse response) {
        onAllTransferDone(response);
    }

    @Override
    public void onPaySharpTransferFailed(ApiError error) {
        hideProgress();
    }

    @Override
    public void onAddBeneficiaryComplete(AddBeneficiaryResponse response) {
        showAddBeneficiaryStatus(response);
    }

    @Override
    public void onAddBeneficiaryFailed(ApiError error) {
        hideProgress();
        Snackbar.make(mSubmitBTN, getString(R.string.text_valid_withdraw_details), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onRaceConditionComplete(PayoutResponse response) {
        hideProgress();
        mLinkDetailsLL.setVisibility(View.GONE);
        if (response.isStatus()) {
            showMsg(this, response.getMessage(), false, 1);
        } else if (response.isManual_withdraw()) {
            showWithdrawConfirmation(getString(R.string.text_msg_manual_wiithdraw), response.getMessage(), false, true);
        } else {
            showMsg(this, response.getMessage(), false, 1);
        }
    }

    @Override
    public void onRaceConditionFailed(ApiError error) {
        Toast.makeText(this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onIPayComplete(PayoutResponse response) {
        onAllTransferDone(response);
    }

    @Override
    public void onIPayFailed(ApiError error) {
        hideProgress();
    }

    @Override
    public void onCheckTDSComplete(TdsResponse response) {
        mTDSAmount=response.getTdsAmount();
        if ((mAmount) <= mWinningCoins) {
            if (mKYCVerified) {
                hideProgress();
                showConfirmWithdrawDialog();
            } else {
                mPresenter.getWithdrawLimit();
            }
        } else {
            mSubmitBTN.setEnabled(true);
            hideProgress();
            AppUtilityMethods.showMsg(this, getString(R.string.text_not_enough_coins_wallet), false);
        }
    }

    @Override
    public void onCheckTDSFailed(ApiError error) {
        mSubmitBTN.setEnabled(true);
        hideProgress();
    }

    public void showMsg(final Context activity, String msg, boolean isCancel, int from) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setContentView(R.layout.dialog_delete);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        Button mNoBTN = dialog.findViewById(R.id.btn_no);
        if (from == 3) {
            okBTN.setText(getString(R.string.verify));
            mNoBTN.setText(getString(R.string.cancel));
        }
        if (from == 1) {
            clearBeneficiaryData();
            okBTN.setText(getString(R.string.ok));
            mNoBTN.setVisibility(View.GONE);
        }
        okBTN.setOnClickListener(view -> {
            dialog.dismiss();
            if (from == 1) {
                getBeneficiaryList();
            } else if (from == 2) {
                showProgress(getString(R.string.txt_progress_authentication));
                mPresenter.deleteBeneficiary(mBeneficiaryId);
            } else if ((from == 3) || (from == 4)) {
                Intent i = new Intent(this, AadharActivity.class);
                i.putExtra(AppConstant.FROM, AppConstant.FROM_AADHAR);
                startActivity(i);
            }
        });
        mNoBTN.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    private void clearBeneficiaryData() {
        mPaymentAddressET.setText("");
        mAddressET.setText("");
        mIFSCCodeET.setText("");
        mBankAccountNameET.setText("");
        mBankAccountNumberET.setText("");
        mUpiNameET.setText("");
    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        //not verified
        mKYCVerified = mList.get(position).isExist();
        if (position >= 0) {
            mLinkDetailsLL.setVisibility(View.GONE);
            setPaymentSelected();
            mPaymentMode = mList.get(position).getTransferMode().toLowerCase();
            if (mPayoutGateway == 1) {  //cashfree
                mBeneficiaryId = mList.get(position).getBeneId();
            } else {  //razorpay
                mFundAccountId = mList.get(position).getFundAccountId();
                if (!TextUtils.isEmpty(mList.get(position).getBeneId())) {
                    mBeneficiaryId = mList.get(position).getBeneId();
                }
                if(mPaymentMode.equalsIgnoreCase(AppConstant.VPA)){
                    mPaymentMode=AppConstant.UPI;
                }
            }
            mAmountDetailsLL.setVisibility(View.VISIBLE);
            mAdapter.setSelectedPos(position);
            mAdapter.notifyDataSetChanged();
            mName = mList.get(position).getName();
            mBID = mList.get(position).getId();
            if (mList.get(position).getTransferType() == 4) {
                mBank = mList.get(position).getBankAccount();
                mIfsc = mList.get(position).getIfsc();
            } else {
                mBank = mList.get(position).getTransferMode();
                mIfsc = mList.get(position).getVPA();
            }
        }
    }

    @Override
    public void onDeleteClicked(int position) {
        if (position >= 0) {
            mBeneficiaryId = mList.get(position).getBeneId();
            mLinkDetailsLL.setVisibility(View.GONE);
            setPaymentSelected();
            mAmountET.setText("");
            mAmountDetailsLL.setVisibility(View.GONE);
            showMsg(this, getString(R.string.text_delete_withdraw_acoount), false, 2);
        }
    }

    public void showWithdrawConfirmation(String heading, String msg, boolean result, boolean manualWithdraw) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_payment_confirmation);
        final TextView mHeadingTV = dialog.findViewById(R.id.tv_heading);
        mHeadingTV.setText(heading);
        final TextView mMsgTV = dialog.findViewById(R.id.tv_msg);
        mMsgTV.setText(msg);
        final ImageView mIV = dialog.findViewById(R.id.iv_payment);
        Button mSendBTN = dialog.findViewById(R.id.btn_ok);
        if (manualWithdraw) {
            mIV.setVisibility(View.GONE);
            mSendBTN.setText(R.string.text_go_manual_withdraw);
        } else if (result) {
            mHeadingTV.setTextColor(ContextCompat.getColor(this, R.color.color_green));
            mIV.setBackground(AppCompatResources.getDrawable(this, R.drawable.payment_success));
            Properties properties = new Properties();
            properties.addAttribute("Transaction Date & Time", new Date())
                    .addAttribute("Amount", mAmount)
                    .addAttribute("currency", "INR")
                    .addAttribute("paymentgateway", mPayoutGatewayName)
                    .setNonInteractive();
            MoEAnalyticsHelper.INSTANCE.trackEvent(this, "Withdraw Success", properties);
        } else {
            mHeadingTV.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            mIV.setBackground(AppCompatResources.getDrawable(this, R.drawable.payment_failure));
            Properties properties = new Properties();
            properties.addAttribute("Transaction Date & Time", new Date())
                    .addAttribute("Amount", mAmount)
                    .addAttribute("currency", "INR")
                    .addAttribute("payment gateway", mPayoutGateway)
                    .setNonInteractive();
            MoEAnalyticsHelper.INSTANCE.trackEvent(this, "Withdraw Failure", properties);
        }
        mSendBTN.setOnClickListener(v -> {
            dialog.dismiss();
            if (manualWithdraw) {
                Intent i = new Intent(this, ManualWithdrawActivity.class);
                i.putExtra(AppConstant.FROM, AppConstant.FROM_WITHDRAW);
                startActivity(i);
            } else {
                Intent intent = new Intent();
                setResult(AppConstant.REQUEST_ADD_WALLET, intent);
                finish();
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void showConfirmWithdrawDialog() {
        mIsDataRefresh=true;
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_withdraw_confirm);
        TextView walletCoins = dialog.findViewById(R.id.tv_wallet);
        walletCoins.setText(String.valueOf(Double.parseDouble(new DecimalFormat("##.##").format(mWinningCoins))));
        TextView withdrawCoins = dialog.findViewById(R.id.tv_withdraw);
        withdrawCoins.setText(String.valueOf(mAmount));
        TextView transactionFeeTV = dialog.findViewById(R.id.tv_transaction_fee);
        TextView tdsTV = dialog.findViewById(R.id.tv_tds_fee);
        TextView tdsInfoTV = dialog.findViewById(R.id.tv_tds_info);
        tdsTV.setText(String.valueOf(mTDSAmount));
        double transactionFees;
        long comission = 0;
        //list loop - from to //amount - from-to = comission
        for (int i = 0; i < mWithdrawCommission.size(); i++) {
            long from, to;
            from = mWithdrawCommission.get(i).getFrom();
            to = mWithdrawCommission.get(i).getTo();
            if (mAmount >= from && mAmount <= to) {
                comission = mWithdrawCommission.get(i).getCommission();
            }
        }
        transactionFees = comission;
        transactionFees = Double.parseDouble(new DecimalFormat("##.##").format(transactionFees));
        transactionFeeTV.setText(String.valueOf(transactionFees));
        double finalFee = mAmount - transactionFees - mTDSAmount;
        TextView finalPay = dialog.findViewById(R.id.tv_final_fee);
        finalPay.setText(String.valueOf(Double.parseDouble(new DecimalFormat("##.##").format(finalFee))));
        Button okBTN = dialog.findViewById(R.id.btn_send);
        okBTN.setOnClickListener(view -> {
            dialog.dismiss();
            mSubmitBTN.setEnabled(true);
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.sendOtp(mAppPreference.getMobile());
        });
        Button mNoBTN = dialog.findViewById(R.id.btn_cancel);
        mNoBTN.setOnClickListener(view -> {
            dialog.dismiss();
            mSubmitBTN.setEnabled(true);
            mIsDataRefresh = false;
        });
        tdsInfoTV.setOnClickListener(view -> {
            Intent i = new Intent(this, TermsActivity.class);
            i.putExtra(AppConstant.FROM, AppConstant.TDS);
            startActivity(i);
        });
        dialog.show();
    }

    private void setWalletBenefeciaryData() {
        ProfileDetails profileData = mAppPreference.getProfileData();
        mWinningCoins = profileData.getCoins().getWinning();
        mAddressET.setText(profileData.getState());
        Resources res = getResources();
        if (String.valueOf(profileData.getCoins().getWinning()).contains(".")) {
            mWinningCashTV.setText(String.format(res.getString(R.string.text_wallet_winning_coins), "₹" + String.format("%.2f", mWinningCoins)));
        } else {
            mWinningCashTV.setText(String.format("₹" + res.getString(R.string.text_wallet_winning_coins), "₹" + mWinningCoins));
        }
    }

    private void getBeneficiaryList() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getBeneficiaryList();
        } else {
            Snackbar.make(mSubmitBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    private final IOnWithdrawOtpListener onWithdrawOtpListener = new IOnWithdrawOtpListener() {
        @Override
        public void onVerifyOtpTransferListener(String otp) {
            showProgress(getString(R.string.txt_progress_authentication));
            if (mPayoutGateway == 1) {//cashfree
                mPayoutGatewayName = "CashFree";
                mPresenter.onCashfreeTransfer(mBeneficiaryId, mAmountET.getText().toString().trim(), otp);
            } else if (mPayoutGateway == 2) {  //razorpay - bank_account
                mPayoutGatewayName = "Razorpay";
                mPresenter.onRazorpayTransfer(mFundAccountId, mPaymentMode, mAmountET.getText().toString().trim(), otp, mBeneficiaryId);
            } else if (mPayoutGateway == 3) { //// apexpay
                mPayoutGatewayName = "Apexpay";
                mPresenter.onApexPayTransfer(mBeneficiaryId, mAmountET.getText().toString().trim(), otp);
            } else if (mPayoutGateway == 5 || mPayoutGateway == 6) { // easebuzz ==5, neokred ==6
                if (mPayoutGateway == 5) {
                    mPayoutGatewayName = "Easebuzz";
                } else {
                    mPayoutGatewayName = "Neokred";
                }
                double amount=Double.parseDouble(mAmountET.getText().toString().trim());
                mPresenter.onEasebuzzTransfer(mBeneficiaryId, amount, otp, mPayoutGateway);
            } else if (mPayoutGateway == 7) { /* ===Race-Condition after OTP Verify=== */
                mPresenter.onRaceConditionTransferFinal(mBeneficiaryId, mAmountET.getText().toString().trim(), otp);
            } else if (mPayoutGateway == 8) {
                mPresenter.onIPayTransfer(mBeneficiaryId, mAmountET.getText().toString().trim(), otp, LocationCheckUtils.getInstance().getmLatitute(), LocationCheckUtils.getInstance().getmLongitude());
            } else {
                mPresenter.onPaySharpTransfer(mBeneficiaryId, mAmountET.getText().toString().trim(), otp);
            }
        }
        @Override
        public void onResendOtpListener(){
            mIsDataRefresh = true;
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.resendOtp(mAppPreference.getMobile());
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (!mIsDataRefresh) {
            refreshData();
        }
    }

    private void refreshData() {
        setPaymentSelected();
        mAmountDetailsLL.setVisibility(View.GONE);
        mAdapter.setSelectedPos(selectedPos);
        mAdapter.notifyDataSetChanged();
        mAmountET.setText("");
        getBeneficiaryList();
    }

    private IOnWithdrawVerifyListener onWithdrawListener = new IOnWithdrawVerifyListener() {

        @Override
        public void onWithdrawVerify() {
            refreshData();
        }

        @Override
        public void onAadharVerify(String id) {
            Intent i = new Intent(NewWithdrawActivity.this, AadharActivity.class);
            i.putExtra(AppConstant.FROM, AppConstant.WITHDRAW_VERIFICATION);
            i.putExtra(AppConstant.ID, id);
            startActivity(i);
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
    public void iOnAddressSuccess() {

    }

    @Override
    public void iOnAddressFailure() {

    }

    private void setUpAdvertisementViewPager(List<BannerDetails> advertisementDetails) {
        mAdvertisementsList.clear();
        mAdvertisementsList.addAll(advertisementDetails);
        List<Fragment> mFragmentList = new ArrayList<>();
        for (BannerDetails advertisement : advertisementDetails) {
            mFragmentList.add(BannerFragment.getInstance(advertisement));
        }
        BannerPagerAdapter adapter=new BannerPagerAdapter(this.getSupportFragmentManager(),mFragmentList);
        mBannerVP.setAdapter(adapter);
        mBannerVP.setOffscreenPageLimit(3);
        if (mHandler==null){
            mHandler=new Handler();
            moveToNextAd(0);
        }
    }
    private void moveToNextAd(int i) {
        mBannerVP.setCurrentItem(i, true);
        mHandler.postDelayed(() -> {
            int currentItem = mBannerVP.getCurrentItem();
            moveToNextAd((currentItem + 1) % mAdvertisementsList.size() == 0 ? 0 : currentItem + 1);
        }, 10000);
    }
    @Override
    protected void onDestroy(){
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }
}