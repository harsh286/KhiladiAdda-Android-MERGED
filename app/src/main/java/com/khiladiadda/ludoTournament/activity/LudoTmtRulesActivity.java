package com.khiladiadda.ludoTournament.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.utility.AppConstant;

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

    private int type;

    @Override
    protected int getContentView() {
        return R.layout.activity_ludo_tmt_rules;
    }

    @Override
    protected void initViews() {
        type = getIntent().getIntExtra("type", 1);
        rulesEnglishTv.setMovementMethod(new ScrollingMovementMethod());
        rulesHindiTv.setMovementMethod(new ScrollingMovementMethod());
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
            if (type == 1) {
                rulesEnglishTv.setText(AppConstant.CLASSIC_RULE);
            } else if (type == 3) {
                rulesEnglishTv.setText(AppConstant.SERIES_RULES);
            } else if (type == 2) {
                rulesEnglishTv.setText(AppConstant.TIMER_RULE);
            }
        } else if (langH.equals(i.getStringExtra("lang"))) {
            rulesHindiTv.setVisibility(View.VISIBLE);
            rulesEnglishTv.setVisibility(View.INVISIBLE);
            if (type == 1) {
                rulesHindiTv.setText(AppConstant.CLASSIC_RULE_HINDI);
            } else if (type == 3) {
                rulesHindiTv.setText(AppConstant.SERIES_RULE_HINDI);
            } else if (type == 2) {
                rulesHindiTv.setText(AppConstant.TIMER_RULES_HINDI);
            }
        }
    }
}