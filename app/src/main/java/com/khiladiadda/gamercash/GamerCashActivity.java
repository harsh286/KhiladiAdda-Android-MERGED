package com.khiladiadda.gamercash;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.chat.model.ChatMessage;
import com.khiladiadda.gamercash.interfaces.IGamerCashView;
import com.khiladiadda.gamercash.interfaces.IVerifyingGamerCashUserPresenter;
import com.khiladiadda.gamercash.ip.GamerCashUserPresenter;
import com.khiladiadda.network.model.ApiError;
import com.khiladiadda.network.model.response.gamer_cash.GamerCashResponse;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.NetworkStatus;
import com.moengage.core.analytics.MoEAnalyticsHelper;
import com.moengage.core.model.AppStatus;

import butterknife.BindView;

public class GamerCashActivity extends BaseActivity implements IGamerCashView {
    @BindView(R.id.tv_error)
    TextView tvError;
    private IVerifyingGamerCashUserPresenter mPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_gamer_cash;
    }

    @Override
    protected void initViews() {
        mPresenter = new GamerCashUserPresenter(this);
        AppSharedPreference.initialize(this);
        gamerCasdVerifyAPI();
    }


    private void gamerCasdVerifyAPI() {
        if (new NetworkStatus(this).isInternetOn()) {
            showProgress(getString(R.string.txt_progress_authentication));
            mPresenter.getGamerCashUserData();
        } else {
            Snackbar.make(tvError, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initVariables() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onGamerCashSuccess(GamerCashResponse response) {
//        new Handler(Looper.getMainLooper()).postDelayed(() -> {
        if (response.getAlreadyLinked() || response.getLinked()) {
            String amount = getIntent().getExtras().getString("enter_amount");
            int coins = getIntent().getExtras().getInt("coins", 0);
            hideProgress();
            AppSharedPreference.getInstance().setIsGamerCashLinked(true);
            Intent intent = new Intent(this, PayActivity.class);
            intent.putExtra("enter_amount", amount);
            intent.putExtra("coins", coins);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(GamerCashActivity.this, NotInstalledActivity.class);
            intent.putExtra("isVerified", false);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onGamerCashFailure(ApiError error) {
        hideProgress();
        Intent intent = new Intent(GamerCashActivity.this, InstallActivity.class);
        intent.putExtra("isVerified", false);
        startActivity(intent);
    }
};
