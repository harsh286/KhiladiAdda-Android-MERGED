package com.khiladiadda.pinelabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.dialogs.IOnPineLabsListener;

import butterknife.BindView;

public class PineLabsPancardActivity extends BaseActivity implements IOnPineLabsListener {

    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.btn_proceed)
    AppCompatButton mProceedBtn;
    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @Override
    protected int getContentView() {
        return R.layout.activity_pine_labs_pancard;
    }

    @Override
    protected void initViews() {
        mTitleTv.setText("My Card");
    }

    @Override
    protected void initVariables() {
        mBackIv.setOnClickListener(this);
        mProceedBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View p0) {
        if (p0.getId() == R.id.iv_back){
            finish();
        }else if(p0.getId() == R.id.btn_proceed){
            AppDialog.showPanCardSuccessPineLabsDialog(this,this);
        }
    }

    @Override
    public void iOnOtpSuccess() {

    }

    @Override
    public void iOnResendOtp() {

    }

    @Override
    public void iOnSuccessOkayClicked(int type) {
        if (type == 2){
            startActivity(new Intent(this, PineLabsLinkDeviceActivity.class));
        }
    }
}