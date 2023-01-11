package com.khiladiadda.referhistory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.fcm.NotificationActivity;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.ReferDetail;
import com.khiladiadda.network.model.response.ReferResponse;
import com.khiladiadda.referhistory.adapter.ReferAdapter;
import com.khiladiadda.referhistory.interfaces.IReferPresenter;
import com.khiladiadda.referhistory.interfaces.IReferView;
import com.khiladiadda.utility.AppConstant;
import com.khiladiadda.utility.AppUtilityMethods;
import com.khiladiadda.utility.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ReferActivity extends BaseActivity implements IReferView {

    @BindView(R.id.iv_back) ImageView mBackIV;
    @BindView(R.id.tv_activity_name) TextView mActivityNameTV;
    @BindView(R.id.iv_notification) ImageView mNotificationIV;
    @BindView(R.id.btn_share) Button mShareBTN;
    @BindView(R.id.rv_refer) RecyclerView mReferRV;
    @BindView(R.id.tv_earn) TextView mEarnTV;
    @BindView(R.id.tv_total_friends) TextView mFriendsTV;

    private IReferPresenter mPresenter;
    private ReferAdapter mAdapter;
    private List<ReferDetail> mList = null;

    @Override protected int getContentView() {
        return R.layout.activity_refer;
    }

    @Override protected void initViews() {
        mActivityNameTV.setText(R.string.my_referals);
        mBackIV.setOnClickListener(this);
        mNotificationIV.setOnClickListener(this);
        mShareBTN.setOnClickListener(this);
    }

    @Override protected void initVariables() {
        mPresenter = new ReferPresenter(this);

        mList = new ArrayList<>();
        mAdapter = new ReferAdapter(mList);
        mReferRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mReferRV.setAdapter(mAdapter);

        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getRefer();
        } else {
            Snackbar.make(mShareBTN, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.btn_share:
                AppUtilityMethods.shareInviteCode(this);
                break;
        }
    }

    @Override public void onReferComplete(ReferResponse responseModel) {
        hideProgress();
        mList.clear();
        int amount = responseModel.getResponse().size() * 5;
        mEarnTV.setText(getString(R.string.text_earning_through_friends) + "\n" + amount);
        mFriendsTV.setText(getString(R.string.text_friends_joined) + "\n" + responseModel.getResponse().size());
        mList.addAll(responseModel.getResponse());
        mAdapter.notifyDataSetChanged();
    }

    @Override public void onReferFailure(ApiError error) {
        hideProgress();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

}