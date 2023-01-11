package com.khiladiadda.withdrawcoins;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.khiladiadda.R;
import com.khiladiadda.dialogs.interfaces.IOnWithdrawOtpListener;
import com.khiladiadda.utility.AppUtilityMethods;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WithdrawOTPDialog extends Dialog implements View.OnClickListener, View.OnKeyListener {

    @BindView(R.id.btn_send) Button mSendBTN;
    @BindView(R.id.btn_cancel) Button mCancelBTN;
    @BindView(R.id.et_one) EditText mOneET;
    @BindView(R.id.et_two) EditText mTwoET;
    @BindView(R.id.et_three) EditText mThreeET;
    @BindView(R.id.et_four) EditText mFourET;
    @BindView(R.id.et_five) EditText mFiveET;
    @BindView(R.id.et_six) EditText mSixET;
    @BindView(R.id.tv_resend) TextView mResendTV;

    private List<EditText> mEditTexts;
    private final IOnWithdrawOtpListener mOnWithdrawOtpListener;
    private final Context mContext;

    public WithdrawOTPDialog(@NonNull Context context, IOnWithdrawOtpListener onWithdrawOtpListener) {
        super(context);
        this.mContext = context;
        this.mOnWithdrawOtpListener = onWithdrawOtpListener;
        init();
    }

    private void init() {
        initDialog();
        initViews();
        show();
    }

    private void initDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_withdraw_otp);
        getWindow().setBackgroundDrawableResource(R.color.colorTransparent);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    private void initViews() {
        ButterKnife.bind(this);
        mCancelBTN.setOnClickListener(this);
        mSendBTN.setOnClickListener(this);
        mResendTV.setOnClickListener(this);
        mEditTexts = Arrays.asList(mOneET, mTwoET, mThreeET, mFourET, mFiveET, mSixET);
        for (int i = 0; i < mEditTexts.size(); i++) {
            EditText editText = mEditTexts.get(i);
            editText.addTextChangedListener(new OtpTextWatcher(i));
            editText.setOnKeyListener(this);
        }
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                cancel();
                break;
            case R.id.btn_send:
                String mOtp = mOneET.getText().toString().trim() + mTwoET.getText().toString().trim() + mThreeET.getText().toString().trim() + mFourET.getText().toString().trim() + mFiveET.getText().toString().trim() + mSixET.getText().toString().trim();
                if (TextUtils.isEmpty(mOtp)) {
                    AppUtilityMethods.showMsg(mContext, mContext.getString(R.string.text_empty_otp), false);
                } else if (mOtp.length() < 6) {
                    AppUtilityMethods.showMsg(mContext, mContext.getString(R.string.text_valid_otp), false);
                } else {
                    mOnWithdrawOtpListener.onVerifyOtpTransferListener(mOtp);
                    cancel();
                }
                break;
            case R.id.tv_resend:
                mOnWithdrawOtpListener.onResendOtpListener();
                break;
        }
    }

    @Override public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            EditText editText = (EditText) v;
            int editTextIndex = mEditTexts.indexOf(editText);
            if (editTextIndex > 0 && editText.getText().toString().length() == 0) {
                mEditTexts.get(editTextIndex - 1).requestFocus();
                mEditTexts.get(editTextIndex - 1).setCursorVisible(true);
                return true;
            }
            return false;
        }
        return false;
    }

    private class OtpTextWatcher implements TextWatcher {

        private int mEditTextIndex;

        private OtpTextWatcher(int editTextIndex) {
            mEditTextIndex = editTextIndex;
        }

        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mEditTextIndex < 5) {
                if (s.length() == 1) {
                    mEditTexts.get(mEditTextIndex).clearFocus();
                    mEditTexts.get(mEditTextIndex + 1).requestFocus();
                    mEditTexts.get(mEditTextIndex + 1).setCursorVisible(true);
                }
            }
        }

        @Override public void afterTextChanged(Editable s) {
        }
    }

}