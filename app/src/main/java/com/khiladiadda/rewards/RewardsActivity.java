package com.khiladiadda.rewards;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.help.HelpActivity;
import com.khiladiadda.league.myleague.MyLeagueActivity;

import butterknife.BindView;

public class RewardsActivity extends BaseActivity {

    @BindView(R.id.iv_back) ImageView mBackIV;
    @BindView(R.id.tv_activity_name) TextView mActivityNameTV;
    @BindView(R.id.iv_notification) ImageView mNotificationIV;
    @BindView(R.id.tv_league) TextView mLeagueTV;
    @BindView(R.id.tv_home) TextView mHomeTV;
    @BindView(R.id.tv_help) TextView mHelpTV;
    @BindView(R.id.btn_my_coupon) Button mMyCouponBTN;
    @BindView(R.id.btn_get_coupon) Button mGetCouponBTN;
    @BindView(R.id.btn_coupons) Button mCouponsBTN;

    @Override protected int getContentView() {
        return R.layout.activity_rewards;
    }

    @Override protected void initViews() {
        mActivityNameTV.setText(R.string.my_rewards_offer);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mLeagueTV.setOnClickListener(this);
        mHomeTV.setOnClickListener(this);
        mHelpTV.setOnClickListener(this);
        mMyCouponBTN.setOnClickListener(this);
        mGetCouponBTN.setOnClickListener(this);
        mCouponsBTN.setOnClickListener(this);
        mMyCouponBTN.setSelected(true);
    }

    @Override protected void initVariables() {

    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
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
            case R.id.btn_my_coupon:
                mMyCouponBTN.setSelected(true);
                mGetCouponBTN.setSelected(false);
                break;
            case R.id.btn_get_coupon:
                mMyCouponBTN.setSelected(false);
                mGetCouponBTN.setSelected(true);
                break;
            case R.id.btn_coupons:
                Snackbar.make(mCouponsBTN, R.string.text_payment_coming_soon, Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

}