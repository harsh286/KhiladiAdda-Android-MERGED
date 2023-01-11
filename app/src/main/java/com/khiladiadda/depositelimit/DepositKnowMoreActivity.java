package com.khiladiadda.depositelimit;

import android.view.View;
import android.widget.ImageView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;

import butterknife.BindView;

public class DepositKnowMoreActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @Override
    protected int getContentView() {
        return R.layout.activity_deposit_know_more;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initVariables() {
        mBackIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View p0) {
        if (p0.getId() == R.id.iv_back) {
            finish();
        }
    }
}