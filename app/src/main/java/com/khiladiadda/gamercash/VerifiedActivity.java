package com.khiladiadda.gamercash;

import android.content.Intent;
import android.os.Handler;
import android.view.View;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.utility.AppUtilityMethods;

public class VerifiedActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_verified;
    }

    @Override
    protected void initViews() {
        AppUtilityMethods.installedGamerPE(this);
    }

    @Override
    protected void initVariables() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);
    }

    @Override
    public void onClick(View view) {

    }
}