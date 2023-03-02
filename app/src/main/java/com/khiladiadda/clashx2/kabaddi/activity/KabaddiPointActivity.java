package com.khiladiadda.clashx2.kabaddi.activity;

import android.view.View;
import android.widget.ImageView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;

import butterknife.BindView;

public class KabaddiPointActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected int getContentView() {
        return R.layout.activity_kabaadi_point;
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void initVariables() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back) {
            finish();
        }
    }
}