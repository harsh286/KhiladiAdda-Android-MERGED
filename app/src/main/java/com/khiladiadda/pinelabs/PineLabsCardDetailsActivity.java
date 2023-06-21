package com.khiladiadda.pinelabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;

import butterknife.BindView;

public class PineLabsCardDetailsActivity extends BaseActivity {
    @BindView(R.id.btn_proceed)
    AppCompatButton mProceedBtn;

    @Override
    protected int getContentView() {
        return R.layout.activity_pine_labs_card_details;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initVariables() {
        mProceedBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View p0) {
        if (p0.getId() == R.id.btn_proceed){
            startActivity(new Intent(this, PineLabsManageCardActivity.class));
        }
    }
}