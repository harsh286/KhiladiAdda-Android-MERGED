package com.khiladiadda.referhistory;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsflyer.AppsFlyerLib;
import com.appsflyer.CreateOneLinkHttpTask;
import com.appsflyer.share.LinkGenerator;
import com.appsflyer.share.ShareInviteHelper;
import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.help.HelpActivity;
import com.khiladiadda.league.myleague.MyLeagueActivity;
import com.khiladiadda.main.MainActivity;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;
import com.moengage.inapp.MoEInAppHelper;
import com.moengage.widgets.NudgeView;

import butterknife.BindView;

public class InviteActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mBackIV;
    @BindView(R.id.tv_activity_name)
    TextView mActivityNameTV;
    @BindView(R.id.iv_notification)
    ImageView mNotificationIV;
    @BindView(R.id.tv_league)
    TextView mLeagueTV;
    @BindView(R.id.tv_home)
    TextView mHomeTV;
    @BindView(R.id.tv_help)
    TextView mHelpTV;
    @BindView(R.id.tv_invite_code)
    TextView mInviteCodeTV;
    @BindView(R.id.btn_whatsapp)
    Button mWhatsAppBTN;
    @BindView(R.id.btn_option)
    Button mOptionBTN;
    @BindView(R.id.tv_view)
    TextView mViewTV;
    @BindView(R.id.tv_refer_coins)
    TextView mReferCoinsTV;
    @BindView(R.id.nudge)
    NudgeView mNV;

    @Override
    protected int getContentView() {
        return R.layout.activity_invite;
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

        mViewTV.setOnClickListener(this);
        mActivityNameTV.setText(R.string.text_invite_friends);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mLeagueTV.setOnClickListener(this);
        mHomeTV.setOnClickListener(this);
        mHelpTV.setOnClickListener(this);
        mWhatsAppBTN.setOnClickListener(this);
        mOptionBTN.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        mInviteCodeTV.setText(mAppPreference.getInviteCode());
        SpannableString ss1 = new SpannableString(mReferCoinsTV.getText().toString().trim());
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 24, ss1.length(), 0);
        ss1.setSpan(new RelativeSizeSpan(2f), 24, ss1.length(), 0); // set size
        ss1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 24, ss1.length(), 0);// set color
        mReferCoinsTV.setText(ss1);
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
            case R.id.tv_home:
                finish();
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.tv_league:
                startActivity(new Intent(this, MyLeagueActivity.class));
                break;
            case R.id.tv_help:
                startActivity(new Intent(this, HelpActivity.class));
                break;
            case R.id.tv_view:
                startActivity(new Intent(this, ReferActivity.class));
                break;
            case R.id.btn_whatsapp:
                AppUtilityMethods.inviteOnWhatsApp(this, mOptionBTN);
                break;
            case R.id.btn_option:
                if (new NetworkStatus(this).isInternetOn()) {
                    AppsFlyerRefer();
                } else {
                    Snackbar.make(mOptionBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void AppsFlyerRefer() {
        LinkGenerator linkGenerator = ShareInviteHelper.generateInviteUrl(this);
        AppsFlyerLib.getInstance().setAppInviteOneLink("g37F");
        linkGenerator.setCampaign("user_invite");
        linkGenerator.setChannel("af_app_invites");
        linkGenerator.addParameter("af_sub1", mAppPreference.getInviteCode());
        CreateOneLinkHttpTask.ResponseListener listener = new CreateOneLinkHttpTask.ResponseListener() {
            @Override
            public void onResponse(String s) {
                //  Log.d("APPS", "Share invite link: " + s);
                shareInviteCode(s);

            }

            @Override
            public void onResponseError(String s) {
                //Log.d("APPS", "onResponseError called");
            }
        };
        linkGenerator.generateLink(this, listener);
    }

    private void shareInviteCode(String refer) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, "Hi, Please download this app and register through my referral code: " + mAppPreference.getInviteCode() + "\n" + refer);
        this.startActivity(Intent.createChooser(share, "Referral Code"));
    }

    @Override
    public void onBackPressed() {
        if (mAppPreference.getIsDeepLinking()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            super.onBackPressed();
        }
    }

}