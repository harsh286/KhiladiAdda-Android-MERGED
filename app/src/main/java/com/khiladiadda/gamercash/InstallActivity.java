package com.khiladiadda.gamercash;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.preference.AppSharedPreference;
import com.khiladiadda.utility.AppUtilityMethods;

import butterknife.BindView;

public class InstallActivity extends BaseActivity {
    @BindView(R.id.btn_download_now)
    Button downloadBTN;
    @BindView(R.id.iv_back_arrow)
    ImageView imgBack;
    @BindView(R.id.tv_not_installed)
    TextView notInstallTv;
    private boolean isVerified;

    @Override
    protected int getContentView() {
        return R.layout.activity_install;
    }

    @Override
    protected void initViews() {
        downloadBTN.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
        isVerified = getIntent().getBooleanExtra("isVerified", false);
        if (AppUtilityMethods.installedGamerPE(this)) {
            if (isVerified) {
                startActivity(new Intent(InstallActivity.this, PayActivity.class));
            } else {
                notInstallTv.setText("You are not register on gamerpe with " + AppSharedPreference.initialize(this).getMasterData().getResponse().getProfile().getMobile());
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_download_now:
                openPlayStore();
                break;
            case R.id.iv_back_arrow:
                onBackPressed();
                finish();
                break;
        }
    }

    private void openPlayStore() {
        Intent i = new Intent(android.content.Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.gamerpe.in"));
        startActivity(i);
        finish();
    }
}