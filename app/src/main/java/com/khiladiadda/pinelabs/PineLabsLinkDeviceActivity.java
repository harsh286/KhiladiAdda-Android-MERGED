package com.khiladiadda.pinelabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;

import butterknife.BindView;

public class PineLabsLinkDeviceActivity extends BaseActivity {

    @BindView(R.id.tv_link)
    TextView mLinkTv;

    @Override
    protected int getContentView() {
        return R.layout.activity_pine_labs_link_device;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initVariables() {
        mLinkTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View p0) {
        if (p0.getId() == R.id.tv_link) {
            startActivity(new Intent(this, PineLabsCardDetailsActivity.class));
        }
    }
}