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
    @BindView(R.id.tv_english_rules)
    TextView rulesEnglishTv;
    @BindView(R.id.tv_hindi_rules)
    TextView rulesHindiTv;

    @Override
    protected int getContentView() {
        return R.layout.activity_ludo_tmt_rules;
    }

    @Override
    protected void initViews() {
        dialoglangFrom();
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

    private void dialoglangFrom() {
        String langE = "english";
        String langH = "hindi";
        Intent i = getIntent();
        if (langE.equals(i.getStringExtra("lang"))) {
            rulesHindiTv.setVisibility(View.INVISIBLE);
            rulesEnglishTv.setVisibility(View.VISIBLE);
        } else if (langH.equals(i.getStringExtra("lang"))) {
            rulesHindiTv.setVisibility(View.VISIBLE);
            rulesEnglishTv.setVisibility(View.INVISIBLE);
        }
    }
}