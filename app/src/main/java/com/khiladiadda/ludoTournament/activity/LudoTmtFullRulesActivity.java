package com.khiladiadda.ludoTournament.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;

import butterknife.BindView;

public class LudoTmtFullRulesActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView backIv;

    @Override
    protected int getContentView() {
        return R.layout.activity_ludo_tmt_full_rules;
    }

    @Override
    protected void initViews() {
        backIv.setOnClickListener(this);

    }

    @Override
    protected void initVariables() {
        backIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View p0) {
        if (p0.getId() == R.id.iv_back) {
            finish();
        }
    }
}