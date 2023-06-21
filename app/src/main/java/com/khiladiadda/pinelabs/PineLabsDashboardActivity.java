package com.khiladiadda.pinelabs;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;

import butterknife.BindView;

public class PineLabsDashboardActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.btn_apply)
    AppCompatButton mApplyBtn;
    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @Override
    protected int getContentView() {
        return R.layout.activity_pine_labs_dashboard;
    }

    @Override
    protected void initViews() {
        mTitleTv.setText("My Card");
    }

    @Override
    protected void initVariables() {
        mApplyBtn.setOnClickListener(this);
        mBackIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View p0) {
        if (p0.getId() == R.id.iv_back){
            finish();
        }else if(p0.getId() == R.id.btn_apply){
            startActivity(new Intent(this, PineLabsDetailsScreenActivity.class));
        }
    }
}