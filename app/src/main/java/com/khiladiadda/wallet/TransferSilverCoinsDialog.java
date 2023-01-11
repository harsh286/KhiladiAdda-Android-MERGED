package com.khiladiadda.wallet;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.khiladiadda.R;
import com.khiladiadda.dialogs.interfaces.IOnSilverCoinsTransferListener;
import com.khiladiadda.utility.AppUtilityMethods;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransferSilverCoinsDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.tv_silver_coin) TextView mSilverCoinsTV;
    @BindView(R.id.btn_deposit_wallet) Button mDepositBTN;
    @BindView(R.id.btn_bonus_wallet) Button mBonusBTN;
    @BindView(R.id.btn_cancel) Button mCancelBTN;
    @BindView(R.id.btn_send) Button mTransferBTN;

    private final IOnSilverCoinsTransferListener mOnChangeDOBListener;
    private double mSilverCoins, mTransferCoins;
    private String mWallet;
    private int mDeposit, mBonus;

    public TransferSilverCoinsDialog(@NonNull Context context, double silverCoins, IOnSilverCoinsTransferListener onChangeDOBListener) {
        super(context);
        this.mSilverCoins = silverCoins;
        this.mOnChangeDOBListener = onChangeDOBListener;
        init();
    }

    private void init() {
        initDialog();
        initViews();
        show();
    }

    private void initDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_silvercoins_transfer);
        getWindow().setBackgroundDrawableResource(R.color.colorTransparent);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    private void initViews() {
        ButterKnife.bind(this);
        mDepositBTN.setOnClickListener(this);
        mBonusBTN.setOnClickListener(this);
        mCancelBTN.setOnClickListener(this);
        mTransferBTN.setOnClickListener(this);
        mSilverCoinsTV.setText("Silver Coins: " + mSilverCoins);
        double deposit = mSilverCoins / 10;
        double bonus = mSilverCoins / 2;
        mDeposit = (int) deposit;
        mBonus = (int) bonus;
        mDepositBTN.setText("Deposited\n" + mDeposit + " Coins");
        mDepositBTN.setText("Bouns\n" + mBonus + " Coins");
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_deposit_wallet:
                mDepositBTN.setSelected(true);
                mBonusBTN.setSelected(false);
                mWallet = "deposit";
                mTransferCoins = mDeposit;
                break;
            case R.id.btn_bonus_wallet:
                mDepositBTN.setSelected(false);
                mBonusBTN.setSelected(true);
                mWallet = "bonus";
                mTransferCoins = mBonus;
                break;
            case R.id.btn_send:
                if (TextUtils.isEmpty(mWallet)) {
                    AppUtilityMethods.showMsg(getContext(), "Please select one wallet from above to transfer coins", false);
                } else if (mOnChangeDOBListener != null) {
                    if (mWallet.equalsIgnoreCase("deposit")) {
                        mTransferCoins = mTransferCoins * 10;
                    } else {
                        mTransferCoins = mTransferCoins * 2;
                    }
                    mOnChangeDOBListener.onSilverCoinsTransfer(mTransferCoins, mWallet);
                }
                break;
            case R.id.btn_cancel:
                setCancelable(true);
                break;
        }
    }

}
