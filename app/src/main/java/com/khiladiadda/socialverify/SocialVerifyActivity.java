package com.khiladiadda.socialverify;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import com.google.firebase.messaging.FirebaseMessaging;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.login.LoginActivity;
import com.khiladiadda.main.MainActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.request.FBRegisterRequest;
import com.khiladiadda.network.model.request.GmailRegisterRequest;
import com.khiladiadda.network.model.response.MasterResponse;
import com.khiladiadda.network.model.response.OtpResponse;
import com.khiladiadda.network.model.response.SocialResponse;
import com.khiladiadda.socialverify.interfaces.ISocialVerifyPresenter;
import com.khiladiadda.socialverify.interfaces.ISocialVerifyView;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.utility.PermissionUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class SocialVerifyActivity extends BaseActivity implements ISocialVerifyView, View.OnKeyListener {

    @BindView(R.id.et_mobile)
    EditText mMobileET;
    @BindView(R.id.et_reference_code)
    EditText mInviteCodeET;
    @BindView(R.id.btn_send_otp)
    Button mSendOtpBTN;
    @BindView(R.id.tv_help)
    TextView mHelpTV;
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
    @BindView(R.id.tv_resend_otp)
    TextView mResendOTPTV;
    @BindView(R.id.btn_confirm_otp)
    Button mConfirmOTPBTN;

    private ISocialVerifyPresenter mPresenter;
    private int mExitCount;
    private String mFrom, mFBToken;
    private GmailRegisterRequest mGmailRequest;
    private List<EditText> mEditTexts;
    private String mSocialLogin;

    @Override
    protected int getContentView() {
        return R.layout.activity_social_verify;
    }

    @Override
    protected void initViews() {
        mEditTexts = Arrays.asList(mOneET, mTwoET, mThreeET, mFourET, mFiveET, mSixET);
        PermissionUtils.hasSMSReadPermission(this);
        getFirebaseId();
    }

    @Override
    protected void initVariables() {
        mFrom = getIntent().getStringExtra(AppConstant.FROM);
        if (mFrom.equalsIgnoreCase(AppConstant.FROM_GMAIL)) {
            mGmailRequest = getIntent().getParcelableExtra(AppConstant.DATA);
            mSocialLogin = "GoogleLogin";
        } else {
            mFBToken = getIntent().getStringExtra(AppConstant.DATA);
            mSocialLogin="FBLogin";
        }
        mPresenter = new SocialVerifyPresenter(this);
        mSendOtpBTN.setOnClickListener(this);
        mResendOTPTV.setOnClickListener(this);
        mConfirmOTPBTN.setOnClickListener(this);
        for (int i = 0; i < mEditTexts.size(); i++) {
            EditText editText = mEditTexts.get(i);
            editText.addTextChangedListener(new OtpTextWatcher(i));
            editText.setOnKeyListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_otp:
                mPresenter.validateData();
                break;
            case R.id.tv_resend_otp:
                if (new NetworkStatus(this).isInternetOn()) {
                    showProgress(getString(R.string.txt_progress_authentication));
                    mPresenter.resendOtp(mMobileET.getText().toString());
                } else {
                    Snackbar.make(mSendOtpBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_confirm_otp:
                String mOtp = mOneET.getText().toString().trim() + mTwoET.getText().toString().trim() + mThreeET.getText().toString().trim() + mFourET.getText().toString().trim() + mFiveET.getText().toString().trim() + mSixET.getText().toString().trim();
                if (TextUtils.isEmpty(mOtp)) {
                    AppUtilityMethods.showMsg(this, "Please provide OTP send to above mobile number.", false);
                    return;
                }
                if (mOtp.length() < 6) {
                    AppUtilityMethods.showMsg(this, "Please provide valid OTP (6 digit).", false);
                    return;
                }
                if (new NetworkStatus(this).isInternetOn()) {
                    showProgress(getString(R.string.txt_progress_authentication));
                    mPresenter.verifyOtp(mMobileET.getText().toString().trim(), mOtp);
                } else {
                    Snackbar.make(mSendOtpBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public String getMobileNumber() {
        return mMobileET.getText().toString().trim();
    }

    @Override
    public void onValidationComplete() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            if (mFrom.equalsIgnoreCase(AppConstant.FROM_GMAIL)) {
                mGmailRequest.setMobile(mMobileET.getText().toString().trim());
                mGmailRequest.setInviteCode(mInviteCodeET.getText().toString().trim());
                mGmailRequest.setPlatform(AppConstant.ANDORID);
                mGmailRequest.setDeviceId(mAppPreference.getString(AppConstant.UUID, ""));
                mGmailRequest.setToken(mAppPreference.getDeviceToken());
                mPresenter.doGmailRegister(mGmailRequest);
            } else {
                FBRegisterRequest request = new FBRegisterRequest(mFBToken, mMobileET.getText().toString().trim(), mInviteCodeET.getText().toString().trim());
                mPresenter.doFbRegister(request);
            }
        } else {
            Snackbar.make(mSendOtpBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onValidationFailure(String errorMsg) {
        AppUtilityMethods.showMsg(this, errorMsg, false);
    }

    @Override
    public void onGmailLoginComplete(SocialResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            setData();
            AppUtilityMethods.showMsg(this, getString(R.string.text_otp_send_successfully), false);
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override
    public void onGmailLoginFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onFbLoginComplete(SocialResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            setData();
            AppUtilityMethods.showMsg(this, getString(R.string.text_otp_send_successfully), false);
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override
    public void onFbLoginFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onVerifyOtpComplete(OtpResponse responseModel) {
        if (responseModel.isStatus()) {
            mAppPreference.setSessionToken(responseModel.getJwt());
            mPresenter.getMasterData();
            Map<String, Object> eventParameters1 = new HashMap<>();
            eventParameters1.put(AFInAppEventParameterName.REGSITRATION_METHOD, mSocialLogin);
            AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AFInAppEventType.COMPLETE_REGISTRATION, eventParameters1);
        } else {
            hideProgress();
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override
    public void onVerifyOtpFailure(ApiError error) {
        hideProgress();
        AppUtilityMethods.showMsg(this, error.getMessage(), false);
    }

    @Override
    public void onResendOtpComplete(OtpResponse responseModel) {
        hideProgress();
        AppUtilityMethods.showMsg(this, getString(R.string.text_otp_send_successfully), false);
    }

    @Override
    public void onResendOtpFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onMasterComplete(MasterResponse responseModel) {
        AppUtilityMethods.saveMasterData(responseModel);
        hideProgress();
        showMsg(this, getString(R.string.txt_social_register), false);
    }

    @Override
    public void onMasterFailure(ApiError error) {
        hideProgress();
    }

    public void showMsg(final Context activity, String msg, boolean isCancel) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.setContentView(R.layout.popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            mAppPreference.setIsLogin(true);
            startActivity(new Intent(SocialVerifyActivity.this, MainActivity.class));
            finish();
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (mExitCount == 0) {
            mExitCount++;
            Snackbar.make(mSendOtpBTN, "Please proceed to register yourself or press again to exit.", Snackbar.LENGTH_LONG).show();
        } else {
            startActivity(new Intent(SocialVerifyActivity.this, LoginActivity.class));
            finish();
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }

    private void setData() {
        mHelpTV.setVisibility(View.VISIBLE);
        mOneET.setVisibility(View.VISIBLE);
        mTwoET.setVisibility(View.VISIBLE);
        mThreeET.setVisibility(View.VISIBLE);
        mFourET.setVisibility(View.VISIBLE);
        mFiveET.setVisibility(View.VISIBLE);
        mSixET.setVisibility(View.VISIBLE);
        mResendOTPTV.setVisibility(View.VISIBLE);
        mConfirmOTPBTN.setVisibility(View.VISIBLE);
    }

    private void getFirebaseId() {
//        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
//            if (!task.isSuccessful()) {
//                return;
//            }
//            mAppPreference.setDeviceToken(task.getResult().getToken());
//            if (!mAppPreference.getIsUUID()) {
//                mAppPreference.setString(AppConstant.UUID, AppUtilityMethods.getDeviceUniqueID(this));
//                mAppPreference.setIsUUID(true);
//            }
//        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        return;
                    }
                    mAppPreference.setDeviceToken(task.getResult());
                    if (!mAppPreference.getIsUUID()) {
                        mAppPreference.setString(AppConstant.UUID, AppUtilityMethods.getDeviceUniqueID(this));
                        mAppPreference.setIsUUID(true);
                    }
                });
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == AppConstant.RC_ASK_PERMISSIONS_MSG) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.txt_read_sms_permission), Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
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

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

}