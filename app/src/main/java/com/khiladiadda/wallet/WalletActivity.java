package com.khiladiadda.wallet;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.main.MainActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
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
import com.khiladiadda.network.model.response.TransactionDetails;
import com.khiladiadda.network.model.response.VersionResponse;
import com.khiladiadda.network.model.response.ZaakpayChecksumResponse;
import com.khiladiadda.transaction.PaymentHistoryActivity;
import com.khiladiadda.transaction.TransactionActivity;
import com.khiladiadda.transaction.adapter.TransactionAdapter;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.LocationCheckUtils;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.wallet.interfaces.IWalletPresenter;
import com.khiladiadda.wallet.interfaces.IWalletView;
import com.khiladiadda.withdrawcoins.NewWithdrawActivity;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;
import com.moengage.inapp.MoEInAppHelper;
import com.moengage.widgets.NudgeView;

import java.util.ArrayList;

import butterknife.BindView;

public class WalletActivity extends BaseActivity implements IWalletView, TransactionAdapter.IOnItemChildClickListener, LocationCheckUtils.IOnAdressPassed {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.btn_add_coins)
    Button mAddCoinsBTN;
    @BindView(R.id.btn_withdraw)
    Button mWithdrawBTN;
    @BindView(R.id.tv_coins)
    TextView mTotalCoinsTV;
    @BindView(R.id.tv_deposit)
    TextView mDepositTV;
    @BindView(R.id.tv_winning)
    TextView mWinningTV;
    @BindView(R.id.tv_bonus)
    TextView mBonusTV;
    @BindView(R.id.rv_transaction)
    RecyclerView mTransactionRV;
    @BindView(R.id.tv_recent_transaction)
    TextView mTransactionTV;
    @BindView(R.id.tv_payment_history)
    TextView mPaymentHistoryTV;

    private boolean mWithdraw, mPaytm;
    private IWalletPresenter mPresenter;
    private TransactionAdapter mAdapter;
    private ArrayList<TransactionDetails> mList;
    private boolean isAllowed = true;
    private double mDepositCoins,mWinningCoins,mBonusCoins;
    @BindView(R.id.nudge)
    NudgeView mNV;

    @Override
    protected int getContentView() {
        return R.layout.activity_wallet;
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
        mActivityNameTV.setText(R.string.my_wallet);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mAddCoinsBTN.setOnClickListener(this);
        mWithdrawBTN.setOnClickListener(this);
        mTransactionTV.setOnClickListener(this);
        mPaymentHistoryTV.setOnClickListener(this);
        mPaymentHistoryTV.setVisibility(View.VISIBLE);
        LocationCheckUtils.initialize(this, this, this);
        if (mAppPreference.getBoolean(AppConstant.IS_LOCATION_ENABLED, false)) {

            if (!LocationCheckUtils.getInstance().hasLocationPermission()) {
                LocationCheckUtils.getInstance().statusCheck();
            } else {
                LocationCheckUtils.getInstance().requestNewLocationData();
            }
        }
    }

    @Override
    protected void initVariables() {
        mPresenter = new WalletPresenter(this);
        mList = new ArrayList<>();
        mAdapter = new TransactionAdapter(mList);
        mTransactionRV.setLayoutManager(new LinearLayoutManager(this));
        mTransactionRV.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
        getData();
    }

    private void getData() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getProfile();
        } else {
            setData();
            Snackbar.make(mWithdrawBTN, getString(R.string.snackbar_internet_off), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        Intent i;
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
            case R.id.btn_add_coins:
                if (mAppPreference.getBoolean(AppConstant.IS_LOCATION_ENABLED, false)) {
                    if(LocationCheckUtils.getInstance().hasLocationPermission()) {
                        LocationCheckUtils.getInstance().requestNewLocationData();
                        if (isAllowed) {
//                            i = new Intent(this, AddWalletActivity.class);
                            i = new Intent(this, WalletCashbackActivity.class);
                            walletActivityResultLauncher.launch(i);
                        } else
                            Snackbar.make(mAddCoinsBTN, R.string.not_allowed, Snackbar.LENGTH_SHORT).show();

                    } else {
                        AppDialog.DialogWithLocationCallBack(this, "KhiladiAdda need to access your location.");
                    }
                } else {
//                    i = new Intent(this, AddWalletActivity.class);
                    i = new Intent(this, WalletCashbackActivity.class);
                    walletActivityResultLauncher.launch(i);
                }
                break;

            case R.id.btn_withdraw:
                if (mAppPreference.getBoolean(AppConstant.IS_LOCATION_ENABLED, false)) {
                    if (LocationCheckUtils.getInstance().hasLocationPermission()) {
                        LocationCheckUtils.getInstance().requestNewLocationData();
                        if (isAllowed) {
                            i = new Intent(this, NewWithdrawActivity.class);
                            mAppPreference.setBoolean(AppConstant.IS_PAYTMWALLET_ENABLED, mPaytm);
                            walletActivityResultLauncher.launch(i);
                        } else
                            Snackbar.make(mAddCoinsBTN, R.string.not_allowed, Snackbar.LENGTH_SHORT).show();

                    } else {
                        AppDialog.DialogWithLocationCallBack(this, "KhiladiAdda need to access your location.");
                    }
                } else {
                    i = new Intent(this, NewWithdrawActivity.class);
                    mAppPreference.setBoolean(AppConstant.IS_PAYTMWALLET_ENABLED, mPaytm);
                    walletActivityResultLauncher.launch(i);
                }
                break;
            case R.id.tv_payment_history:
                i = new Intent(this, PaymentHistoryActivity.class);
                startActivity(i);
                break;
            case R.id.tv_recent_transaction:
                i = new Intent(this, TransactionActivity.class);
                i.putExtra(AppConstant.FROM, AppConstant.FROM_WALLET);
                startActivity(i);
                break;
        }
    }

    private void setData() {
        Coins coins = mAppPreference.getProfileData().getCoins();
          mDepositCoins = coins.getDeposit();
          /*point calculation*/
         int mBonus=((int)(coins.getBonus()*100));
          mBonusCoins=((double) mBonus)/100.0;
         /*Deposit*/
        int mDeposit=((int)(coins.getDeposit()*100));
         mDepositCoins=((double) mDeposit)/100.0;
         /*winning*/
        int mWinning=((int)(coins.getWinning()*100));
        mWinningCoins=((double) mWinning)/100.0;
        if (!TextUtils.isEmpty(mAppPreference.getMobile()) && !mAppPreference.getMobile().startsWith("8888888888")) {
            mAppPreference.setMobile(String.valueOf(mAppPreference.getMobile()));
        }
        if (String.valueOf(mDepositCoins).contains(".")) {
            mDepositTV.setText(getString(R.string.text_deposited_next) + String.format("%.2f", mDepositCoins));
        } else {
            mDepositTV.setText(getString(R.string.text_deposited_next) + mDepositCoins);
        }
        if (String.valueOf(coins.getWinning()).contains(".")) {
            mWinningTV.setText(getString(R.string.text_wining_next) +String.format("%.2f", mWinningCoins));
        } else {
            mWinningTV.setText(getString(R.string.text_wining_next) + mWinningCoins);
        }
        if (String.valueOf(coins.getBonus()).contains(".")) {

            mBonusTV.setText(getString(R.string.text_bonus_next)+String.format("%.2f", mBonusCoins));

        } else {
            mBonusTV.setText(getString(R.string.text_bonus_next) +mBonusCoins);
        }
        double total=mDepositCoins + mBonusCoins + mWinningCoins;
        int totalCoins=((int)(total*100));
        total=((double) totalCoins)/100.0;
        if(String.valueOf(total).contains(".")){
            mTotalCoinsTV.setText("Total Balance\n" + "₹" + String.format("%.2f", total));
        } else {
            mTotalCoinsTV.setText("Total Balance\n" + "₹" + total);
        }

        SpannableString deposit = new SpannableString(mDepositTV.getText().toString());
        deposit.setSpan(new RelativeSizeSpan(1.2f), 9, deposit.length(), 0); // set size
        mDepositTV.setText(deposit);
        SpannableString winning = new SpannableString(mWinningTV.getText().toString());
        winning.setSpan(new RelativeSizeSpan(1.2f), 7, winning.length(), 0); // set size
        mWinningTV.setText(winning);
        SpannableString bonus = new SpannableString(mBonusTV.getText().toString());
        bonus.setSpan(new RelativeSizeSpan(1.2f), 5, bonus.length(), 0); // set size
        mBonusTV.setText(bonus);
        SpannableString totalCoin = new SpannableString(mTotalCoinsTV.getText().toString());
        totalCoin.setSpan(new RelativeSizeSpan(1.5f), 13, totalCoin.length(), 0); // set size
        mTotalCoinsTV.setText(totalCoin);
        Properties properties = new Properties();
        properties.addAttribute(getString(R.string.text_wining_next), coins.getWinning()).addAttribute("Currency", "INR").addAttribute(getString(R.string.text_deposited_next), coins.getDeposit()).addAttribute(getString(R.string.text_bonus_next), coins.getBonus()).addAttribute("Total Balance", total);
        MoEAnalyticsHelper.INSTANCE.trackEvent(this, "Wallet Balance", properties);

    }

    @Override
    public void onProfileComplete(ProfileTransactionResponse responseModel) {
        hideProgress();
        mAppPreference.setProfileData(responseModel.getResponse());
        if (responseModel.getTransactionDetails().size() > 0) {
            mList.clear();
            mList.addAll(responseModel.getTransactionDetails());
            mAdapter.notifyDataSetChanged();
        }
        mWithdraw = responseModel.getWallet();
        mPaytm = responseModel.getPaytmEnabled();
        setData();
    }

    @Override
    public void onProfileFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onInvoiceComplete(InvoiceResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            try {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setDataAndType(Uri.parse(responseModel.getResponse().getFileUrl()), "application/pdf");
                Intent chooser = Intent.createChooser(browserIntent, getString(R.string.chooser_title));
                chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // optional
                startActivity(chooser);
            } catch (ActivityNotFoundException e) {
                AppUtilityMethods.showMsg(this, "Please download an app to open", false);
            }
        }
    }

    @Override
    public void onInvoiceFailure(ApiError error) {
        hideProgress();
    }

    @Override
    protected void onDestroy() {
        AppUtilityMethods.deleteCache(this);
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void onInvoiceClicked(int position) {
        showProgress(getString(R.string.txt_progress_authentication));
        mPresenter.getInvoice(mList.get(position).getId());
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

    ActivityResultLauncher<Intent> walletActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        getData();
    });

    @Override
    public void iOnAddressSuccess() {
        isAllowed = true;
    }

    @Override
    public void iOnAddressFailure() {
        isAllowed = false;
    }
}