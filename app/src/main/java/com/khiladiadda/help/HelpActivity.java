package com.khiladiadda.help;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.applozic.mobicomkit.uiwidgets.kommunicate.KmPrefSettings;
import com.applozic.mobicommons.ALSpecificSettings;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.help.interfaces.IHelpPresenter;
import com.khiladiadda.help.interfaces.IHelpView;
import com.khiladiadda.main.MainActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.FaqCategoryResponse;
import com.khiladiadda.network.model.response.HelpResponse;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.ImageActivity;
import com.khiladiadda.utility.NetworkStatus;
import com.moengage.inapp.MoEInAppHelper;
import com.moengage.widgets.NudgeView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import io.kommunicate.KmSettings;
import io.kommunicate.Kommunicate;
import io.kommunicate.callbacks.KMLoginHandler;
import io.kommunicate.users.KMUser;

public class HelpActivity extends BaseActivity implements IHelpView {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.cv_freefire)
    ImageView mFreeFireCV;
    @BindView(R.id.cv_ludo)
    ImageView mLudoCV;
    @BindView(R.id.iv_fanbattle)
    ImageView mFanBattleIV;
    @BindView(R.id.cv_quiz)
    ImageView mQuizCV;
    @BindView(R.id.cv_other)
    ImageView mOtherCV;
    @BindView(R.id.cv_deposit)
    ImageView mDepositCV;
    @BindView(R.id.cv_withdraw)
    ImageView mWithdrawCV;
    @BindView(R.id.cv_pubg)
    ImageView mPubgCV;
    @BindView(R.id.cv_kyc)
    ImageView mKycCV;
    @BindView(R.id.cv_ff_max)
    ImageView mFFMaxCV;
    @BindView(R.id.iv_hthhelp)
    ImageView mHTHIV;
    @BindView(R.id.iv_ludo_universe)
    ImageView mLudoAddaIV;
    @BindView(R.id.iv_esports_help)
    ImageView mEsportsIV;
    @BindView(R.id.iv_ludo_tournament)
    ImageView mLudoTournamentTv;
    @BindView(R.id.iv_wscoming)
    ImageView mWordSearchIV;
    @BindView(R.id.iv_codepieces)
    ImageView mCodePieces;
    @BindView(R.id.iv_droid)
    ImageView mDroidIV;
    @BindView(R.id.iv_rummy)
    ImageView mRummyIV;
    @BindView(R.id.nudge)
    NudgeView mNV;
    private IHelpPresenter mPresenter;
    private boolean mSupportVia;
    private String mFrom;

    @Override
    protected int getContentView() {
        return R.layout.activity_help;
    }

    @Override
    protected void initViews() {
        mActivityNameTV.setText(R.string.text_help);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mFreeFireCV.setOnClickListener(this);
        mLudoCV.setOnClickListener(this);
        mQuizCV.setOnClickListener(this);
        mOtherCV.setOnClickListener(this);
        mDepositCV.setOnClickListener(this);
        mWithdrawCV.setOnClickListener(this);
        mPubgCV.setOnClickListener(this);
        mKycCV.setOnClickListener(this);
        mFFMaxCV.setOnClickListener(this);
        mFanBattleIV.setOnClickListener(this);
        mHTHIV.setOnClickListener(this);
        mLudoAddaIV.setOnClickListener(this);
        mEsportsIV.setOnClickListener(this);
        mWordSearchIV.setOnClickListener(this);
        mDroidIV.setOnClickListener(this);
        mLudoTournamentTv.setOnClickListener(this);
        mCodePieces.setOnClickListener(this);
        mRummyIV.setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mNV.initialiseNudgeView(this);
        MoEInAppHelper.getInstance().showInApp(this);
    }

    @Override
    protected void initVariables() {
        mPresenter = new HelpPresenter(this);
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getFaqCategory();
        } else {
            Snackbar.make(mActivityNameTV, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        Intent i;
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
            case R.id.cv_pubg:
                if (mSupportVia) {
                    mFrom = AppConstant.BGMICATEGORY_KOMMUNICATE;
                    openHelpScreen();
                } else {
                    i = new Intent(this, HelpDetailsActivity.class);
                    i.putExtra(AppConstant.DATA, mAppPreference.getFaqCategoryData().getResponse().get(0));
                    startActivity(i);
                }
                break;
            case R.id.cv_freefire:
                if (mSupportVia) {
                    mFrom = AppConstant.FREEFIRE_KOMMUNI_CATEGORY;
                    openHelpScreen();
                } else {
                    i = new Intent(this, HelpDetailsActivity.class);
                    i.putExtra(AppConstant.DATA, mAppPreference.getFaqCategoryData().getResponse().get(2));
                    startActivity(i);
                }
                break;
            case R.id.cv_quiz:
                if (mSupportVia) {
                    mFrom = AppConstant.QUIZ_KOMMUNI_CATEGORY;
                    openHelpScreen();
                } else {
                    i = new Intent(this, HelpDetailsActivity.class);
                    i.putExtra(AppConstant.DATA, mAppPreference.getFaqCategoryData().getResponse().get(5));
                    startActivity(i);
                }
                break;
            case R.id.iv_ludo_universe:
                if (mSupportVia) {
                    mFrom = AppConstant.LUDOADDA_KOMMUNI_CATEGORY;
                    openHelpScreen();
                } else {
                    i = new Intent(this, HelpDetailsActivity.class);
                    i.putExtra(AppConstant.DATA, mAppPreference.getFaqCategoryData().getResponse().get(6));
                    startActivity(i);
                }
                break;
            case R.id.cv_ludo:
                if (mSupportVia) {
                    mFrom = AppConstant.LUDO_KOMMUNI_CATEGORY;
                    openHelpScreen();
                } else {
                    i = new Intent(this, HelpDetailsActivity.class);
                    i.putExtra(AppConstant.DATA, mAppPreference.getFaqCategoryData().getResponse().get(6));
                    startActivity(i);
                }
                break;
            case R.id.iv_fanbattle:  //fb
            case R.id.iv_hthhelp:
                if (mSupportVia) {
                    mFrom = AppConstant.FANBATTLE_KOMMUNI_CATEGORY;
                    openHelpScreen();
                } else {
                    i = new Intent(this, HelpDetailsActivity.class);
                    i.putExtra(AppConstant.DATA, mAppPreference.getFaqCategoryData().getResponse().get(11));
                    startActivity(i);
                }
                break;
            case R.id.cv_deposit:
                if (mSupportVia) {
                    mFrom = AppConstant.WITHDRAW_AND_DEPOSIT_KOMMUNICATECATEGORY;
                    openHelpScreen();
                } else {
                    i = new Intent(this, HelpDetailsActivity.class);
                    i.putExtra(AppConstant.DATA, mAppPreference.getFaqCategoryData().getResponse().get(7));
                    startActivity(i);
                }
                break;
            case R.id.iv_wscoming:
            case R.id.iv_droid:
            case R.id.cv_withdraw:
                if (mSupportVia) {
                    mFrom = AppConstant.WITHDRAW_AND_DEPOSIT_KOMMUNICATECATEGORY;
                    openHelpScreen();
                } else {
                    i = new Intent(this, HelpDetailsActivity.class);
                    i.putExtra(AppConstant.DATA, mAppPreference.getFaqCategoryData().getResponse().get(8));
                    startActivity(i);
                }
                break;
            case R.id.cv_other:
                if (mSupportVia) {
                    mFrom = AppConstant.KYC_KOMMUNICATECATEGORY;
                    openHelpScreen();
                } else {
                    i = new Intent(this, HelpDetailsActivity.class);
                    i.putExtra(AppConstant.DATA, mAppPreference.getFaqCategoryData().getResponse().get(9));
                    startActivity(i);
                }
                break;
            case R.id.cv_kyc:
                if (mSupportVia) {
                    mFrom = AppConstant.KYC_KOMMUNICATECATEGORY;
                    openHelpScreen();
                } else {
                    i = new Intent(this, HelpDetailsActivity.class);
                    i.putExtra(AppConstant.DATA, mAppPreference.getFaqCategoryData().getResponse().get(10));
                    startActivity(i);
                }
                break;
            case R.id.cv_ff_max:
                if (mSupportVia) {
                    mFrom = AppConstant.FREEFIREMAX_KOMMUNICATECATEGORY;
                    openHelpScreen();
                } else {
                    i = new Intent(this, HelpDetailsActivity.class);
                    i.putExtra(AppConstant.DATA, mAppPreference.getFaqCategoryData().getResponse().get(12));
                    startActivity(i);
                }
                break;
            case R.id.iv_esports_help:
                if (mSupportVia) {
                    mFrom = AppConstant.ESPROTSPERMIUM_KOMMUNICATE;
                    openHelpScreen();
                } else {
                    i = new Intent(this, HelpDetailsActivity.class);
                    i.putExtra(AppConstant.DATA, mAppPreference.getFaqCategoryData().getResponse().get(12));
                    startActivity(i);
                }
                break;
            case R.id.iv_ludo_tournament:
                if (mSupportVia) {
                    mFrom = AppConstant.LUDOTOUR_KOMMUNI_CATEGORY;
                    openHelpScreen();
                } else {
                    i = new Intent(this, HelpDetailsActivity.class);
                    i.putExtra(AppConstant.DATA, mAppPreference.getFaqCategoryData().getResponse().get(14));
                    startActivity(i);
                }
                break;
            case R.id.iv_codepieces:
                if (mSupportVia) {
                    mFrom = AppConstant.CALLBREAK_KOMMUNI_CATEGORY;
                    openHelpScreen();
                } else {
                    i = new Intent(this, HelpDetailsActivity.class);
                    i.putExtra(AppConstant.DATA, mAppPreference.getFaqCategoryData().getResponse().get(15));
                    startActivity(i);
                }
                break;
            case R.id.iv_rummy:
                if (mSupportVia) {
                    mFrom = AppConstant.RUMMY_KOMMUNI_CATEGORY;
                    openHelpScreen();
                } else {
                    i = new Intent(this, HelpDetailsActivity.class);
                    i.putExtra(AppConstant.DATA, mAppPreference.getFaqCategoryData().getResponse().get(16));
                    startActivity(i);
                }
                break;
        }
    }

    @Override
    public void onGetCategoryComplete(FaqCategoryResponse responseModel) {
        hideProgress();
        if (responseModel.isStatus()) {
            mSupportVia = responseModel.getSupportVia() != 0;
            mAppPreference.setFAQCategoryData(responseModel);
        }
    }

    @Override
    public void onGetCategoryFailure(ApiError error) {
        hideProgress();
    }

    @Override
    public void onHelpComplete(HelpResponse responseModel) {
    }

    @Override
    public void onHelpFailure(ApiError error) {
    }

    @Override
    protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
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

    private void openKommunicateChat(String category) {
        Kommunicate.init(HelpActivity.this, AppConstant.APP_ID);
        Kommunicate.updateDeviceToken(HelpActivity.this, mAppPreference.getDeviceToken());
        KmPrefSettings.getInstance(HelpActivity.this).enableSpeechToText(false);
        ALSpecificSettings.getInstance(HelpActivity.this).enableLoggingForReleaseBuild(false);
        ALSpecificSettings.getInstance(HelpActivity.this).enableTextLogging(false);
        KMUser mUser = new KMUser();
        mUser.setUserId(mAppPreference.getMobile()); // Pass a unique key
        mUser.setImageLink(mAppPreference.getUrl()); // Optional
        mUser.setDisplayName(mAppPreference.getName()); //Optional
        mUser.setContactNumber(mAppPreference.getMobile());
        mUser.setEmail(mAppPreference.getEmail());

        Kommunicate.login(HelpActivity.this, mUser, new KMLoginHandler() {
            @Override
            public void onSuccess(RegistrationResponse registrationResponse, Context context) {
                hideProgress();
                Kommunicate.openConversation(context, null);
                Map<String, String> metadata = new HashMap<>();
                metadata.put("Category", category);
                metadata.put("PhoneNumber", mAppPreference.getMobile());// Pass key value string
                // Pass key value string
                if (Kommunicate.isLoggedIn(context)) { // Pass application context
                    KmSettings.updateChatContext(context, metadata);
                }
            }

            @Override
            public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
                hideProgress();
            }
        });
    }

    private void openHelpScreen() {
        Intent i = new Intent(this, ImageActivity.class);
        i.putExtra(AppConstant.FROM, AppConstant.HELPSECTION);
        startActivityForResult(i, AppConstant.FROM_HELP);
    }

    private void openKomunicate() {
        showProgress(getString(R.string.txt_progress_authentication));
        openKommunicateChat(mFrom);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.FROM_HELP && resultCode == RESULT_OK) {
            openKomunicate();
        }
    }

}