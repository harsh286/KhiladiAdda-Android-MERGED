package com.khiladiadda.otp;

import static android.os.Build.VERSION.SDK_INT;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.login.LoginActivity;
import com.khiladiadda.main.MainActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.LoginResponse;
import com.khiladiadda.network.model.response.MasterResponse;
import com.khiladiadda.network.model.response.OtpResponse;
import com.khiladiadda.otp.interfaces.IOtpPresenter;
import com.khiladiadda.otp.interfaces.IOtpView;
import com.khiladiadda.otp.service.SmsBroadcastReceiver;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.registration.RegistrationActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.utility.PermissionUtils;
import com.moengage.core.Properties;
import com.moengage.core.analytics.MoEAnalyticsHelper;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import butterknife.BindView;

public class OtpActivity extends BaseActivity implements IOtpView, View.OnKeyListener {

    @BindView(R.id.btn_register)
    Button mRegisterBTN;
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
    @BindView(R.id.tv_mobile)
    TextView mMobileTV;
    @BindView(R.id.ll_need_support)
    LinearLayout mNeedSupportLL;
    @BindView(R.id.tv_changenumber)
    TextView mChangeNumberTV;

    private boolean back;
    private IOtpPresenter mPresenter;
    private String mMobileNumber, mOtp;
    private List<EditText> mEditTexts;
    private int mFrom;

    private Activity activity;
    private SmsBroadcastReceiver smsBroadcastReceiver;


    @Override
    protected int getContentView() {
        return R.layout.activity_otp;
    }

    @Override
    protected void initViews() {
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        mRegisterBTN.setOnClickListener(this);
        mResendTV.setOnClickListener(this);
        mChangeNumberTV.setOnClickListener(this);
        mNeedSupportLL.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        activity = this;
        mPresenter = new OtpPresenter(this);
        mEditTexts = Arrays.asList(mOneET, mTwoET, mThreeET, mFourET, mFiveET, mSixET);
        mFrom = getIntent().getIntExtra(AppConstant.FROM, 0);
        mMobileNumber = getIntent().getStringExtra(AppConstant.MobileNumber);
        mMobileTV.setText(mMobileNumber);
        SpannableString needsupportString = new SpannableString(mResendTV.getText().toString());
        needsupportString.setSpan(new UnderlineSpan(), 0, 10, 0);
        mResendTV.setText(needsupportString);
        SpannableString ChangeNumberString = new SpannableString(mChangeNumberTV.getText().toString());
        ChangeNumberString.setSpan(new UnderlineSpan(), 0, 13, 0);
        mChangeNumberTV.setText(ChangeNumberString);
        if (mFrom == AppConstant.FROM_LOGIN) {
            mRegisterBTN.setText(R.string.text_verify_login);
        }
        for (int i = 0; i < mEditTexts.size(); i++) {
            EditText editText = mEditTexts.get(i);
            editText.addTextChangedListener(new OtpTextWatcher(i));
            editText.setOnKeyListener(this);
        }
        startSmsUserConsent();

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                if (SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
                        mPresenter.validateData();
                    } else {
                        AppUtilityMethods.showStoragePermisisionMsg(this, "", false);
                    }
                } else {
                    if (PermissionUtils.hasStoragePermission(this)) {
                        mPresenter.validateData();
                    } else {
                        AppUtilityMethods.showStoragePermisisionMsg(this, "", false);
                    }
                }
                break;
            case R.id.tv_resend:
                if (new NetworkStatus(this).isInternetOn()) {
                    showProgress(getString(R.string.txt_progress_authentication));
                    mPresenter.resendOtp(mMobileNumber);
                } else {
                    Snackbar.make(mRegisterBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_need_support:
                openWhatsappContact();
                break;
            case R.id.tv_changenumber:
                onBackPressed();
                break;
        }
    }

    @Override
    public String getOtp() {
        mOtp = mOneET.getText().toString().trim() + mTwoET.getText().toString().trim() + mThreeET.getText().toString().trim() + mFourET.getText().toString().trim() + mFiveET.getText().toString().trim() + mSixET.getText().toString().trim();
        return mOtp;
    }

    @Override
    public void onValidationComplete() {
        if (new NetworkStatus(this).isInternetOn()) {
            back = true;
            showProgress(getString(R.string.txt_progress_authentication));
            if (mFrom == AppConstant.FROM_REGISTRATION_OTP) {
                mPresenter.doVerifyOtp(mMobileNumber, mOtp);
            } else {
                mPresenter.doVerifyLoginOtp(getIntent().getStringExtra(AppConstant.MobileNumber), getIntent().getStringExtra(AppConstant.PAST), mOtp);
            }
        } else {
            Snackbar.make(mRegisterBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onValidationFailure(String errorMsg) {
        AppUtilityMethods.showMsg(this, errorMsg, false);
    }

    @Override
    public void onVerifyOtpComplete(OtpResponse responseModel) {
        if (responseModel.isStatus()) {
            mPresenter.getMasterData();
            mAppPreference.setSessionToken(responseModel.getJwt());
            mAppPreference.setIsLogin(true);
            Map<String, Object> eventParameters1 = new HashMap<>();
            eventParameters1.put(AFInAppEventParameterName.REGSITRATION_METHOD, "SignUp");
            AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AFInAppEventType.COMPLETE_REGISTRATION, eventParameters1);
            MoEAnalyticsHelper.INSTANCE.setUserAttribute(this, "signedUpOn", new Date());
        } else {
            hideProgress();
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
            back = false;
        }
    }

    @Override
    public void onVerifyOtpFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onResendOtpComplete(OtpResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            Snackbar.make(mRegisterBTN, R.string.text_otp_send_successfully, Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(mRegisterBTN, responseModel.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResendOtpFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onVerifyLoginOtpComplete(LoginResponse responseModel) {
        if (responseModel.isStatus()) {
            mAppPreference.setSessionToken(responseModel.getJwt());
            mAppPreference.setIsLogin(true);
            mPresenter.getMasterData();
            MoEAnalyticsHelper.INSTANCE.setUniqueId(this, responseModel.getJwt());
            Properties properties = new Properties();
            if (mFrom == AppConstant.FROM_REGISTRATION_OTP) {
                MoEAnalyticsHelper.INSTANCE.trackEvent(this, "SignupUser", properties);
            } else {
                MoEAnalyticsHelper.INSTANCE.trackEvent(this, "LoginUser", properties);
            }
            AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AFInAppEventType.LOGIN, null, new AppsFlyerRequestListener() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError(int i, @NonNull String s) {
                }
            });
        } else {
            hideProgress();
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
            back = false;
        }
    }

    @Override
    public void onVerifyLoginOtpFailure(ApiError error) {
        hideProgress();
        AppUtilityMethods.showMsg(this, error.getMessage(), false);
        back = false;
    }

    @Override
    public void onMasterComplete(MasterResponse responseModel) {
        try {
            AppUtilityMethods.saveMasterData(responseModel);
            appsFlyer("direct", AppSharedPreference.getInstance().getMobile());
            MoEAnalyticsHelper.INSTANCE.setUniqueId(this, mAppPreference.getProfileData().getId());
            MoEAnalyticsHelper.INSTANCE.setFirstName(this, mAppPreference.getProfileData().getName());
            MoEAnalyticsHelper.INSTANCE.setUserName(this, mAppPreference.getProfileData().getUsername());
            MoEAnalyticsHelper.INSTANCE.setMobileNumber(this, String.valueOf(mAppPreference.getProfileData().getMobile()));
            MoEAnalyticsHelper.INSTANCE.setEmailId(this, mAppPreference.getProfileData().getEmail());
        } finally {
            hideProgress();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onMasterFailure(ApiError error) {
        hideProgress();
    }

    @Override
    protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (!back) {
            Intent i = new Intent(this, RegistrationActivity.class);
            if (mFrom == AppConstant.FROM_LOGIN) {
                i = new Intent(this, LoginActivity.class);
            }
            startActivity(i);
            finish();
            super.onBackPressed();
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

    void openWhatsappContact() {
        String url = "https://wa.me/91" + mAppPreference.getSupportNumber() + "" + "?text=Hello%20I%20have%20a%20problem";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void appsFlyer(String socialName, String mMobileNumber) {
        if (mFrom == AppConstant.FROM_REGISTRATION_OTP)
            AppUtilityMethods.appFlyersLoginEvent(this, socialName, "SIGNUP", mMobileNumber);
        else
            AppUtilityMethods.appFlyersLoginEvent(this, socialName, "LOGIN", mMobileNumber);
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