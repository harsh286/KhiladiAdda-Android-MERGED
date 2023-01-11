package com.khiladiadda.referhistory;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;

import butterknife.BindView;

public class ReferHelpActivity extends BaseActivity {

    @BindView(R.id.tv_next) TextView mNextTV;

    @Override protected int getContentView() {
        return R.layout.activity_refer_help;
    }

    @Override protected void initViews() {
        mNextTV.setOnClickListener(this);
    }

    @Override protected void initVariables() {

    }

    @Override public void onClick(View v) {
        if (v.getId() == R.id.tv_next) {
            startActivity(new Intent(this, InviteActivity.class));
            finish();
        }
    }
}