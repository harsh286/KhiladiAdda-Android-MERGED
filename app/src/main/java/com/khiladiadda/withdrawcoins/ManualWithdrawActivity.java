package com.khiladiadda.withdrawcoins;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.help.HelpActivity;
import com.khiladiadda.league.myleague.MyLeagueActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.ManualWithdrawRequest;
import com.khiladiadda.network.model.response.AddBeneficiaryResponse;
import com.khiladiadda.network.model.response.BeneficiaryResponse;
import com.khiladiadda.network.model.response.BeneficiaryVerifyResponse;
import com.khiladiadda.network.model.response.ManualWithdrawDetails;
import com.khiladiadda.network.model.response.ManualWithdrawResponse;
import com.khiladiadda.network.model.response.OtpResponse;
import com.khiladiadda.network.model.response.PayoutResponse;
import com.khiladiadda.network.model.response.TdsResponse;
import com.khiladiadda.network.model.response.WIthdrawLimitResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.withdrawcoins.adapter.ManualWithdrawAdapter;
import com.khiladiadda.withdrawcoins.interfaces.IWithdrawPresenter;
import com.khiladiadda.withdrawcoins.interfaces.IWithdrawView;

import java.util.ArrayList;

import butterknife.BindView;

public class ManualWithdrawActivity extends BaseActivity implements IWithdrawView, RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.tv_wining_cash)
    TextView mWinningCashTV;
    @BindView(R.id.rg_payment)
    RadioGroup mPaymentRG;
    @BindView(R.id.et_payment_address)
    TextView mPaymentAddressET;
    @BindView(R.id.et_amount)
    TextView mAmountET;
    @BindView(R.id.btn_submit)
    Button mSubmitBTN;
    @BindView(R.id.rv_withdraw)
    RecyclerView mWithdrawRV;
    @BindView(R.id.ll_top)
    LinearLayout mTopLL;

    private IWithdrawPresenter mPresenter;
    private ManualWithdrawAdapter mAdapter;
    private ArrayList<ManualWithdrawDetails> mList;
    private int mWithdrawAmount;
    private String mPaymentMode = AppConstant.PATYM;
    private double mWinningAmount;

    @Override
    protected int getContentView() {
        return R.layout.activity_manual_withdraw;
    }

    @Override
    protected void initViews() {
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mSubmitBTN.setOnClickListener(this);
        mActivityNameTV.setText(R.string.text_withdraw_history);
        mPaymentRG.check(R.id.rb_paytm);
        mPaymentRG.setOnCheckedChangeListener(this);

        mWinningAmount = mAppPreference.getProfileData().getCoins().getWinning();
        mWinningCashTV.setText(mWinningAmount + "\nWinning");

        String from = getIntent().getStringExtra(AppConstant.FROM);
        if (from.equalsIgnoreCase(AppConstant.FROM_TRANSACTION)) {
            mTopLL.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initVariables() {
        mPresenter = new WithdrawPresenter(this);
        mList = new ArrayList<>();
        mAdapter = new ManualWithdrawAdapter(this, mList);
        mWithdrawRV.setLayoutManager(new LinearLayoutManager(this));
        mWithdrawRV.setAdapter(mAdapter);
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getManualWithdraw();
        } else {
            Snackbar.make(mWithdrawRV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
        mAmountET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    mWithdrawAmount = Integer.parseInt(mAmountET.getText().toString().trim());
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
            case R.id.tv_home:
                finish();
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.tv_league:
                startActivity(new Intent(this, MyLeagueActivity.class));
                break;
            case R.id.tv_help:
                startActivity(new Intent(this, HelpActivity.class));
                break;
            case R.id.btn_submit:
                if (TextUtils.isEmpty(mPaymentAddressET.getText().toString().trim()) && mPaymentMode.equalsIgnoreCase(AppConstant.PATYM)) {
                    AppUtilityMethods.showMsg(this, "Please provide Paytm mobile number", false);
                } else if (TextUtils.isEmpty(mPaymentAddressET.getText().toString().trim()) && mPaymentMode.equalsIgnoreCase(AppConstant.UPI)) {
                    AppUtilityMethods.showMsg(this, "Please provide UPI address", false);
                } else mPresenter.validateData();
                break;
        }
    }

    @Override
    public String getAmount() {
        return mAmountET.getText().toString().trim();
    }

    @Override
    public void onValidationComplete() {
        if (mWithdrawAmount > 10000) {
            AppUtilityMethods.showMsg(this, "You can withdraw maximum Rs.10000 at a time. ", false);
        } else if (mWithdrawAmount <= mWinningAmount) {
            showProgress(getString(R.string.txt_progress_authentication));
            ManualWithdrawRequest request = new ManualWithdrawRequest();
            request.setPaymentAddress(mPaymentAddressET.getText().toString().trim());
            request.setPaymentMode(mPaymentMode);
            request.setWithdrawCoins(mWithdrawAmount);
            mPresenter.doManualWithdraw(request);
        } else {
            AppUtilityMethods.showMsg(this, "Not enough coins in your wallet. Withdraw coins should be less or equal to wallet balance.", false);
        }
    }

    @Override
    public void onValidationFailure(String errorMsg) {
        AppUtilityMethods.showMsg(this, errorMsg, false);
    }

    @Override
    public void onGetBeneficiaryListComplete(BeneficiaryResponse responseModel) {
    }

    @Override
    public void onGetBeneficiaryListFailure(ApiError error) {
    }

    @Override
    public void onAddCashfreeBeneficiaryComplete(AddBeneficiaryResponse response) {
    }

    @Override
    public void onAddCashfreeBeneficiaryFailed(ApiError error) {
    }

    @Override
    public void onCashfreeTransferComplete(PayoutResponse response) {
    }

    @Override
    public void onCashfreeTransferFailed(ApiError error) {
    }

    @Override
    public void onDeleteComplete(BaseResponse response) {
    }

    @Override
    public void onDeleteFailed(ApiError error) {
    }

    @Override
    public void onManualWithdrawComplete(BaseResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            AppUtilityMethods.showMsgCancel(this, "Your withdrawal request has been created. We will credit your amount in next 48hours.", false);
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override
    public void onManualWithdrawFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onGetManualWithdrawComplete(ManualWithdrawResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            if (responseModel.getResponse().size() > 0) {
                mList.clear();
                mList.addAll(responseModel.getResponse());
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onGetManualWithdrawFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onResendOtpComplete(OtpResponse responseModel) {

    }

    @Override
    public void onResendOtpFailure(ApiError error) {

    }

    @Override
    public void onSendOtpComplete(BaseResponse responseModel) {

    }

    @Override
    public void onSendOtpFailure(ApiError error) {

    }

    @Override
    public void onAddRazorpayBeneficiaryComplete(AddBeneficiaryResponse response) {

    }

    @Override
    public void onAddRazorpayBeneficiaryFailed(ApiError error) {

    }

    @Override
    public void onRazorpayTransferComplete(PayoutResponse response) {

    }

    @Override
    public void onRazorpayTransferFailed(ApiError error) {

    }

    @Override
    public void onWithdrawLimitComplete(WIthdrawLimitResponse response) {

    }

    @Override
    public void onWithdrawLimitFailed(ApiError error) {

    }

    @Override
    public void onVerifyBeneficiaryComplete(BeneficiaryVerifyResponse responseModel) {

    }

    @Override
    public void onVerifyBeneficiaryFailure(ApiError error) {

    }

    @Override
    public void onApexPayTransferComplete(PayoutResponse response) {

    }

    @Override
    public void onApexPayTransferFailed(ApiError error) {

    }

    @Override
    public void onPaySharpTransferComplete(PayoutResponse response) {

    }

    @Override
    public void onPaySharpTransferFailed(ApiError error) {

    }

    @Override
    public void onAddBeneficiaryComplete(AddBeneficiaryResponse response) {

    }

    @Override
    public void onAddBeneficiaryFailed(ApiError error) {

    }

    @Override
    public void onRaceConditionComplete(PayoutResponse response) {

    }

    @Override
    public void onRaceConditionFailed(ApiError error) {

    }

    @Override
    public void onIPayComplete(PayoutResponse response) {

    }

    @Override
    public void onIPayFailed(ApiError error) {

    }

    @Override
    public void onCheckTDSComplete(TdsResponse response) {

    }

    @Override
    public void onCheckTDSFailed(ApiError error) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_paytm:
                mPaymentAddressET.setText("");
                mPaymentAddressET.setHint(getString(R.string.text_paytm_number));
                mPaymentMode = AppConstant.PATYM;
                break;
            case R.id.rb_upi:
                mPaymentAddressET.setText("");
                mPaymentAddressET.setHint(getString(R.string.text_upi_address));
                mPaymentMode = AppConstant.UPI;
                break;
        }
    }

}