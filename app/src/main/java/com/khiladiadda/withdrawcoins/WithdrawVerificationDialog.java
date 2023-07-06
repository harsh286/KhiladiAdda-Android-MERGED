package com.khiladiadda.withdrawcoins;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.khiladiadda.R;
import com.khiladiadda.profile.update.AadharActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WithdrawVerificationDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.tv_name)
    TextView mNameTV;
    @BindView(R.id.tv_bank)
    TextView mBankTV;
    @BindView(R.id.tv_ifsc)
    TextView mIfscTV;
    @BindView(R.id.tv_help)
    TextView mHelpTV;
    @BindView(R.id.cb_verification)
    CheckBox mVerificationCB;
    @BindView(R.id.btn_cancel)
    Button mCancelBTN;
    @BindView(R.id.btn_proceed)
    Button mProceedBTN;

    private final Context mContext;
    private String mMessage, mName, mBank, mIfsc, mId;

    public WithdrawVerificationDialog(@NonNull Context context, String message, String name, String bank, String ifsc, String id) {
        super(context);
        this.mContext = context;
        this.mMessage = message;
        this.mName = name;
        this.mBank = bank;
        this.mIfsc = ifsc;
        this.mId = id;
        init();
    }

    private void init() {
        initDialog();
        initViews();
        show();
    }

    private void initDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_withdraw_verification);
        getWindow().setBackgroundDrawableResource(R.color.colorTransparent);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }
    private void initViews() {
        ButterKnife.bind(this);
        mProceedBTN.setOnClickListener(this);
        mCancelBTN.setOnClickListener(this);

        mNameTV.setText("Name: " + mName);
        mBankTV.setText("Bank/Wallet/UPI: " + mBank);
        mIfscTV.setText("IFSC/UPI: " + mIfsc);
        mHelpTV.setText(mMessage);

        SpannableString ss1 = new SpannableString(mNameTV.getText().toString().trim());
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 6, ss1.length(), 0);
        ss1.setSpan(new ForegroundColorSpan(mNameTV.getContext().getResources().getColor(R.color.colorBlack)), 6, ss1.length(), 0);// set color
        mNameTV.setText(ss1);

        SpannableString ss2 = new SpannableString(mBankTV.getText().toString().trim());
        ss2.setSpan(new StyleSpan(Typeface.BOLD), 15, ss2.length(), 0);
        ss2.setSpan(new ForegroundColorSpan(mBankTV.getContext().getResources().getColor(R.color.colorBlack)), 7, ss2.length(), 0);// set color
        mBankTV.setText(ss2);

        SpannableString ss3 = new SpannableString(mIfscTV.getText().toString().trim());
        ss3.setSpan(new StyleSpan(Typeface.BOLD), 9, ss3.length(), 0);
        ss3.setSpan(new ForegroundColorSpan(mIfscTV.getContext().getResources().getColor(R.color.colorBlack)), 6, ss3.length(), 0);// set color
        mIfscTV.setText(ss3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                cancel();
                break;
            case R.id.btn_proceed:
                if (mVerificationCB.isChecked()) {
                    Intent i = new Intent(mContext, AadharActivity.class);
                    i.putExtra(AppConstant.FROM, AppConstant.WITHDRAW_VERIFICATION);
                    i.putExtra(AppConstant.ID, mId);
                    mContext.startActivity(i);
                    cancel();
                } else {
                    AppUtilityMethods.showMsg(mContext, "Please check the rules : I have read the information", false);
                }
                break;
        }
    }

}