package com.khiladiadda.profile;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.dialogs.interfaces.IOnChangePasswordListener;
import com.khiladiadda.dialogs.interfaces.IOnVerifyEmailListener;
import com.khiladiadda.otp.OtpActivity;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmailDialog extends Dialog implements View.OnClickListener, View.OnKeyListener {

    private final IOnVerifyEmailListener mOnVerifEmailListener;
    private final Context mContext;
    private String mOtp;

    @BindView(R.id.et_email)
    EditText mEmailET;
    @BindView(R.id.btn_send_otp)
    Button mSendOtpBTN;
    @BindView(R.id.et_one)
    EditText mOneET;
    @BindView(R.id.et_two)
    EditText mTwoET;
    @BindView(R.id.et_three)
    EditText mThreeET;
    @BindView(R.id.et_four)
    EditText mFourET;
    @BindView(R.id.et_five)
    EditText mFiveET;
    @BindView(R.id.et_six)
    EditText mSixET;
    @BindView(R.id.tv_resend)
    TextView mResendTV;
    @BindView(R.id.btn_verify)
    Button mVerifyBTN;
    @BindView(R.id.ll_otp)
    LinearLayout mOTPLL;
    @BindView(R.id.rl_close)
    RelativeLayout mCloseRL;
    @BindView(R.id.ll_email)
    LinearLayout mEmailLL;
    @BindView(R.id.tv_email)
    TextView mEmailTV;


    private List<EditText> mEditTexts;
    private int mFrom;
    private String mEmail;
    private boolean mError;

    public EmailDialog(@NonNull Context context, IOnVerifyEmailListener onVerifEmailListener, int from, String email, boolean error) {
        super(context);
        this.mContext = context;
        this.mOnVerifEmailListener = onVerifEmailListener;
        this.mFrom = from;
        this.mEmail = email;
        this.mError = error;
        init();
    }

    private void init() {
        initDialog();
        initViews();
        show();
    }

    private void initDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_email);
        getWindow().setBackgroundDrawableResource(R.color.colorTransparent);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    private void initViews() {
        ButterKnife.bind(this);
        mSendOtpBTN.setOnClickListener(this);
        mResendTV.setOnClickListener(this);
        mVerifyBTN.setOnClickListener(this);
        mCloseRL.setOnClickListener(this);
        mEditTexts = Arrays.asList(mOneET, mTwoET, mThreeET, mFourET, mFiveET, mSixET);
        for (int i = 0; i < mEditTexts.size(); i++) {
            EditText editText = mEditTexts.get(i);
            editText.addTextChangedListener(new OtpTextWatcher(i));
            editText.setOnKeyListener(this);
        }

        if (mFrom == 1) {
            mOTPLL.setVisibility(View.GONE);
        } else {
            mOTPLL.setVisibility(View.VISIBLE);
            mEmailET.setText(mEmail);
            mEmailTV.setText(mEmail);
            mEmailLL.setVisibility(View.GONE);
            if (mError) {
                //   AppUtilityMethods.showMsg(getContext(),"Please Provide Correct OTP",false);
                Toast.makeText(getContext(), "Please Provide Correct OTP", Toast.LENGTH_SHORT).show();
//                Snackbar.make(mOTPLL, R.string.error_internet, Snackbar.LENGTH_SHORT).show();

            }
        }

    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_resend:
                if (new NetworkStatus(mContext).isInternetOn()) {
//                    showProgress(getString(R.string.txt_progress_authentication));
//                    mPresenter.resendOtp(mMobileNumber);
                    dismiss();
                    mOnVerifEmailListener.onResendOTP(mEmailET.getText().toString().trim());
                } else {
                    Snackbar.make(mVerifyBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_send_otp:
                if (AppUtilityMethods.isEmailValidator(mEmailET.getText().toString().trim())) {
                    mOnVerifEmailListener.onSendOTP(mEmailET.getText().toString().trim());
                    cancel();
                } else {
                    Snackbar.make(mOTPLL, "Please provide valid email address", Snackbar.LENGTH_SHORT).show();

//                    Toast.makeText(getContext(), "Please provide valid email address", Toast.LENGTH_SHORT).show();
                    //   AppUtilityMethods.showMsg(mContext, "Please provide valid email address", false);
                }
                break;
            case R.id.btn_verify:
                mOtp = mOneET.getText().toString().trim() + mTwoET.getText().toString().trim() + mThreeET.getText().toString().trim() + mFourET.getText().toString().trim() + mFiveET.getText().toString().trim() + mSixET.getText().toString().trim();
                if (mOtp.length() == 6) {
                    mOnVerifEmailListener.onVerifyEmail(mEmailET.getText().toString().trim(), mOtp);
                    cancel();
                } else {
                    //AppUtilityMethods.showMsg(mContext, "Please provide valid otp", false);
                    Toast.makeText(getContext(), "Please Provide Correct OTP", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl_close:
                dismiss();
                break;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
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

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mEditTextIndex < 5) {
                if (s.length() == 1) {
                    mEditTexts.get(mEditTextIndex).clearFocus();
                    mEditTexts.get(mEditTextIndex + 1).requestFocus();
                    mEditTexts.get(mEditTextIndex + 1).setCursorVisible(true);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

}