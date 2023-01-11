package com.khiladiadda.clashx2.kabaddi.createbattle;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.khiladiadda.R;
import com.khiladiadda.dialogs.interfaces.IOnCreateBattleListener;
import com.khiladiadda.network.model.response.Coins;
import com.khiladiadda.network.model.response.hth.KaPlayerHTH;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppUtilityMethods;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KabaddiCreateBattleDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.et_amount)
    EditText mAmountET;
    @BindView(R.id.tv_fifty)
    TextView mFiftyTV;
    @BindView(R.id.tv_hundred)
    TextView mHundredTV;
    @BindView(R.id.tv_two_hundred)
    TextView mTwoHundredTV;
    @BindView(R.id.tv_five_hundred)
    TextView mFiveHundredTV;
    @BindView(R.id.tv_entry_fee)
    TextView mEntryFeeTV;
    @BindView(R.id.tv_total_wallet_balance)
    TextView mTotalWalletBalanceTV;
    @BindView(R.id.tv_wallet)
    TextView mWalletTV;
    @BindView(R.id.tv_wallet_entry)
    TextView mDepositWalletTV;
    @BindView(R.id.tv_estimated_winning)
    TextView mEstimatedProfitTV;
    @BindView(R.id.btn_cancel)
    Button mCancelBTN;
    @BindView(R.id.btn_send)
    Button mSendBTN;
    @BindView(R.id.tv_recharge)
    TextView mRechargeTV;

    private final IOnCreateBattleListener mOnCreateBattleListener;
    private double mAmount = 0;
    private double mDepositWinWallet;
    private int mFrom;
    private List<KaPlayerHTH> mPlayerList;

    public KabaddiCreateBattleDialog(@NonNull Context context, IOnCreateBattleListener onCreateBattleListener, List<KaPlayerHTH> playerList) {
        super(context);
        this.mOnCreateBattleListener = onCreateBattleListener;
        this.mPlayerList = playerList;
        init();
    }

    private void init() {
        initDialog();
        initViews();
        show();
    }

    private void initDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_create_battle);
        getWindow().setBackgroundDrawableResource(R.color.colorTransparent);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    private void initViews() {
        ButterKnife.bind(this);
        mCancelBTN.setOnClickListener(this);
        mSendBTN.setOnClickListener(this);
        mFiftyTV.setOnClickListener(this);
        mHundredTV.setOnClickListener(this);
        mTwoHundredTV.setOnClickListener(this);
        mFiveHundredTV.setOnClickListener(this);
        Coins mCoins = AppSharedPreference.getInstance().getProfileData().getCoins();
        mDepositWinWallet = mCoins.getDeposit() + mCoins.getWinning();
        double mTotalWalletBal = mCoins.getDeposit() + mCoins.getWinning() + mCoins.getBonus();
        mTotalWalletBalanceTV.setText(new DecimalFormat("##.##").format(mTotalWalletBal));
        mDepositWalletTV.setText(new DecimalFormat("##.##").format(mDepositWinWallet));
        mAmountET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0 && s.length() > 1) {
                    mAmount = Double.parseDouble(mAmountET.getText().toString().trim());
                    checkEstimatedWinning();
                } else {
                    mAmount = 0;
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
            case R.id.btn_cancel:
                mOnCreateBattleListener.onCancelBattleListener();
                cancel();
                break;
            case R.id.btn_send:
                checkData();
                break;
            case R.id.tv_fifty:
                mFiftyTV.setSelected(true);
                mHundredTV.setSelected(false);
                mTwoHundredTV.setSelected(false);
                mFiveHundredTV.setSelected(false);
                mAmountET.setText(R.string.text_ten);
                break;
            case R.id.tv_hundred:
                mFiftyTV.setSelected(false);
                mHundredTV.setSelected(true);
                mTwoHundredTV.setSelected(false);
                mFiveHundredTV.setSelected(false);
                mAmountET.setText(R.string.text_fifty);
                break;
            case R.id.tv_two_hundred:
                mFiftyTV.setSelected(false);
                mHundredTV.setSelected(false);
                mTwoHundredTV.setSelected(true);
                mFiveHundredTV.setSelected(false);
                mAmountET.setText(R.string.text_hundred);
                break;
            case R.id.tv_five_hundred:
                mFiftyTV.setSelected(false);
                mHundredTV.setSelected(false);
                mTwoHundredTV.setSelected(false);
                mFiveHundredTV.setSelected(true);
                mAmountET.setText(R.string.text_two_hundred);
                break;
        }
    }

    private void checkData() {
        String amount = mAmountET.getText().toString().trim();
        if (mAmountET.getText().toString().trim().isEmpty()) {
            AppUtilityMethods.showMsg(getContext(), "Amount can not be blank", false);
        } else if (Integer.parseInt(amount) < 10) {
            AppUtilityMethods.showMsg(getContext(), "Challenge can not be of less than 10 coins", false);
        } else if (Integer.parseInt(amount) > 5000) {
            AppUtilityMethods.showMsg(getContext(), "Battle can not be  greater than 5000 coins", false);
        } else if (Integer.parseInt(amount) % 10 != 0) {
            AppUtilityMethods.showMsg(getContext(), "Challenge coins must be multiple of 10.(Ex-10,20,30,40,50,100 and so on)", false);
        } else if (Integer.parseInt(amount) > mDepositWinWallet) {
            AppUtilityMethods.showRechargeMsghth(getContext(), "Your wallet balance is insufficient. Please recharge your wallet to play and earn.");
            dismiss();
        } else {
            mOnCreateBattleListener.onAddBattleListener(mAmount, mPlayerList);
            dismiss();
        }
    }

    private void checkEstimatedWinning() {
//        double estimatedWinning = 0;
        if (mAmount > 9) {
            mEntryFeeTV.setText(mAmountET.getText().toString().trim());
//            int commission = 10;
            double entry = (mAmount * 2);
            double earning = entry - (entry / 10);
            mEstimatedProfitTV.setText(String.valueOf(earning));
        }
    }

    private void lowBalanceMsg() {
//        boolean mLowBalance = true;
        mRechargeTV.setVisibility(View.VISIBLE);
        mSendBTN.setText(R.string.text_recharge);
    }

}