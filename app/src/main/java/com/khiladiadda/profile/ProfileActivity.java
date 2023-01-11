package com.khiladiadda.profile;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.clashroyale.ClashRoyaleActivity;
import com.khiladiadda.dialogs.interfaces.IOnChangeDOBListener;
import com.khiladiadda.dialogs.interfaces.IOnChangePasswordListener;
import com.khiladiadda.dialogs.interfaces.IOnVerifyEmailListener;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.forgotpassword.ForgotPasswordActivity;
import com.khiladiadda.league.LeagueActivity;
import com.khiladiadda.ludo.LudoChallengeActivity;
import com.khiladiadda.main.MainActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.BaseResponse;
import com.khiladiadda.network.model.request.GameCredential;
import com.khiladiadda.network.model.response.ProfileDetails;
import com.khiladiadda.network.model.response.ProfileResponse;
import com.khiladiadda.network.model.response.ProfileTransactionResponse;
import com.khiladiadda.profile.interfaces.IProfilePresenter;
import com.khiladiadda.profile.interfaces.IProfileView;
import com.khiladiadda.profile.update.AadharActivity;
import com.khiladiadda.profile.update.PanActivity;
import com.khiladiadda.profile.update.UpdateProfileActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.khiladiadda.utility.PermissionUtils;
import com.moengage.core.analytics.MoEAnalyticsHelper;
import com.moengage.inapp.MoEInAppHelper;
import com.moengage.widgets.NudgeView;

import java.util.List;

import butterknife.BindView;

public class ProfileActivity extends BaseActivity implements IProfileView {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.iv_profile)
    ImageView mProfileIV;
    @BindView(R.id.tv_name)
    TextView mNameTV;
    @BindView(R.id.tv_id)
    TextView mIdTV;
    @BindView(R.id.tv_email)
    TextView mEmailTV;
    @BindView(R.id.tv_mobile)
    TextView mMobileTV;
    @BindView(R.id.tv_update_mobile)
    TextView mUpdateMobileTV;
    @BindView(R.id.tv_country)
    TextView mCountryTV;
    @BindView(R.id.iv_edit)
    ImageView mEditIV;
    @BindView(R.id.tv_change_pwd)
    TextView mChangePwdTV;
    @BindView(R.id.tv_dob)
    TextView mDobTV;
    @BindView(R.id.tv_pan)
    TextView mPanTV;
    @BindView(R.id.tv_aadhar)
    TextView mAadharTV;
    @BindView(R.id.tv_change_dob)
    TextView mChangeDobTV;
    @BindView(R.id.tv_invite_code)
    TextView mInviteCodeTV;
    @BindView(R.id.tv_change_pan)
    TextView mChangePanTV;
    @BindView(R.id.tv_change_aadhar)
    TextView mChangeAadharTV;
    @BindView(R.id.tv_profile_percentage)
    TextView mProfilePercentTV;
    @BindView(R.id.pb_profile_progress)
    ProgressBar mProfileProgressPB;
    @BindView(R.id.tv_profile_complete_msg)
    TextView mProfilePercentMsgTV;
    @BindView(R.id.cv_credential)
    CardView mCredentialCV;
    @BindView(R.id.rl_pubg)
    RelativeLayout mPubgRL;
    @BindView(R.id.rl_pubg_lite)
    RelativeLayout mPubgLiteRL;
    @BindView(R.id.rl_freefire)
    RelativeLayout mFreeFireRL;
    @BindView(R.id.rl_ludo)
    RelativeLayout mLudoRL;
    @BindView(R.id.rl_clashroyale)
    RelativeLayout mClaashRoyaleRL;
    @BindView(R.id.rl_ff_clash)
    RelativeLayout mFFClashRL;
    @BindView(R.id.rl_cod)
    RelativeLayout mCodRL;
    @BindView(R.id.tv_pubg)
    TextView mPubGTV;
    @BindView(R.id.tv_pubg_lite)
    TextView mPubGLiteTV;
    @BindView(R.id.tv_freefire)
    TextView mFreeFireTV;
    @BindView(R.id.tv_ff_clash)
    TextView mFFClashTV;
    @BindView(R.id.tv_ludo)
    TextView mLudoTV;
    @BindView(R.id.btn_play_pubg)
    Button mPlayPubGBTN;
    @BindView(R.id.btn_play_pubg_lite)
    Button mPlayPubGLiteBTN;
    @BindView(R.id.btn_play_ff)
    Button mPlayFFBTN;
    @BindView(R.id.btn_play_ff_clash)
    Button mPlayFFClashBTN;
    @BindView(R.id.btn_play_cod)
    Button mPlayCodBTN;
    @BindView(R.id.btn_play_clash_royale)
    Button mPlayCRBTN;
    @BindView(R.id.btn_play_ludo)
    Button mPlayLudoBTN;
    @BindView(R.id.tv_share)
    TextView mShareInviteCodeTV;
    @BindView(R.id.tv_update_email)
    TextView mUpdateEmailTV;
    @BindView(R.id.tv_u_email)
    TextView mShowEmailTV;
    @BindView(R.id.rl_pubg_global)
    RelativeLayout mPubglobal;
    @BindView(R.id.tv_pubg_global)
    TextView mPubglobalTV;
    @BindView(R.id.rl_esports_per)
    RelativeLayout mEsprtsPermium;
    @BindView(R.id.tv_esports_per)
    TextView mEsportsPermiumTV;
    @BindView(R.id.btn_esports_per)
    AppCompatButton mEsportsPermiumBTN;
    @BindView(R.id.btn_pubg_global)
    AppCompatButton mPubglobalBTN;
    @BindView(R.id.rl_ff_max)
    RelativeLayout mFFMAX;
    @BindView(R.id.tv_ff_max)
    TextView mFFMaxTV;
    @BindView(R.id.btn_ff_max)
    AppCompatButton mFFMaxBTN;
    @BindView(R.id.nudge)
    NudgeView mNV;

    private IProfilePresenter mPresenter;
    private String mNewEmail;

    @Override
    protected int getContentView() {
        return R.layout.activity_profile;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mNV.initialiseNudgeView(this);
        MoEInAppHelper.getInstance().showInApp(this);
    }

    @Override
    protected void initViews() {
        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            String redirect = intent.getString(AppConstant.PushFrom);
            if (redirect.equalsIgnoreCase(AppConstant.MoEngage)) {
                mAppPreference.setIsDeepLinking(true);
            }
        }
        mActivityNameTV.setText(R.string.my_profile);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mUpdateMobileTV.setOnClickListener(this);
        mChangePwdTV.setOnClickListener(this);
        mChangeDobTV.setOnClickListener(this);
        mChangePanTV.setOnClickListener(this);
        mChangeAadharTV.setOnClickListener(this);
        mEditIV.setOnClickListener(this);
        mShareInviteCodeTV.setOnClickListener(this);
        mPlayPubGBTN.setOnClickListener(this);
        mPlayPubGLiteBTN.setOnClickListener(this);
        mPlayFFBTN.setOnClickListener(this);
        mPlayCodBTN.setOnClickListener(this);
        mPlayCRBTN.setOnClickListener(this);
        mPlayLudoBTN.setOnClickListener(this);
        mPlayFFClashBTN.setOnClickListener(this);
        mUpdateEmailTV.setOnClickListener(this);
        mEsportsPermiumBTN.setOnClickListener(this);
        mPubglobalBTN.setOnClickListener(this);
        mFFMaxBTN.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mPresenter = new ProfilePresenter(this);
    }

    private void getProfile() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getProfile();
        } else {
            setProfileData();
            Snackbar.make(mBackIV, getString(R.string.error_internet), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                if (mAppPreference.getIsDeepLinking()) {
                    finish();
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    finish();
                }
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.tv_change_pan:
                Intent pan = new Intent(this, PanActivity.class);
                startActivity(pan);
                break;
            case R.id.tv_change_aadhar:
                Intent aadhar = new Intent(this, AadharActivity.class);
                aadhar.putExtra(AppConstant.FROM, AppConstant.FROM_PAYU);
                startActivity(aadhar);
                break;
            case R.id.tv_update_mobile:
                Intent addMobileNumber = new Intent(this, ForgotPasswordActivity.class);
                addMobileNumber.putExtra(AppConstant.FROM, AppConstant.FROM_UPDATE_MOBILE);
                startActivity(addMobileNumber);
                finish();
                break;
            case R.id.iv_edit:
                if (!PermissionUtils.hasStoragePermission(this)) {
                    Snackbar.make(mPanTV, R.string.txt_allow_permission, Snackbar.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(this, UpdateProfileActivity.class));
                    finish();
                }
                break;
            case R.id.tv_share:
                AppUtilityMethods.shareInviteCode(this);
                break;
            case R.id.btn_play_pubg:
                Intent pubgLite = new Intent(this, LeagueActivity.class);
                pubgLite.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_BGMI);
                startActivity(pubgLite);
                break;
            case R.id.btn_play_pubg_lite:
                Intent pubg = new Intent(this, LeagueActivity.class);
                pubg.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_TDM);
                startActivity(pubg);
                break;
            case R.id.btn_play_ff:
                Intent ff = new Intent(this, LeagueActivity.class);
                ff.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_FREEFIRE);
                ff.putExtra(AppConstant.FROM_TYPE, AppConstant.FREEFIRE_SOLO);
                startActivity(ff);
                break;
            case R.id.btn_play_ff_clash:
                Intent ffClash = new Intent(this, LeagueActivity.class);
                ffClash.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_FF_CLASH);
                ffClash.putExtra(AppConstant.FROM_TYPE, AppConstant.FF_CLASH_SOLO);
                startActivity(ffClash);
                break;
            case R.id.btn_play_cod:
                Intent cod = new Intent(this, LeagueActivity.class);
                cod.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_CALLOFDUTY);
                startActivity(cod);
                break;
            case R.id.btn_play_clash_royale:
                startActivity(new Intent(this, ClashRoyaleActivity.class));
                break;
            case R.id.btn_play_ludo:
                Intent ludo = new Intent(this, LudoChallengeActivity.class);
                ludo.putExtra(AppConstant.CONTEST_TYPE, AppConstant.TYPE_LUDO);
                startActivity(ludo);
                break;
            case R.id.tv_update_email:
                new EmailDialog(this, onVerifyEmailAddressListener, 1, "", false);
                break;
            case R.id.btn_pubg_global:
                Intent pubglobal = new Intent(this, LeagueActivity.class);
                pubglobal.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_PUBG_GLOBAL);
                pubglobal.putExtra(AppConstant.FROM_TYPE, AppConstant.PUBG_GLOBAL_SOLO);
                startActivity(pubglobal);
                break;
            case R.id.btn_esports_per:
                Intent esp = new Intent(this, LeagueActivity.class);
                esp.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_PREMIUM_ESPORTS);
                esp.putExtra(AppConstant.FROM_TYPE, AppConstant.PREMIUM_ESPORTS_SOLO);
                startActivity(esp);
                break;
            case R.id.btn_ff_max:
                Intent i = new Intent(this, LeagueActivity.class);
                i.putExtra(AppConstant.FROM, AppConstant.FROM_VIEW_FF_MAX);
                i.putExtra(AppConstant.FROM_TYPE, AppConstant.FF_MAX_SOLO);
                startActivity(i);
                break;

        }
    }

    @Override
    public void onUpdatePasswordComplete(BaseResponse responseModel) {
        hideProgress();
        mAppPreference.setIsProfile(false);
        AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
    }

    @Override
    public void onUpdatePasswordFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onUpdateDOBComplete(ProfileResponse responseModel) {
        hideProgress();
        showMsg(responseModel.getMessage());
    }

    @Override
    public void onUpdateDOBFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onProfileComplete(ProfileTransactionResponse responseModel) {
        hideProgress();
        mAppPreference.setIsProfile(true);
        mAppPreference.setProfileData(responseModel.getResponse());
        setProfileData();
        setUserDataMoEngage();
    }

    private void setCredentials(List<GameCredential> credentialList) {
        if (credentialList != null && credentialList.size() > 0) {
            mCredentialCV.setVisibility(View.VISIBLE);
            for (int i = 0; i < credentialList.size(); i++) {
                if (credentialList.get(i).getGameId().equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_LITE_ID, ""))) {
                    mPubgRL.setVisibility(View.VISIBLE);
                    mPubGTV.setText(credentialList.get(i).getGameUsername() + " - " + credentialList.get(i).getGameCharacterId());
                } else if (credentialList.get(i).getGameId().equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_ID, ""))) {
                    mPubgLiteRL.setVisibility(View.VISIBLE);
                    mPubGLiteTV.setText(credentialList.get(i).getGameUsername() + " - " + credentialList.get(i).getGameCharacterId());
                } else if (credentialList.get(i).getGameId().equalsIgnoreCase(mAppPreference.getString(AppConstant.FREEFIRE_ID, ""))) {
                    mFreeFireRL.setVisibility(View.VISIBLE);
                    mFreeFireTV.setText(credentialList.get(i).getGameUsername() + " - " + credentialList.get(i).getGameCharacterId());
                } else if (credentialList.get(i).getGameId().equalsIgnoreCase(mAppPreference.getString(AppConstant.LUDO_ID, ""))) {
                    mLudoRL.setVisibility(View.VISIBLE);
                    mLudoTV.setText(credentialList.get(i).getGameCharacterId());
                } else if (credentialList.get(i).getGameId().equalsIgnoreCase(mAppPreference.getString(AppConstant.CLASHROYALE_ID, ""))) {
                    mClaashRoyaleRL.setVisibility(View.VISIBLE);
                    //mClashRoyaleTV.setText(credentialList.get(i).getGameUsername() + " - " + credentialList.get(i).getGameCharacterId());
                } else if (credentialList.get(i).getGameId().equalsIgnoreCase(mAppPreference.getString(AppConstant.CALL_DUTY_ID, ""))) {
                    mCodRL.setVisibility(View.VISIBLE);
                    // mCodTV.setText(credentialList.get(i).getGameUsername());
                } else if (credentialList.get(i).getGameId().equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_CLASH_ID, ""))) {
                    mFFClashRL.setVisibility(View.VISIBLE);
                    mFFClashTV.setText(credentialList.get(i).getGameUsername() + " - " + credentialList.get(i).getGameCharacterId());
                } else if (credentialList.get(i).getGameId().equalsIgnoreCase(mAppPreference.getString(AppConstant.PUBG_GLOBAL_ID, ""))) {
                    mPubglobal.setVisibility(View.VISIBLE);
                    mPubglobalTV.setText(credentialList.get(i).getGameUsername() + " - " + credentialList.get(i).getGameCharacterId());
                } else if (credentialList.get(i).getGameId().equalsIgnoreCase(mAppPreference.getString(AppConstant.PREMIUM_ESPORTS_ID, ""))) {
                    mEsprtsPermium.setVisibility(View.VISIBLE);
                    mEsportsPermiumTV.setText(credentialList.get(i).getGameUsername() + " - " + credentialList.get(i).getGameCharacterId());
                } else if (credentialList.get(i).getGameId().equalsIgnoreCase(mAppPreference.getString(AppConstant.FF_MAX_ID, ""))) {
                    mFFMAX.setVisibility(View.VISIBLE);
                    mFFMaxTV.setText(credentialList.get(i).getGameUsername() + " - " + credentialList.get(i).getGameCharacterId());
                }
            }
        }
    }

    @Override
    public void onProfileFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onApplyVoucherComplete(BaseResponse responseModel) {

    }

    @Override
    public void onApplyVoucherFailure(ApiError error) {

    }

    @Override
    public void onSendOtpComplete(BaseResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            new EmailDialog(this, onVerifyEmailAddressListener, 2, mNewEmail, false);
        } else {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
        }
    }

    @Override
    public void onSendOtpFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onVerifyEmailComplete(BaseResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            AppUtilityMethods.showMsg(this, responseModel.getMessage(), false);
            getProfile();
        } else {
            new EmailDialog(this, onVerifyEmailAddressListener, 2, mNewEmail, true);
        }
    }

    @Override
    public void onVerifyEmailFailure(ApiError error) {
        hideProgress();
    }

    private void setProfileData() {
        ProfileDetails mProfileDetails = mAppPreference.getProfileData();
        mAppPreference.setEmail(mProfileDetails.getEmail());
        mAppPreference.setName(mProfileDetails.getName());
        mAppPreference.setUrl(mProfileDetails.getDp());
        mAppPreference.setInviteCode(mProfileDetails.getInvitecode());
        int mProgress = 0;
        mProgress = mProgress + 30;
        mMobileTV.setText(mAppPreference.getMobile());
        mUpdateMobileTV.setText(getString(R.string.text_verified));
        mUpdateMobileTV.setEnabled(false);
        mUpdateMobileTV.setTextColor(getResources().getColor(R.color.color_green));
        if (!TextUtils.isEmpty(mAppPreference.getEmail())) {
            mEmailTV.setText(mAppPreference.getEmail());
            mShowEmailTV.setText(mAppPreference.getEmail());
        }
        if (!TextUtils.isEmpty(mAppPreference.getName())) {
            mNameTV.setText(mAppPreference.getName());
            mProgress = mProgress + 10;
        }
        if (!TextUtils.isEmpty(mAppPreference.getUrl())) {
            mProgress = mProgress + 20;
            Glide.with(this).load(mAppPreference.getUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(mProfileIV);
        }
        if (!TextUtils.isEmpty(mAppPreference.getInviteCode())) {
            mInviteCodeTV.setText(mAppPreference.getInviteCode());
        }
        if (TextUtils.isEmpty(mProfileDetails.getUsername()) || mProfileDetails.getUsername().startsWith("8888888888")) {
            mIdTV.setText(getString(R.string.text_update_username));
        } else {
            mProgress = mProgress + 20;
            mIdTV.setText("Username: " + mProfileDetails.getUsername());
        }
        if (mProfileDetails.getAadharUpdated() == 4) {
            mChangeAadharTV.setText(getString(R.string.text_rejected));
            mChangeAadharTV.setTextColor(getResources().getColor(R.color.battle_red));
            mChangeAadharTV.setEnabled(true);
            mAadharTV.setText(mProfileDetails.getAadharDetails().getRejectReason());
        } else if (mProfileDetails.getAadharUpdated() == 3) {
            mChangeAadharTV.setText(getString(R.string.text_verified));
            mChangeAadharTV.setEnabled(false);
            mChangeAadharTV.setTextColor(getResources().getColor(R.color.color_green));
            mAadharTV.setText(R.string.password_hide);
            mChangeDobTV.setText(getString(R.string.text_verified));
            mChangeDobTV.setEnabled(false);
            mChangeDobTV.setTextColor(getResources().getColor(R.color.color_green));
            mCountryTV.setText(mProfileDetails.getAddress());
            mProgress = mProgress + 10;
        } else if (mProfileDetails.getAadharUpdated() == 2) {
            mChangeAadharTV.setText(getString(R.string.text_pending_approval));
            mChangeAadharTV.setEnabled(false);
            mChangeAadharTV.setTextColor(getResources().getColor(R.color.orange_dark));
            mAadharTV.setText("");
        }
//        mChangeAadharTV.setEnabled(true);
        if (mProfileDetails.isEmailVerified()) {
            mShowEmailTV.setText(mAppPreference.getEmail());
            mUpdateEmailTV.setText(R.string.text_verified);
            mUpdateEmailTV.setEnabled(false);
            mUpdateEmailTV.setTextColor(getResources().getColor(R.color.color_green));
            mProgress = mProgress + 10;
        } else {
            mUpdateEmailTV.setText(R.string.text_update_email);
            mUpdateEmailTV.setEnabled(true);
            mShowEmailTV.setText("");
            mUpdateEmailTV.setTextColor(getResources().getColor(R.color.battle_red));
        }
        mProfileProgressPB.setProgress(mProgress);
        mProfilePercentTV.setText(mProgress + "/100");
        setCredentials(mProfileDetails.getCredentials());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == AppConstant.RC_ASK_PERMISSIONS_STORAGE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.txt_storage_permission), Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, UpdateProfileActivity.class));
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showMsg(String msg) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.popup);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        Button okBTN = dialog.findViewById(R.id.btn_ok);
        okBTN.setOnClickListener(view -> {
            dialog.dismiss();
            getProfile();
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (mAppPreference.getIsDeepLinking()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            finish();
        }
    }

    private final IOnVerifyEmailListener onVerifyEmailAddressListener = new IOnVerifyEmailListener() {
        @Override
        public void onSendOTP(String email) {
            mNewEmail = email;
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.doSendOTP(mNewEmail);
        }

        @Override
        public void onVerifyEmail(String email, String otp) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.doVerifyEmail(email, otp);
        }

        @Override
        public void onResendOTP(String email) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.doSendOTP(email);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        getProfile();
    }

    @Override
    protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }

    private void setUserDataMoEngage() {
        MoEAnalyticsHelper.INSTANCE.setFirstName(this, mAppPreference.getName());
        MoEAnalyticsHelper.INSTANCE.setUserName(this, mAppPreference.getProfileData().getUsername());
        MoEAnalyticsHelper.INSTANCE.setEmailId(this, mAppPreference.getProfileData().getEmail());
        MoEAnalyticsHelper.INSTANCE.setMobileNumber(this, mAppPreference.getMobile());
    }


}