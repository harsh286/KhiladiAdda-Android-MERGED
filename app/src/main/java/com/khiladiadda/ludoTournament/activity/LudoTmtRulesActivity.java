package com.khiladiadda.ludoTournament.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;

import butterknife.BindView;

public class LudoTmtRulesActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView backIv;
    @BindView(R.id.tv_view_full)
    TextView viewFullIv;

    @Override
    protected int getContentView() {
        return R.layout.activity_ludo_tmt_rules;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initVariables() {
        backIv.setOnClickListener(this);
        viewFullIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View p0) {
        if (p0.getId() == R.id.iv_back) {
            finish();
        } else if (p0.getId() == R.id.tv_view_full) {
            startActivity(new Intent(this, LudoTmtFullRulesActivity.class));
        }
    }
}