package com.khiladiadda.pinelabs;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;
import com.khiladiadda.dialogs.AppDialog;
import com.khiladiadda.dialogs.IOnPineLabsListener;

import butterknife.BindView;

public class PineLabsDetailsScreenActivity extends BaseActivity implements IOnPineLabsListener {

    @BindView(R.id.btn_get_otp)
    AppCompatButton mGetOtpBtn;
    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @Override
    protected int getContentView() {
        return R.layout.activity_pine_labs_details_screen;
    }

    @Override
    protected void initViews() {
        mTitleTv.setText("My Card");
    }

    @Override
    protected void initVariables() {
        mGetOtpBtn.setOnClickListener(this);
        mBackIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View p0) {
        if (p0.getId() == R.id.btn_get_otp) {
            AppDialog.PineLabsDialog(this, this, "Enter the 6 otp sent to 9876543210");
        } else if (p0.getId() == R.id.iv_back) {
            finish();
        }
    }

    @Override
    public void iOnOtpSuccess() {
        AppDialog.showStatusSuccessPineLabsDialog(this, this);
    }

    @Override
    public void iOnResendOtp() {

    }

    @Override
    public void iOnSuccessOkayClicked(int type) {
        if (type == 1)
            startActivity(new Intent(this, PineLabsPancardActivity.class));
    }
}