package com.khiladiadda.gamercash;

import android.content.Intent;
import android.os.Handler;
import android.view.View;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;

public class NotInstalledActivity extends BaseActivity {
    private static int TIME_OUT = 2000;
    private boolean isVerified;

    @Override
    protected int getContentView() {
        return R.layout.activity_not_installed;
    }

    @Override
    protected void initViews() {
        isVerified = getIntent().getBooleanExtra("isVerified", false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(NotInstalledActivity.this, InstallActivity.class);
                i.putExtra("isVerified", isVerified);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    public void onClick(View view) {

    }
}