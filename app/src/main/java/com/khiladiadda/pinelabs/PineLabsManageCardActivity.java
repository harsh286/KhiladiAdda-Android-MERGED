package com.khiladiadda.pinelabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;

import butterknife.BindView;

public class PineLabsManageCardActivity extends BaseActivity {

    @BindView(R.id.cl_card_transaction_limit)
    ConstraintLayout mCardTransactionLimitCl;
    @BindView(R.id.cl_card_status_update)
    ConstraintLayout mCardStatusUpdateCl;
    @BindView(R.id.cl_generate_card_pin)
    ConstraintLayout mGenerateCardPinCl;
    @BindView(R.id.cl_card_transaction_history)
    ConstraintLayout mCardTransactionHistoryCl;

    @Override
    protected int getContentView() {
        return R.layout.activity_pine_labs_manage_card;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initVariables() {
        mCardTransactionLimitCl.setOnClickListener(this);
        mCardStatusUpdateCl.setOnClickListener(this);
        mGenerateCardPinCl.setOnClickListener(this);
        mCardTransactionHistoryCl.setOnClickListener(this);
    }

    @Override
    public void onClick(View p0) {
        Intent intent;
        if (p0.getId() == R.id.cl_card_transaction_limit) {
            intent = new Intent(this, PineLabsTransactionLimitActivity.class);
            startActivity(intent);
        } else if (p0.getId() == R.id.cl_card_status_update) {
            intent = new Intent(this, PineLabsUpdateCardStatusActivity.class);
            startActivity(intent);
        } else if (p0.getId() == R.id.cl_generate_card_pin) {
            intent = new Intent(this, PineLabsGeneratePinActivity.class);
            startActivity(intent);
        } else if (p0.getId() == R.id.cl_card_transaction_history) {
            intent = new Intent(this, PineLabsGeneratePinActivity.class);
            startActivity(intent);
        }
    }
}