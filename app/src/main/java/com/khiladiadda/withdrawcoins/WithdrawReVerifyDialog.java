package com.khiladiadda.withdrawcoins;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.khiladiadda.R;
import com.khiladiadda.dialogs.interfaces.IOnWithdrawVerifyListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WithdrawReVerifyDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.tv_help)
    TextView mHelpTV;
    @BindView(R.id.btn_reverify_aadhar)
    Button mReVerifyAadharBTN;
    @BindView(R.id.btn_reverify_beneficiary)
    Button mReVerifyBeneficiaryBTN;
    @BindView(R.id.btn_cancel)
    Button mCancelBTN;
    @BindView(R.id.tv_bank_name)
    TextView mBankNameTV;
    @BindView(R.id.tv_aadhar_name)
    TextView mAadharNameTV;

    private final Context mContext;
    private IOnWithdrawVerifyListener mOnWithdrawListener;
    private int mFrom;
    private String mId, mAadharName, mBankName;

    public WithdrawReVerifyDialog(@NonNull Context context, IOnWithdrawVerifyListener onWithdrawListener, int from, String id, String aadharName, String bankName) {
        super(context);
        this.mContext = context;
        this.mOnWithdrawListener = onWithdrawListener;
        this.mFrom = from;
        this.mId = id;
        this.mAadharName = aadharName;
        this.mBankName = bankName;
        init();
    }

    private void init() {
        initDialog();
        initViews();
        show();
    }

    private void initDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_withdraw_reverify);
        getWindow().setBackgroundDrawableResource(R.color.colorTransparent);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    private void initViews() {
        ButterKnife.bind(this);
        mCancelBTN.setOnClickListener(this);
        mReVerifyAadharBTN.setOnClickListener(this);
        mReVerifyBeneficiaryBTN.setOnClickListener(this);
        mBankNameTV.setText("Name\n" + mBankName);
        mAadharNameTV.setText("Name\n" + mAadharName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reverify_aadhar:
                if (mFrom == 2) {
                    cancel();
                } else {
                    cancel();
                    mOnWithdrawListener.onAadharVerify(mId);
                }
                break;
            case R.id.btn_reverify_beneficiary:
                cancel();
                mOnWithdrawListener.onWithdrawVerify();
                break;
            case R.id.btn_cancel:
                cancel();
                break;
        }
    }

}