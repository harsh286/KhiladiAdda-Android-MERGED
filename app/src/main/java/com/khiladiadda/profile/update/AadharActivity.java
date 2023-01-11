package com.khiladiadda.profile.update;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.interfaces.IOnFilterChallengeListener;
import com.khiladiadda.dialogs.interfaces.IOnWithdrawVerifyListener;
import com.khiladiadda.help.HelpActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.SurepassResponse;
import com.khiladiadda.network.model.response.ProfileResponse;
import com.khiladiadda.network.model.response.UploadImageResponse;
import com.khiladiadda.network.model.response.WIthdrawLimitResponse;
import com.khiladiadda.profile.update.interfaces.IUpdateProfilePresenter;
import com.khiladiadda.profile.update.interfaces.IUpdateProfileView;
import com.khiladiadda.network.model.response.AadharCaptchaResponse;
import com.khiladiadda.network.model.response.AadharDetailsResponse;
import com.khiladiadda.network.model.response.VerifiBaseResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.withdrawcoins.NewWithdrawActivity;
import com.khiladiadda.withdrawcoins.WithdrawReVerifyDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import butterknife.BindView;

public class AadharActivity extends BaseActivity implements IUpdateProfileView, View.OnKeyListener {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    //Aadhar Details
    @BindView(R.id.tv_aadhar_hint)
    TextView mAadharHintTV;
    @BindView(R.id.iv_captcha)
    ImageView mCaptchaIV;
    @BindView(R.id.et_captcha)
    EditText mCaptchaET;
    @BindView(R.id.et_aadhaar_number)
    EditText mAadharNumberET;
    @BindView(R.id.btn_update_aadhar)
    Button mUpdateAadharBTN;
    //Otp Details
    @BindView(R.id.tv_otp_hint)
    TextView mOtpHintTV;
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
    @BindView(R.id.ll_otp)
    LinearLayout mOtpLL;
    @BindView(R.id.btn_confirm_otp)
    Button mConfirmOtpBTN;
    @BindView(R.id.ll_profile)
    LinearLayout mProfileLL;
    //Manual Details
    @BindView(R.id.tv_manual_hint)
    TextView mManualHintTV;
    @BindView(R.id.btn_aadhar_manual)
    Button mAdharManualBTN;
    @BindView(R.id.tv_resend)
    TextView mResendTV;
    @BindView(R.id.tv_quiz_timer)
    TextView mQuizTimerTV;
    @BindView(R.id.tv_new_captcha)
    TextView mNewCaptchaTV;
    @BindView(R.id.btn_query)
    Button mQueryBTN;

    private IUpdateProfilePresenter mPresenter;
    private List<EditText> mEditTexts;
    private String mUID;
    private String mRequestId;
    private String mShareCode;
    Handler handler = new Handler();
    private final Handler mHandlerTimer = new Handler();
    private static final int COUNTDOWN_TIME = 120;
    private int mTimerValue = COUNTDOWN_TIME;
    private int mFrom = 1; //1. sendOTP, 2.resendOTP
    private int mCaptchaCount, mAadharVerifiedVia;
    private int mFromActivity;
    private String mBeneficiaryId;

    @Override
    protected int getContentView() {
        return R.layout.activity_aadhar;
    }

    @Override
    protected void initViews() {
        mEditTexts = Arrays.asList(mOneET, mTwoET, mThreeET, mFourET, mFiveET, mSixET);
        mActivityNameTV.setText(R.string.text_update_aadhar_details);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setVisibility(View.GONE);
        mUpdateAadharBTN.setOnClickListener(this);
        mConfirmOtpBTN.setOnClickListener(this);
        mAdharManualBTN.setOnClickListener(this);
        mResendTV.setOnClickListener(this);
        mNewCaptchaTV.setOnClickListener(this);
        mQueryBTN.setOnClickListener(this);

        mFromActivity = getIntent().getIntExtra(AppConstant.FROM, 0);
        if (mFrom == AppConstant.WITHDRAW_VERIFICATION) {
            mBeneficiaryId = getIntent().getStringExtra(AppConstant.ID);
        }
    }

    @Override
    protected void initVariables() {
        mPresenter = new UpdateProfilePresenter(this);
        for (int i = 0; i < mEditTexts.size(); i++) {
            EditText editText = mEditTexts.get(i);
            editText.addTextChangedListener(new AadharActivity.OtpTextWatcher(i));
            editText.setOnKeyListener(this);
        }
        checkAadharVerifyVia();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_update_aadhar:
                sendOTP();
                break;
            case R.id.btn_confirm_otp:
                String mOtp = mOneET.getText().toString().trim() + mTwoET.getText().toString().trim() + mThreeET.getText().toString().trim() + mFourET.getText().toString().trim() + mFiveET.getText().toString().trim() + mSixET.getText().toString().trim();
                showProgress(getString(R.string.txt_progress_authentication));
                if (mAadharVerifiedVia == 1) {
                    mPresenter.verifyAadhaarOtp(mRequestId, mUID, mOtp, mShareCode, 1);
                } else {
                    mPresenter.verifyAadhaarOtp("", "", mOtp, "", 2);
                }
                break;
            case R.id.btn_aadhar_manual:
                Intent aadhar = new Intent(this, AadharManualActivity.class);
                startActivity(aadhar);
                finish();
                break;
            case R.id.tv_resend:
                mFrom = 2;
                mTimerValue = COUNTDOWN_TIME;
                mResendTV.setVisibility(View.GONE);
                mQuizTimerTV.setVisibility(View.GONE);
                sendOTP();
                break;
            case R.id.tv_new_captcha:
                if (mCaptchaCount > 2) {
                    mNewCaptchaTV.setEnabled(false);
                    mNewCaptchaTV.setText(R.string.text_exhausted_captcha);
                } else {
                    generateHashForCaptcha();
                }
                break;
            case R.id.btn_query:
                Intent help = new Intent(this, HelpActivity.class);
                startActivity(help);
                finish();
                break;
        }
    }

    private void checkAadharVerifyVia() {
        showProgress(getString(R.string.txt_progress_authentication));
        mPresenter.getWithdrawLimit();
    }

    private void sendOTP() {
        if (mAadharVerifiedVia == 1) {
            if (TextUtils.isEmpty(mAadharNumberET.getText().toString().trim())) {
                AppUtilityMethods.showMsg(this, "Please provide aadhar number as printed on Aadhar card", true);
            } else if (TextUtils.isEmpty(mCaptchaET.getText().toString().trim())) {
                AppUtilityMethods.showMsg(this, "Please provide captcha as shown above.", true);
            } else {
                showProgress(getString(R.string.txt_progress_authentication));
                if (mFrom == 1) {
                    mPresenter.checkAadhar(mAadharNumberET.getText().toString().trim());
                } else {
                    mPresenter.getAadhaarOtp(mRequestId, mUID, mAadharNumberET.getText().toString().trim(), mCaptchaET.getText().toString().trim());
                }
            }
        } else {
            if (TextUtils.isEmpty(mAadharNumberET.getText().toString().trim())) {
                AppUtilityMethods.showMsg(this, "Please provide aadhar number as printed on Aadhar card", true);
            } else {
                showProgress(getString(R.string.txt_progress_authentication));
                mPresenter.checkAadhar(mAadharNumberET.getText().toString().trim());
            }
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getEmailAddress() {
        return null;
    }

    @Override
    public String getMobile() {
        return null;
    }

    @Override
    public String getGender() {
        return null;
    }

    @Override
    public String getState() {
        return null;
    }

    @Override
    public String getCountry() {
        return null;
    }

    @Override
    public void onValidationComplete() {
    }

    @Override
    public void onValidationFailure(String errorMsg) {
    }

    @Override
    public void onUploadImageComplete(UploadImageResponse responseModel) {
    }

    @Override
    public void onUploadImageFailure(ApiError error) {
    }

    @Override
    public void onUpdateProfileComplete(ProfileResponse responseModel) {
    }

    @Override
    public void onUpdateProfileFailure(ApiError error) {
    }

    @Override
    public void onUpdatePANComplete(ProfileResponse responseModel) {
    }

    @Override
    public void onUpdatePANFailure(ApiError error) {
    }

    @Override
    public void onAadharVerifyViaComplete(WIthdrawLimitResponse response) {
        hideProgress();
        mAadharVerifiedVia = response.getResponse().getAadharVerifiedVia();
        if (mAadharVerifiedVia == 1) {
            if (new NetworkStatus(this).isInternetOn()) {
                mProfileLL.setVisibility(View.VISIBLE);
                generateHashForCaptcha();
            } else {
                mProfileLL.setVisibility(View.GONE);
                Snackbar.make(mActivityNameTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
            }
        } else if (mAadharVerifiedVia == 2) {
            mCaptchaET.setVisibility(View.GONE);
            mCaptchaIV.setVisibility(View.GONE);
            mNewCaptchaTV.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAadharVerifyViaFailed(ApiError error) {
        hideProgress();
        Snackbar.make(mActivityNameTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckAdharComplete(BaseResponse responseModel) {
        if (mAadharVerifiedVia == 1) {
            if (responseModel.isStatus()) {
                mPresenter.getAadhaarOtp(mRequestId, mUID, mAadharNumberET.getText().toString().trim(), mCaptchaET.getText().toString().trim());
            } else {
                hideProgress();
                AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
            }
        } else {
            hideProgress();
            if (responseModel.isStatus()) {
                Snackbar.make(mAdharManualBTN, "OTP sent on your mobile number", Snackbar.LENGTH_LONG).show();
                aadharDetailsVisibility();
                mQuizTimerTV.setVisibility(View.VISIBLE);
                startUpdateTimer();
            } else {
                AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
            }
        }
    }

    @Override
    public void onCheckAadharFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onGetCaptchaComplete(AadharCaptchaResponse responseModel) {
        hideProgress();
        if (responseModel.getResponseStatus().getCode().equalsIgnoreCase("000")) {
            mCaptchaCount++;
            String base64Image = responseModel.getResponseData().getCaptcha();
            if (!TextUtils.isEmpty(base64Image)) {
                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                mCaptchaIV.setImageBitmap(decodedByte);
                mUID = responseModel.getResponseData().getUuid();
            }
        } else {
            Snackbar.make(mUpdateAadharBTN, "We are not able to fetch Captcha. Please check your internet connection and try again or contact our support.", Snackbar.LENGTH_SHORT).show();
//            handler.postDelayed(runnable, 5000);
        }
    }

    @Override
    public void onGetCaptchaFailure(ApiError error) {
        hideProgress();
        Snackbar.make(mUpdateAadharBTN, "We are not able to fetch Captcha. Please check your internet connection and try again or contact our support.", Snackbar.LENGTH_SHORT).show();
//        verifyAadharManually();
    }

    @Override
    public void onGetAadhaarOtpComplete(VerifiBaseResponse responseModel) {
        hideProgress();
        if (responseModel.getResponseStatus().getCode().equalsIgnoreCase("000")) {
            aadharDetailsVisibility();
            if (mFrom == 1) {
                mQuizTimerTV.setVisibility(View.VISIBLE);
            }
            mNewCaptchaTV.setVisibility(View.GONE);
            startUpdateTimer();
        } else {
//            handler.postDelayed(runnable, 25000);
            AppUtilityMethods.showMsg(this, responseModel.getResponseStatus().getMessage(), false);
        }
    }

    @Override
    public void onGetAadhaarOtpFailure(ApiError error) {
        hideProgress();
        AppUtilityMethods.showMsg(this, getString(R.string.text_internet_issue), false);
//        verifyAadharManually();
    }

    @Override
    public void onVerifyAadhaarOtpComplete(VerifiBaseResponse responseModel) {
        if (responseModel.getResponseStatus().getCode().equalsIgnoreCase("000")) {
            getKycHash();
        } else {
            hideProgress();
            AppUtilityMethods.showMsg(this, responseModel.getResponseStatus().getMessage(), false);
        }
    }

    @Override
    public void onVerifyAadhaarOtpFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onGetAadhaarKYCComplete(AadharDetailsResponse responseModel) {
        if (responseModel.getResponseStatus().getCode().equalsIgnoreCase("000")) {
            try {
                mPresenter.updateAadhaarServer(responseModel.getResponseData().getName(), "", "",
                        responseModel.getResponseData().getAddress(),
                        mAppPreference.getEmail(),
                        responseModel.getResponseData().getGender(),
                        responseModel.getResponseData().getDob(), mAadharNumberET.getText().toString().trim(),
                        responseModel.getResponseData().getMotherName(),
                        responseModel.getResponseData().getFatherName(), 2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            hideProgress();
            AppUtilityMethods.showMsg(this, responseModel.getResponseStatus().getMessage(), false);
        }
    }

    @Override
    public void onGetAadhaarKYCFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onUpdateAadharComplete(ProfileResponse responseModel) {
        mAppPreference.setProfileData(responseModel.getResponse());
        if (mFromActivity == AppConstant.WITHDRAW_VERIFICATION) {
            mPresenter.verifyBeneficiary(String.valueOf(mBeneficiaryId));
        } else {
            hideProgress();
            if (responseModel.isStatus()) {
                showMsg(responseModel.getMessage());
            } else {
                AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
            }
        }
    }

    @Override
    public void onUpdateAadhaarFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onVerifyAadhaarOtpSurepassComplete(ProfileResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            mAppPreference.setProfileData(responseModel.getResponse());
            showMsg("!!!Thank You!!!\nYour Aadhaar verification has been completed.\nYou can now withdraw the maximum limit.");
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override
    public void onVerifyAadhaarOtpSurepassFailure(ApiError error) {
        hideProgress();
        Snackbar.make(mActivityNameTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onVerifyBeneficiaryComplete(BaseResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            AppUtilityMethods.showMsg(this, "!!!Thank You!!!\nYour bank details and aadhar card details has matched. Now you can withdraw amount easily.", false);
        } else {
            showMsg("!!!Thank You!!!\nYour Aadhaar verification has been completed.\nYou can now withdraw the maximum limit.");
//            new WithdrawReVerifyDialog(this, onWithdrawListener, 2, mBeneficiaryId,"","");
        }
    }

    @Override
    public void onVerifyBeneficiaryFailure(ApiError error) {
        hideProgress();
    }

    Runnable runnable = this::verifyAadharManually;

    //generate hash
    //get captcha
    //display captcha
    //getOtp
    //verifyOtp
    //get aadhar Details
    private void getKycHash() {
        try {
            String hashValue = calculateHash(AppConstant.AADHAR_CLIENT_ID, mUID, AppConstant.AADHAR_API_KEY, AppConstant.AADHAR_SALT);
            mPresenter.getAadhaarKYC(mRequestId, mUID, hashValue);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void generateHashForCaptcha() {
        final int random = new Random().nextInt(1000000) + 20;
        mRequestId = String.valueOf(random);
        String uuid = UUID.randomUUID().toString();
        mShareCode = uuid.substring(0, 4);
        try {
            String hashValue = calculateHash(AppConstant.AADHAR_CLIENT_ID, mRequestId, AppConstant.AADHAR_API_KEY, AppConstant.AADHAR_SALT);
            getCaptcha(mRequestId, hashValue);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private String calculateHash(String clientCode, String requestId, String apiKey, String
            salt) throws NoSuchAlgorithmException {
        MessageDigest digest;
        digest = MessageDigest.getInstance("SHA-256");
        if (digest != null) {
            byte[] hash = digest.digest((clientCode + "|" + requestId + "|" + apiKey + "|" + salt).getBytes());
            return bytesToHex(hash);
        }
        return null;
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private void getCaptcha(String request_id, String hashValue) {
        showProgress(getString(R.string.txt_progress_authentication));
        mPresenter.getCaptcha(request_id, hashValue);
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

        private final int mEditTextIndex;

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

    public void showMsg(String msg) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(arg0 -> {
            dialog.dismiss();
            finish();
        });
        dialog.show();
    }

    //Timer
    public void startUpdateTimer() {
        mHandlerTimer.postDelayed(mTimerRunnable, 1000);
    }

    public void stopTimer() {
        mHandlerTimer.removeCallbacksAndMessages(null);
    }

    private final Runnable mTimerRunnable = new Runnable() {
        @Override
        public void run() {
            if (mFrom == 1) {
                if (mTimerValue < 1) {
                    stopTimer();
                    mQuizTimerTV.setVisibility(View.GONE);
                    mResendTV.setVisibility(View.VISIBLE);
//                    verifyAadharManually();
                } else {
                    mQuizTimerTV.setText("Resend OTP in " + mTimerValue + "s");
                    mTimerValue--;
                    startUpdateTimer();
                }
            }
        }
    };

    private void aadharDetailsVisibility() {
        mOtpHintTV.setVisibility(View.VISIBLE);
        mOtpLL.setVisibility(View.VISIBLE);
        mConfirmOtpBTN.setVisibility(View.VISIBLE);
        mCaptchaET.setEnabled(false);
        mAadharNumberET.setEnabled(false);
        mUpdateAadharBTN.setEnabled(false);
    }

    private void verifyAadharManually() {
        mManualHintTV.setVisibility(View.VISIBLE);
        mAdharManualBTN.setVisibility(View.VISIBLE);
        mOtpHintTV.setVisibility(View.GONE);
        mOtpLL.setVisibility(View.GONE);
        mConfirmOtpBTN.setVisibility(View.GONE);
        mCaptchaET.setEnabled(false);
        mAadharNumberET.setEnabled(false);
        mUpdateAadharBTN.setEnabled(false);
    }

}