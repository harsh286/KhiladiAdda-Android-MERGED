package com.khiladiadda.forgotpassword;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.forgotpassword.interfaces.IForgetPasswordPresenter;
import com.khiladiadda.forgotpassword.interfaces.IForgetPasswordView;
import com.khiladiadda.login.LoginActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.response.OtpResponse;
import com.khiladiadda.otp.service.SmsBroadcastReceiver;
import com.khiladiadda.profile.ProfileActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.utility.PermissionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

public class ForgotPasswordActivity extends BaseActivity implements IForgetPasswordView, View.OnKeyListener {

    @BindView(R.id.et_mobile) EditText mMobileET;
    @BindView(R.id.btn_send_otp) Button mSendOtpBTN;
    @BindView(R.id.tv_help) TextView mHelpTV;
    @BindView(R.id.tv_mobile) TextView mMobileTV;
    @BindView(R.id.et_one) EditText mOneET;
    @BindView(R.id.et_two) EditText mTwoET;
    @BindView(R.id.et_three) EditText mThreeET;
    @BindView(R.id.et_four) EditText mFourET;
    @BindView(R.id.et_five) EditText mFiveET;
    @BindView(R.id.et_six) EditText mSixET;
    @BindView(R.id.tv_resend_otp) TextView mResendOTPTV;
    @BindView(R.id.btn_confirm_otp) Button mConfirmOTPBTN;
    @BindView(R.id.et_password) EditText mPasswordET;
    @BindView(R.id.et_confirm_password) EditText mConfirmPasswordET;
    @BindView(R.id.btn_change_password) Button mChangePasswordBTN;
    @BindView(R.id.tv_needsupport) TextView mNeedSupportTV;
    @BindView(R.id.ll_need_support) LinearLayout mNeedSupportLL;

    private IForgetPasswordPresenter mPresenter;
    private String mFrom, mOtp;
    private List<EditText> mEditTexts;
    private SmsBroadcastReceiver smsBroadcastReceiver;


    @Override protected int getContentView() {
        return R.layout.activity_forgot_password;
    }

    @Override protected void initViews() {
        mEditTexts = Arrays.asList(mOneET, mTwoET, mThreeET, mFourET, mFiveET, mSixET);
        mFrom = getIntent().getStringExtra(AppConstant.FROM);
        SpannableString needsupportString = new SpannableString(mNeedSupportTV.getText().toString());
        needsupportString.setSpan(new StyleSpan(Typeface.BOLD), 10, needsupportString.length(), 0);
        needsupportString.setSpan(new UnderlineSpan(), 0, 12, 0);
        mNeedSupportTV.setText(needsupportString);
        PermissionUtils.hasSMSReadPermission(this);
    }

    @Override protected void initVariables() {
        mSendOtpBTN.setOnClickListener(this);
        mResendOTPTV.setOnClickListener(this);
        mConfirmOTPBTN.setOnClickListener(this);
        mChangePasswordBTN.setOnClickListener(this);
        mNeedSupportLL.setOnClickListener(this);
        mPresenter = new ForgetPasswordPresenter(this);
        for (int i = 0; i < mEditTexts.size(); i++) {
            EditText editText = mEditTexts.get(i);
            editText.addTextChangedListener(new OtpTextWatcher(i));
            editText.setOnKeyListener(this);
        }
    }

    @Override public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_otp:
                mPresenter.validateData();
                break;
            case R.id.tv_resend_otp:
                if (new NetworkStatus(this).isInternetOn()) {
                    showProgress(getString(R.string.txt_progress_authentication));
                    mPresenter.sendOtp(mMobileET.getText().toString());
                } else {
                    Snackbar.make(mSendOtpBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_confirm_otp:
                mOtp = mOneET.getText().toString().trim() + mTwoET.getText().toString().trim() + mThreeET.getText().toString().trim() + mFourET.getText().toString().trim() + mFiveET.getText().toString().trim() + mSixET.getText().toString().trim();
                if (TextUtils.isEmpty(mOtp)) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_otp_empty), false);
                    return;
                }
                if (mOtp.length() < 6) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_valid_otp), false);
                    return;
                }
                if (new NetworkStatus(this).isInternetOn()) {
                    showProgress(getString(R.string.txt_progress_authentication));
                    if (mFrom.equalsIgnoreCase(AppConstant.FROM_UPDATE_MOBILE)) {
                        mPresenter.verifyMobile(mMobileET.getText().toString().trim(), mOtp);
                    } else {
                        mPresenter.verifyOtp(mMobileET.getText().toString().trim(), mOtp);
                    }
                } else {
                    Snackbar.make(mSendOtpBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_change_password:
                String mPassword = mPasswordET.getText().toString();
                String mConfirmPassword = mConfirmPasswordET.getText().toString();
                if (TextUtils.isEmpty(mPassword)) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_password_empty), false);
                    return;
                }
                if (mPassword.length() < 8 || mPassword.length() > 15) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_pwd_length), false);
                    return;
                }
                if (!AppUtilityMethods.isValidPassword(mPassword)) {
                    onValidationFailure(getString(R.string.text_pwd_validator));
                    return;
                }
                if (TextUtils.isEmpty(mConfirmPassword)) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_confirm_pwd_empty), false);
                    return;
                }
                if (!mPassword.equals(mConfirmPassword)) {
                    AppUtilityMethods.showMsg(this, getString(R.string.text_pwd_not_match), false);
                    return;
                }
                if (new NetworkStatus(this).isInternetOn()) {
                    showProgress(getString(R.string.txt_progress_authentication));
                    mPresenter.resetPassword(mMobileET.getText().toString(), mOtp, mPassword);
                } else {
                    Snackbar.make(mSendOtpBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_need_support:
                openWhatsappContact();
                break;
        }
    }

    @Override public String getMobileNumber() {
        return mMobileET.getText().toString();
    }

    @Override public void onValidationComplete() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            if (mFrom.equalsIgnoreCase(AppConstant.FROM_UPDATE_MOBILE)) {
                mPresenter.sendOtpVerify(mMobileET.getText().toString().trim());
            } else {
                mPresenter.sendOtp(mMobileET.getText().toString().trim());
            }
        } else {
            Snackbar.make(mSendOtpBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override public void onValidationFailure(String errorMsg) {
        AppUtilityMethods.showMsg(this, errorMsg, false);
    }

    @Override public void onSendOtpComplete(BaseResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            setData();
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override public void onSendOtpFailure(ApiError error) {
        hideProgress();
        AppUtilityMethods.showMsg(this, error.getMessage(), false);
    }

    @Override public void onVerifyOtpComplete(BaseResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            mMobileET.setEnabled(false);
            mHelpTV.setEnabled(false);
            mMobileTV.setEnabled(false);
            mOneET.setEnabled(false);
            mTwoET.setEnabled(false);
            mThreeET.setEnabled(false);
            mFourET.setEnabled(false);
            mFiveET.setEnabled(false);
            mSixET.setEnabled(false);
            mConfirmOTPBTN.setEnabled(false);
            mPasswordET.setVisibility(View.VISIBLE);
            mConfirmPasswordET.setVisibility(View.VISIBLE);
            mChangePasswordBTN.setVisibility(View.VISIBLE);
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override public void onVerifyOtpFailure(ApiError error) {
        hideProgress();
        AppUtilityMethods.showMsg(this, error.getMessage(), false);
    }

    @Override public void onResetPasswordComplete(BaseResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            AppUtilityMethods.showChangePassword(this, getString(R.string.text_pwd_changed), false);
        }
    }

    @Override public void onResetPasswordFailure(ApiError error) {
        hideProgress();
        AppUtilityMethods.showMsg(this, error.getMessage(), false);
    }

    @Override public void onSendOtpVerifyComplete(OtpResponse responseModel) {
        hideProgress();
        setData();
    }

    @Override public void onSendOtpVerifyFailure(ApiError error) {
        hideProgress();
        AppUtilityMethods.showMsg(this, error.getMessage(), false);
    }

    @Override public void onVerifyMobileComplete(OtpResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            mAppPreference.setSessionToken(responseModel.getJwt());
            AppUtilityMethods.showMsgCancel(this, getString(R.string.text_verify_mobile_complete), false);
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override public void onVerifyMobileFailure(ApiError error) {
        hideProgress();
        AppUtilityMethods.showMsg(this, error.getMessage(), false);
    }

    private void setData() {
        mSendOtpBTN.setText(getString(R.string.text_resend_otp));
        mHelpTV.setVisibility(View.VISIBLE);
        mMobileTV.setText(mMobileET.getText().toString());
        mMobileTV.setVisibility(View.VISIBLE);
        mOneET.setVisibility(View.VISIBLE);
        mTwoET.setVisibility(View.VISIBLE);
        mThreeET.setVisibility(View.VISIBLE);
        mFourET.setVisibility(View.VISIBLE);
        mFiveET.setVisibility(View.VISIBLE);
        mSixET.setVisibility(View.VISIBLE);
        mConfirmOTPBTN.setVisibility(View.VISIBLE);
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        if (mFrom.equalsIgnoreCase(AppConstant.FROM_FORGOT_PASSWORD)) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if (mFrom.equalsIgnoreCase(AppConstant.FROM_UPDATE_MOBILE)) {
            startActivity(new Intent(this, ProfileActivity.class));
        }
        finish();
    }

    @Override protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
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

    @Override public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == AppConstant.RC_ASK_PERMISSIONS_MSG) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.txt_read_sms_permission), Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                String upToNCharacters = message.substring(0, Math.min(message.length(), 6));
                updateOtp(upToNCharacters);
            }
        }
    };

    private void updateOtp(String message) {
        if (message.length() < 6) {
            return;
        }
        for (int i = 0; i < mEditTexts.size(); i++) {
            mEditTexts.get(i).setText(String.valueOf(message.charAt(i)));
        }
    }

    @Override public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
    void openWhatsappContact() {
        String url = "https://wa.me/91"+mAppPreference.getSupportNumber() + "" + "?text=Hello%20I%20have%20a%20problem";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void startSmsUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        //We can add sender phone number or leave it blank
        // I'm adding null here

        client.startSmsUserConsent(null).addOnSuccessListener(new OnSuccessListener<Void>() {


            @Override
            public void onSuccess(Void aVoid) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if ((resultCode == RESULT_OK) && (data != null)) {
                //That gives all message to us.
                // We need to get the code from inside with regex
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                getOtpFromMessage(message);
            }
        }
    }

    private String[] breakOtpNumber(int number) {
        String[] arr = String.valueOf(number).split("(?<=.)");
        return String.valueOf(number).split("(?<=.)");
    }
    private void getOtpFromMessage(String message) {
        // This will match any 6 digit number in the message
        Pattern pattern = Pattern.compile("(|^)\\d{6}");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            Log.e("OTP Text", "getOtpFromMessage: " + matcher.group(0));
            String[] num = breakOtpNumber(Integer.parseInt(Objects.requireNonNull(matcher.group(0))));
            mOneET.setText(num[0]);
            mTwoET.setText(num[1]);
            mThreeET.setText(num[2]);
            mFourET.setText(num[3]);
            mFiveET.setText(num[4]);
            mSixET.setText(num[5]);

            doCursorAligment();

        }
    }

    private void registerBroadcastReceiver() {
        smsBroadcastReceiver = new SmsBroadcastReceiver();
        smsBroadcastReceiver.smsBroadcastReceiverListener =
                new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, 200);
                    }

                    @Override
                    public void onFailure() {
                    }
                };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);

    }

    private void doCursorAligment() {
        if (mSixET.getText() != null)
            mSixET.setSelection(mSixET.getText().length());
        if (mFiveET.getText() != null)
            mFiveET.setSelection(mSixET.getText().length());
        if (mFourET.getText() != null)
            mFourET.setSelection(mSixET.getText().length());
        if (mThreeET.getText() != null)
            mThreeET.setSelection(mSixET.getText().length());
        if (mTwoET.getText() != null)
            mTwoET.setSelection(mSixET.getText().length());
        if (mOneET.getText() != null)
            mOneET.setSelection(mSixET.getText().length());
    }

}