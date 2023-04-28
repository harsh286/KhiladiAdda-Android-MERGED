package com.khiladiadda.battle;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.khiladiadda.R;
import com.khiladiadda.dialogs.interfaces.IOnJoinBattleListener;
import com.khiladiadda.network.model.response.Coins;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.wallet.WalletCashbackActivity;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JoinBattleDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.ll_bonus)
    LinearLayout mBonusLL;
    @BindView(R.id.tv_bonus)
    TextView mBonusTV;
    @BindView(R.id.tv_wallet_bonus)
    TextView mWalletBonusTV;
    @BindView(R.id.ll_total_wallet)
    LinearLayout mTotalWalletLL;
    @BindView(R.id.ll_wallet)
    LinearLayout mWalletLL;
    @BindView(R.id.ll_profit)
    LinearLayout mProfitLL;
    @BindView(R.id.tv_recharge)
    TextView mRechargeTV;
    @BindView(R.id.et_amount)
    EditText mAmountET;
    @BindView(R.id.tv_total_wallet_entry)
    TextView mTotalWalletTV;
    @BindView(R.id.tv_wallet)
    TextView mWalletTV;
    @BindView(R.id.tv_wallet_entry)
    TextView mDepositWalletTV;
    @BindView(R.id.tv_estimated_profit)
    TextView mEstimatedProfitTV;
    @BindView(R.id.btn_cancel)
    Button mCancelBTN;
    @BindView(R.id.btn_send)
    Button mSendBTN;
    @BindView(R.id.tv_estimated_winning)
    TextView mEstimatedWinningTV;
    @BindView(R.id.tv_hint)
    TextView mHintTV;
    @BindView(R.id.tv_heading)
    TextView mHeadingTV;
    @BindView(R.id.tv_msg)
    TextView mMsgTV;

    private final IOnJoinBattleListener mOnJoinBattleListener;
    private final Context mContext;
    private double mAmount = 0;
    private double mDepositWinWallet;
    private boolean mLowBalance, mIsFree;
    private double mBattleInvestedAmount, mGroupInvestedAmount, mBonusAmount;
    private long mGroupJoined, mBonusCode;
    private final int mPosition;
    private int mFrom;
    private Coins mCoins;

    public JoinBattleDialog(@NonNull Context context, IOnJoinBattleListener onJoinBattleListener, int position, double battleInvestedAmount, double groupInvestedAmount, long groupJoined, double amount, int from, long bonus, boolean isFree) {
        super(context);
        this.mContext = context;
        this.mOnJoinBattleListener = onJoinBattleListener;
        this.mBattleInvestedAmount = battleInvestedAmount;
        this.mGroupInvestedAmount = groupInvestedAmount;
        this.mGroupJoined = groupJoined;
        this.mPosition = position;
        this.mAmount = amount;
        this.mFrom = from;
        this.mBonusCode = bonus;
        this.mIsFree = isFree;
        init();
    }

    private void init() {
        initDialog();
        initViews();
        show();
    }

    private void initDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_join_battle);
        getWindow().setBackgroundDrawableResource(R.color.colorTransparent);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    private void initViews() {
        ButterKnife.bind(this);
        mCancelBTN.setOnClickListener(this);
        mSendBTN.setOnClickListener(this);
        mEstimatedWinningTV.setOnClickListener(this);
        mCoins = AppSharedPreference.getInstance().getProfileData().getCoins();
        mDepositWinWallet = mCoins.getDeposit() + mCoins.getWinning();
        double mTotalWalletBal = mCoins.getDeposit() + mCoins.getWinning() + mCoins.getBonus();
        mTotalWalletTV.setText(new DecimalFormat("##.##").format(mTotalWalletBal));
        mDepositWalletTV.setText(new DecimalFormat("##.##").format(mDepositWinWallet));
        if (mIsFree) {
            mHintTV.setText("In free battle investment is 0coins.");
        }
        if (mBonusCode != 0) {
            mWalletTV.setText("Deposit+Winning\n(" + new DecimalFormat("##.##").format(mDepositWinWallet) + ")");
            mDepositWalletTV.setText(R.string.text_zero);
            mBonusLL.setVisibility(View.VISIBLE);
            mBonusTV.setText("Bonus (" + new DecimalFormat("##.##").format(mCoins.getBonus()) + ")");
            mWalletBonusTV.setText(R.string.text_zero);
        }
        if (mFrom == 2) {
            mAmountET.setEnabled(false);
            mAmountET.setText(String.valueOf(mAmount));
            mMsgTV.setVisibility(View.GONE);
            mTotalWalletLL.setVisibility(View.GONE);
            mWalletLL.setVisibility(View.GONE);
            mHeadingTV.setText("Invested Amount");
            mHintTV.setText("Amount has been invested from previous invested group.");
            mProfitLL.setVisibility(View.GONE);
            //            checkEstimatedWinning();
        }
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
                mOnJoinBattleListener.onCancelBattleListener(mPosition);
                cancel();
                break;
            case R.id.btn_send:
                if (!mIsFree) {
                    if (mLowBalance) {
                        mContext.startActivity(new Intent(mContext, WalletCashbackActivity.class));
                    } else if (mAmount < 9) {
                        AppUtilityMethods.showMsg(mContext, mContext.getString(R.string.text_battle_amount), false);
                    } else {
                        checkData();
                    }
                } else {
                    mOnJoinBattleListener.onJoinBattleListener(mAmount);
                    cancel();
                }
                break;
            case R.id.tv_estimated_winning:
                AppUtilityMethods.showMsg(mContext, mContext.getString(R.string.text_estimated_winning_vary), false);
                break;
        }
    }

    private void checkData() {
        if (!TextUtils.isEmpty(mAmountET.getText().toString().trim()) && mAmount > 9) {
            mOnJoinBattleListener.onJoinBattleListener(mAmount);
            cancel();
        } else {
            AppUtilityMethods.showMsg(mContext, mContext.getString(R.string.text_battle_amount), false);
        }
    }

    private void checkEstimatedWinning() {
        double estimatedWinning = 0;
        if (mGroupJoined > 1) {
            estimatedWinning = ((mBattleInvestedAmount + mAmount) - ((mBattleInvestedAmount + mAmount) / 10)) * (mAmount / (mGroupInvestedAmount + mAmount));
        } else {
            estimatedWinning = mAmount * 1.2;
        }
        if (mAmount > 9) {
            mRechargeTV.setVisibility(View.GONE);
            mTotalWalletLL.setVisibility(View.GONE);
            mWalletLL.setVisibility(View.GONE);
            mProfitLL.setVisibility(View.GONE);
            mBonusLL.setVisibility(View.GONE);
            mEstimatedProfitTV.setText("");
            if (mFrom == 1 && mBonusCode != 0) {
//                mBonusAmount = (mAmount * ((double) mBonusCode / 100));
//                double balAmount = mCoins.getDeposit() + mCoins.getWinning() + mCoins.getBonus() + mBonusAmount;
//
//                if ((mCoins.getBonus() >= mBonusAmount) && (mAmount <= balAmount)) {
//                    showData(estimatedWinning);
//                } else  if (mCoins.getBonus() < mBonusAmount && (mAmount<=balAmount)) {
//                    double usableAmount = mAmount - mCoins.getBonus();
//                    double tm = (mCoins.getDeposit() + mCoins.getWinning()) - usableAmount;
//
//                    showData(estimatedWinning);
//                } else {
//                    lowBalanceMsg();
//                }
                mBonusAmount = (mAmount * ((double) mBonusCode / 100));
                double bonusUsable = mCoins.getBonus() - mBonusAmount;
                double bonus;
                if (bonusUsable < 0) {
                    bonus = mCoins.getBonus();
                } else {
                    bonus = mBonusAmount;
                }
                if (mAmount <= (mCoins.getDeposit() + mCoins.getWinning() + bonus)) {
                    showData(estimatedWinning);
                } else {
                    lowBalanceMsg();
                }
            } else if (mAmount > mDepositWinWallet && mFrom == 1) {
                lowBalanceMsg();
            } else {
                showData(estimatedWinning);
            }
        }
    }

    private void lowBalanceMsg() {
        mLowBalance = true;
        mRechargeTV.setVisibility(View.VISIBLE);
        mSendBTN.setText(R.string.text_recharge);
    }

    private void showData(double estimatedWinning) {
        mLowBalance = false;
        if (mFrom == 1) {
            mTotalWalletLL.setVisibility(View.VISIBLE);
            mWalletLL.setVisibility(View.VISIBLE);
            if (mBonusCode != 0) {
                mBonusLL.setVisibility(View.VISIBLE);
                mBonusAmount = (mAmount / 100) * mBonusCode;
                if (mCoins.getBonus() >= mBonusAmount && mCoins.getBonus() > 0) {
                    double depositWinAmount = mAmount - mBonusAmount;
                    mWalletBonusTV.setText(new DecimalFormat("##.##").format(mBonusAmount));
                    mDepositWalletTV.setText(new DecimalFormat("##.##").format(depositWinAmount));
                } else if (mCoins.getBonus() <= 0) {
                    mWalletBonusTV.setText("0");
                    mDepositWalletTV.setText(new DecimalFormat("##.##").format(mAmount));
                } else {
                    mWalletBonusTV.setText(new DecimalFormat("##.##").format(mCoins.getBonus()));
                    mDepositWalletTV.setText(new DecimalFormat("##.##").format(mAmount - mCoins.getBonus()));
                }
            }
        }
        mProfitLL.setVisibility(View.VISIBLE);
        mSendBTN.setText(R.string.text_confirm);
        mEstimatedProfitTV.setText(new DecimalFormat("##.##").format(estimatedWinning));
    }

}