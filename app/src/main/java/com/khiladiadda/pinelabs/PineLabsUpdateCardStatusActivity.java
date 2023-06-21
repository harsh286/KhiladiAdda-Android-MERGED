package com.khiladiadda.pinelabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.khiladiadda.R;
import com.khiladiadda.base.BaseActivity;

import butterknife.BindView;

public class PineLabsUpdateCardStatusActivity extends BaseActivity implements AdapterView.OnItemSelectedListener{

    @BindView(R.id.sp_action)
    Spinner mActionSp;

    String[] action = { "Unblock", "Block" };

    @Override
    protected int getContentView() {
        return R.layout.activity_pine_labs_update_card_status;
    }

    @Override
    protected void initViews() {
        mActionSp.setOnItemSelectedListener(this);
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                action);
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        mActionSp.setAdapter(ad);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}