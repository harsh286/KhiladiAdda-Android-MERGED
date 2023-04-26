package com.khiladiadda.gamercash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.MediaBrowserCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.gamercash.interfaces.ISwitchGamerCashPresenter;
import com.khiladiadda.gamercash.interfaces.ISwitchGamerCashView;
import com.khiladiadda.gamercash.ip.SwitchGamerCashPresenter;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.gamer_cash.SwitchGamerCashRequest;
import com.khiladiadda.network.model.response.gamer_cash.SwitchGamerCashResponse;
import com.khiladiadda.utility.NetworkStatus;

import butterknife.BindView;

public class PayActivity extends BaseActivity implements ISwitchGamerCashView {
    @BindView(R.id.tv_amount_dedution)
    TextView tvAmount;
    @BindView(R.id.tv_blance_in_gamerpe)
    TextView tvGamerPeBalance;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.btn_payy)
    Button btnPay;
    @BindView(R.id.iv_back_arrow)
    ImageView imgBack;
    private ISwitchGamerCashPresenter mPresenter;
    private long coins = 0;
    private String mCouponCode = "";

    @Override
    protected int getContentView() {
        return R.layout.activity_pay;
    }

    @Override
    protected void initViews() {
        mPresenter = new SwitchGamerCashPresenter(this);
        btnPay.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        getData();
    }

    private void getData() {
        String amount = getIntent().getExtras().getString("enter_amount");
        coins = getIntent().getExtras().getLong("coins");
        if (getIntent().getExtras().getString("coupon") != null)
            mCouponCode = getIntent().getExtras().getString("coupon");
        tvAmount.setText("Rs." + amount);
        tvGamerPeBalance.setText("Rs." + coins);
    }

    @Override
    protected void initVariables() {

    }

    private void payGamerCashVerifyAPI() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            int amount = 0;
            if (tvAmount.getText().toString() != "") {
                amount = Integer.parseInt(getIntent().getExtras().getString("enter_amount"));
            }
            SwitchGamerCashRequest request = new SwitchGamerCashRequest();
            request.setAmount(amount);
            request.setCoupon(mCouponCode);
            mPresenter.getSwitchGamerCashUserData(request);
        } else {
            Snackbar.make(tvError, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_payy:
                payGamerCashVerifyAPI();
                break;
            case R.id.iv_back_arrow:
                onBackPressed();
                finish();
                break;
        }
    }

    @Override
    public void onSwitchGamerCashSuccess(SwitchGamerCashResponse response) {
        if (response.isStatus()) {
            if (coins > 0) {
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    hideProgress();
                    AppDialog.showStatusSuccessDialog(this, response.getMessage());
                }, 1000);
            } else {
                hideProgress();
                AppDialog.showInsufficientGCDialog(this);
            }
        } else {
            hideProgress();
            AppDialog.showStatusFailureDialog(this, response.getMessage());
        }
    }

    @Override
    public void onSwitchGamerCashFailure(ApiError error) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            hideProgress();
            AppDialog.showStatusFailureDialog(this, "");
        }, 1000);
    }

}